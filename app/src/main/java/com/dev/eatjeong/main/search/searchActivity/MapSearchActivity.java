package com.dev.eatjeong.main.search.searchActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapListResponseVO;
import com.dev.eatjeong.layout.ClearEditText;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;
import com.dev.eatjeong.mainWrap.GpsTracker;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import net.daum.android.map.MapActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapSearchActivity extends AppCompatActivity {
    ArrayList<String> allList = new ArrayList<String>();
    AppCompatTextView area_cancel,area_search;
//    ClearEditText area_keyword;
//    EditText area_keyword;
    ListView main_area_list;
    ListView sub_area_list;

    ArrayList<String> areaCodeList = new ArrayList<String>();
    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchAreaListResponseVO> mCallSearchMainAreaListResponseVO;

    StableArrayAdapter adapter;

    boolean subSearchFlag = false;

    private String main_area_name;

    private String sub_area_name;

    private String search_area_keyword = "";

    ConstraintLayout layoutMiddle;

    public GpsTracker  gpsTracker;
    AllAreaControll allAreaControll = new AllAreaControll();
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    AutoCompleteTextView area_keyword;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_search);

        area_cancel = findViewById(R.id.area_cancel);
        area_search = findViewById(R.id.area_search);


        area_keyword = (AutoCompleteTextView) findViewById(R.id.area_keyword);
        // AutoCompleteTextView 에 아답터를 연결한다.


        main_area_list = findViewById(R.id.main_area_list);
        sub_area_list = findViewById(R.id.sub_area_list);

        layoutMiddle = findViewById(R.id.layoutMiddle);




        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callMainAreaResponse();

        area_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

                        intent.putExtra("keyword",area_keyword.getText().toString());
                        setResult(1,intent);
                        finish();
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }

                return true;
            }
        });
        area_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        area_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_area_keyword.equals("")){
                    Toast.makeText(getApplicationContext(),"지역을 선택 해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.
                intent.putExtra("keyword",search_area_keyword);

                setResult(1,intent);
                finish();
            }
        });


        main_area_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main_area_name = main_area_list.getAdapter().getItem(position).toString();
                callSubAreaResponse(areaCodeList.get(position));
                area_search.setTextColor(Color.parseColor("#dddddd"));
            }
        });

        sub_area_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sub_area_name = sub_area_list.getAdapter().getItem(position).toString();

                search_area_keyword = main_area_name + ' ' + sub_area_name;
                if(sub_area_name.indexOf(main_area_name) > -1){
                    search_area_keyword = main_area_name; //ex)서울 전체 선택 시 -- 서울 서울 전체 가 되므로 서울 전체는 키워드에서 제외
                }

                area_search.setTextColor(Color.parseColor("#ffe074"));
            }
        });


        layoutMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLocationServicesStatus()) {

                    showDialogForLocationServiceSetting();
                }else {

                    checkRunTimePermission();
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

    }

    private void callMainAreaResponse() {

        mCallSearchMainAreaListResponseVO = mSearchRetrofitAPI.getMainAreaList();
        mCallSearchMainAreaListResponseVO.enqueue(mRetrofitCallback);

    }

    private void callSubAreaResponse(String area_code) {

        subSearchFlag = true;
        mCallSearchMainAreaListResponseVO = mSearchRetrofitAPI.getSubAreaList(area_code);
        mCallSearchMainAreaListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchAreaListResponseVO> mRetrofitCallback = new Callback<SearchAreaListResponseVO>() {



        @Override

        public void onResponse(Call<SearchAreaListResponseVO> call, Response<SearchAreaListResponseVO> response) {

            if(subSearchFlag==false){
                final ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < response.body().mDatalist.size(); i++) {
                    areaCodeList.add(response.body().mDatalist.get(i).getArea()); //코드넣어주는 리스트 따로 관리
                    list.add(response.body().mDatalist.get(i).getArea_name());
                }

                adapter = new StableArrayAdapter(getApplicationContext(),
                        R.layout.area_text, list);

                main_area_list.setAdapter(adapter);
            }else{
                final ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < response.body().mDatalist.size(); i++) {
                    list.add(response.body().mDatalist.get(i).getArea_name());
                }

                adapter = new StableArrayAdapter(getApplicationContext(),
                        R.layout.area_text, list);

                sub_area_list.setAdapter(adapter);
                subSearchFlag = false;
            }

            allAreaControll.setRetrofitInit();
        }



        @Override

        public void onFailure(Call<SearchAreaListResponseVO> call, Throwable t) {

            t.printStackTrace();
            allAreaControll.setRetrofitInit();
        }

    };



    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                getLocation();
//                setFirstFragment();
                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MapSearchActivity.this, "위치 설정이 거부되었습니다. 앱 사용에 제약이 있을 수 있습니다.", Toast.LENGTH_LONG).show();
                    //finish();


                }else {

                    Toast.makeText(MapSearchActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
//                getLocation();
//                setFirstFragment();
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MapSearchActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MapSearchActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            //  이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            //이미 권한 가지고있으면 바로 호출함
//            setFirstFragment();
            getLocation();


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapSearchActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
//                Toast.makeText(MainWrapActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MapSearchActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MapSearchActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }



        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
//            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            //네트워크 문제 발생하면 address return ""으로 뱉어서 서울 맛집으로 검색 되도록 설정.
            return "";
        } catch (IllegalArgumentException illegalArgumentException) {
//            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            //네트워크 문제 발생하면 address return ""으로 뱉어서 서울 맛집으로 검색 되도록 설정.
            return "";

        }



        if (addresses == null || addresses.size() == 0) {
//            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapSearchActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    public void getLocation(){
        gpsTracker = new GpsTracker(MapSearchActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);


        String address_arr[] = address.split(" ");
        String query = address_arr[1] + " " + address_arr[2] + " " + address_arr[3];

        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.
        intent.putExtra("keyword",query);

        setResult(1,intent);
        finish();
    }

    class AllAreaControll{
        private Call<CommonMapListResponseVO> mCallCommonMapListResponseVO;

        private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

            callAllArea();
        }


        private void callAllArea() {

            mCallCommonMapListResponseVO = mSearchRetrofitAPI.getAllArea();
            mCallCommonMapListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<CommonMapListResponseVO> mRetrofitCallback = new Callback<CommonMapListResponseVO>() {



            @Override

            public void onResponse(Call<CommonMapListResponseVO> call, Response<CommonMapListResponseVO> response) {

                Log.e("ssss",String.valueOf(response.body().getCode()));

                allList.addAll(response.body().getDataList());

                area_keyword.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_dropdown_item_1line,  allList ));
            }



            @Override

            public void onFailure(Call<CommonMapListResponseVO> call, Throwable t) {

                t.printStackTrace();

            }

        };
    }
}
