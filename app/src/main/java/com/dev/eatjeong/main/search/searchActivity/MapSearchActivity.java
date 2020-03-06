package com.dev.eatjeong.main.search.searchActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapSearchActivity extends AppCompatActivity {

    Button area_cancel,area_search;
    EditText area_keyword;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_search);

        area_cancel = (Button)findViewById(R.id.area_cancel);
        area_search = (Button)findViewById(R.id.area_search);

        area_keyword = (EditText)findViewById(R.id.area_keyword);

        main_area_list = (ListView)findViewById(R.id.main_area_list);
        sub_area_list = (ListView)findViewById(R.id.sub_area_list);

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
                        android.R.layout.simple_list_item_1, list);

                main_area_list.setAdapter(adapter);
            }else{
                final ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < response.body().mDatalist.size(); i++) {
                    list.add(response.body().mDatalist.get(i).getArea_name());
                }

                adapter = new StableArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_list_item_1, list);

                sub_area_list.setAdapter(adapter);
                subSearchFlag = false;
            }

        }



        @Override

        public void onFailure(Call<SearchAreaListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

}
