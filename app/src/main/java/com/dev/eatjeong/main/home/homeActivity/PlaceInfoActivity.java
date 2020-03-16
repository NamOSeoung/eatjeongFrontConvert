package com.dev.eatjeong.main.home.homeActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.bookmark.BookmarkRetrofitAPI;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchFragment.LatelyFragment;
import com.dev.eatjeong.main.search.searchFragment.PopularFragment;
import com.dev.eatjeong.main.search.searchListVO.PlaceListVO;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlaceInfoActivity extends AppCompatActivity {
    String user_id, sns_division;
    double latitude = 0.0;
    double longitude = 0.0;

    String info_place_name = "";
    String info_place_id = "";
    String info_place_address = "";
    String bookmark_flag = "";

    private ArrayList<PlaceListVO> arrayList = new ArrayList<PlaceListVO>();
    private Retrofit mRetrofit;
    private SearchRetrofitAPI mSearchRetrofitAPI;
    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    TextView place_name, category, address;

    private PopularFragment popularFragment = new PopularFragment();
    private LatelyFragment latelyFragment = new LatelyFragment();

    private Button place_bookmark_add, place_bookmark_delete;

    private ProgressBar place_info_progress;

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        info_place_name = intent.getStringExtra("place_name");
        info_place_id = intent.getStringExtra("place_id");
        info_place_address = intent.getStringExtra("place_address");

        latitude = Double.parseDouble(intent.getStringExtra("latitude"));
        longitude = Double.parseDouble(intent.getStringExtra("longitude"));

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        user_id = sp.getString("user_id", null);
        sns_division = sp.getString("sns_division", null);

        setContentView(R.layout.place_info);

        place_name = (TextView) findViewById(R.id.place_name);
        category = (TextView) findViewById(R.id.category);
        address = (TextView) findViewById(R.id.address);
//        place_bookmark_add = (Button)findViewById(R.id.place_bookmark_add);
//        place_bookmark_delete = (Button)findViewById(R.id.place_bookmark_delete);

        place_info_progress = (ProgressBar) findViewById(R.id.place_info_progress);


        // Todo:북마크 버튼 연결
//        place_bookmark_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                BookmarkControll bookmarkControll = new BookmarkControll();
//                bookmarkControll.setRetrofitInit();
//            }
//        });
//
//        place_bookmark_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                BookmarkControll bookmarkControll = new BookmarkControll();
//                bookmarkControll.setRetrofitInit();
//            }
//        });

//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.youtube_frame_layout, popularFragment).commitAllowingStateLoss();
//        transaction.replace(R.id.naver_frame_layout, latelyFragment).commitAllowingStateLoss();


        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callPlaceInfoResponse();

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);

// 줌 레벨 변경
        mapView.setZoomLevel(3, true);

// 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 3, true);

// 줌 인
        mapView.zoomIn(true);

// 줌 아웃
        mapView.zoomOut(true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

    }


    public void goWebview() {
        Toast.makeText(getApplicationContext(), "asdas", Toast.LENGTH_SHORT).show();
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

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

    }

    private void callPlaceInfoResponse() {
        if (user_id == null) {
            user_id = "temp";
            sns_division = "T";
        }
        mCallCommonMapResponseVO = mSearchRetrofitAPI.getPlaceInfo(info_place_id, user_id, sns_division, String.valueOf(latitude), String.valueOf(longitude));
        mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
        @Override
        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());
            Log.e("dd", response.body().getDataList().get("road_address"));

            place_name.setText(response.body().getDataList().get("place_name"));
            category.setText(response.body().getDataList().get("category"));
            address.setText(response.body().getDataList().get("address"));
            bookmark_flag = response.body().getDataList().get("bookmark_flag");

            // Todo:북마크 체크 로직
//            if(bookmark_flag.equals("true")){
//                place_bookmark_delete.setVisibility(View.VISIBLE);
//                place_bookmark_add.setVisibility(View.GONE);
//            }else{
//                place_bookmark_delete.setVisibility(View.GONE);
//                place_bookmark_add.setVisibility(View.VISIBLE);
//            }

            place_info_progress.setVisibility(View.GONE);

        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();

        }
    };

    public Map<String, String> getPlaceInfo() {
        Map<String, String> placeInfo = new HashMap();

        placeInfo.put("place_name", info_place_name);
        placeInfo.put("place_id", info_place_id);
        placeInfo.put("place_address", info_place_address);

        return placeInfo;
    }

    public Map<String, String> getUserInfo() {
        Map<String, String> userInfo = new HashMap();

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        userInfo.put("user_id", sp.getString("user_id", null));
        userInfo.put("sns_division", sp.getString("sns_division", null));

        return userInfo;
    }

    public class BookmarkControll {

        String code = "";

        private Retrofit mRetrofit;

        private BookmarkRetrofitAPI mBookmarkRetrofitAPI;

        private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

            if (user_id == null) {
                return;
            }
            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mBookmarkRetrofitAPI = mRetrofit.create(BookmarkRetrofitAPI.class);

            callPlaceInfoResponse();

        }

        private void callPlaceInfoResponse() {
            if (bookmark_flag.equals("true")) {
                mCallCommonMapResponseVO = mBookmarkRetrofitAPI.deleteBookmarkPlace("place", info_place_id, user_id, sns_division);
            } else {
                mCallCommonMapResponseVO = mBookmarkRetrofitAPI.setBookmarkPlace("place", info_place_id, user_id, sns_division);
            }

            mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
            @Override
            public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {

                Log.e("code : ", response.body().getCode());
                Log.e("code : ", response.body().getMessage());
                if (response.body().getCode().equals("200")) {
                    if (bookmark_flag.equals("true")) {
                        place_bookmark_add.setVisibility(View.VISIBLE);
                        place_bookmark_delete.setVisibility(View.GONE);
                        bookmark_flag = "false";
                    } else {
                        place_bookmark_add.setVisibility(View.GONE);
                        place_bookmark_delete.setVisibility(View.VISIBLE);
                        bookmark_flag = "true";
                    }

                }
//            setCode(response.body().getCode());

            }

            @Override

            public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

//            Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }
}
