package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeListAdapter.MainNaverListAdapter;
import com.dev.eatjeong.main.home.homeReviewWebview.HomeReviewWebviewActivity;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsListAdapter;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsBlackListResponseVO;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlackListActivity extends AppCompatActivity implements View.OnClickListener {


    int listPosition;
    private String portal,write_author,division,review_id;
    private String user_id, sns_division;

    Button youtube_btn,naver_btn,tistory_btn,post_btn,author_btn;

    RecyclerView listView;

    ProgressBar progressBar;

    public ArrayList<Map<String,String>> authorBlackListYoutube = new ArrayList<>();
    ArrayList<Map<String,String>> authorBlackListNaver = new ArrayList<>();
    ArrayList<Map<String,String>> authorBlackListTistory = new ArrayList<>();

    ArrayList<Map<String,String>> postBlackListYoutube = new ArrayList<>();
    ArrayList<Map<String,String>> postBlackListNaver = new ArrayList<>();
    ArrayList<Map<String,String>> postBlackListTistory = new ArrayList<>();

    SettingsListAdapter adapter;

    /*
    *
    * SET_CODE = 0 : 유튜브 게시글, 1 : 유튜브 게시자 , 2 : 네이버 게시글 , 3 : 네이버 게시자 , 4 : 티스토리 게시글 , 5 : 티스토리 게시자
    *
    *  */
    int SET_CODE = 0;

    /*
    *
    * WRITE_TYPE_DIVISION = 0: 게시글 , 1 : 게시자
    *
    * */

    /*
     *
     * PORTAR_DIVISIONT = 0: 유튜브 , 1 : 네이버 , 2 : 티스토리
     *
     * */

    int WRITE_TYPE_DIVISION = 0;
    int PORTAR_DIVISIONT = 0;

    /*
    *
    *
    * DELETE_FLAG = 0 : 미삭제 , 1 : 삭제
    *
    *  */
    int DELETE_FLAG = 0;

    BlackListControll blackListControll = new BlackListControll();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_black_list);


        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");

        youtube_btn = (Button)findViewById(R.id.youtube_btn);
        naver_btn = (Button)findViewById(R.id.naver_btn);
        tistory_btn = (Button)findViewById(R.id.tistory_btn);

        post_btn = (Button)findViewById(R.id.post_btn);
        author_btn = (Button)findViewById(R.id.author_btn);

        progressBar = (ProgressBar)findViewById(R.id.progress);

        blackListControll.setRetrofitInit();

        youtube_btn.setOnClickListener(this);
        naver_btn.setOnClickListener(this);
        tistory_btn.setOnClickListener(this);

        post_btn.setOnClickListener(this);
        author_btn.setOnClickListener(this);

        listView = (RecyclerView) findViewById(R.id.recycler_view);

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
    public void onBackPressed() {
        // 검색 동작
        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

        setResult(1,intent);
        // finish();
        super.onBackPressed();

    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.youtube_btn:
                PORTAR_DIVISIONT = 0;
                if(WRITE_TYPE_DIVISION == 0){
                    SET_CODE = 0;
                }else {
                    SET_CODE = 1;
                }
                break;
            case R.id.naver_btn:
                PORTAR_DIVISIONT = 1;
                if(WRITE_TYPE_DIVISION == 0){
                    SET_CODE = 2;
                }else {
                    SET_CODE = 3;
                }
                break;
            case R.id.tistory_btn:
                PORTAR_DIVISIONT = 2;
                if(WRITE_TYPE_DIVISION == 0){
                    SET_CODE = 4;
                }else {
                    SET_CODE = 5;
                }
                break;
            case R.id.post_btn:
                WRITE_TYPE_DIVISION = 0;
                if(PORTAR_DIVISIONT == 0){
                    SET_CODE = 0;
                }else if(PORTAR_DIVISIONT == 1){
                    SET_CODE = 2;
                }else if(PORTAR_DIVISIONT == 2){
                    SET_CODE = 4;
                }
                break;
            case R.id.author_btn:
                WRITE_TYPE_DIVISION = 1;
                if(PORTAR_DIVISIONT == 0){
                    SET_CODE = 1;
                }else if(PORTAR_DIVISIONT == 1){
                    SET_CODE = 3;
                }else if(PORTAR_DIVISIONT == 2){
                    SET_CODE = 5;
                }
                break;
        }
        blackListControll.setRetrofitInit();
    }

    //플랫폼별 블랙리스트 검색 내부클래스
    public class BlackListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<SettingsBlackListResponseVO> mCallSettingsBlackListResponseVO;

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


            mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

            callResponse();

        }

        private void callResponse() {

            if(DELETE_FLAG == 0){
                mCallSettingsBlackListResponseVO = mSettingsRetrofitAPI.getBlackList(user_id,sns_division);
            }else if(DELETE_FLAG == 1){

                if(SET_CODE == 0){
                    review_id = postBlackListYoutube.get(listPosition).get("review_id");
                    write_author = postBlackListYoutube.get(listPosition).get("author");
                }else if(SET_CODE == 1){
                    review_id = authorBlackListYoutube.get(listPosition).get("review_id");
                    write_author = authorBlackListYoutube.get(listPosition).get("author");
                }else if(SET_CODE == 2){
                    review_id = postBlackListNaver.get(listPosition).get("review_id");
                    write_author = postBlackListNaver.get(listPosition).get("author");
                }else if(SET_CODE == 3){
                    review_id = authorBlackListNaver.get(listPosition).get("review_id");
                    write_author = authorBlackListNaver.get(listPosition).get("author");
                }else if(SET_CODE == 4){
                    review_id = postBlackListTistory.get(listPosition).get("review_id");
                    write_author = postBlackListTistory.get(listPosition).get("author");
                }else if(SET_CODE == 5){
                    review_id = authorBlackListTistory.get(listPosition).get("review_id");
                    write_author = authorBlackListTistory.get(listPosition).get("author");
                }
                if(PORTAR_DIVISIONT == 0){
                    portal = "youtube";
                }else if(PORTAR_DIVISIONT == 1){
                    portal = "naver";
                }else if(PORTAR_DIVISIONT == 2){
                    portal = "daum";
                }

                if(WRITE_TYPE_DIVISION == 0){ //게시글
                    division = "post";
                }else if(WRITE_TYPE_DIVISION == 1){ //게시자
                    division = "author";
                }

                mCallSettingsBlackListResponseVO = mSettingsRetrofitAPI.deleteBlackList(user_id,sns_division,portal,write_author,division,review_id);
            }

            mCallSettingsBlackListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<SettingsBlackListResponseVO> mRetrofitCallback = new Callback<SettingsBlackListResponseVO>() {
            @Override
            public void onResponse(Call<SettingsBlackListResponseVO> call, Response<SettingsBlackListResponseVO> response) {

                Log.e("code : ", response.body().getCode());
                Log.e("message : ", response.body().getMessage());

                if(DELETE_FLAG == 1){
                    DELETE_FLAG = 0;
                    progressBar.setVisibility(View.VISIBLE);
                    blackListControll.setRetrofitInit();
                }else {
                    progressBar.setVisibility(View.GONE);
                    if(response.body().getCode().equals("200")){
                        authorBlackListYoutube.clear();
                        authorBlackListNaver.clear();
                        authorBlackListTistory.clear();
                        postBlackListYoutube.clear();
                        postBlackListNaver.clear();
                        postBlackListTistory.clear();


                        if(response.body().getDataList().get("author_blacklist_youtube").size()>0){
                            authorBlackListYoutube.addAll(response.body().getDataList().get("author_blacklist_youtube"));
                        }
                        if(response.body().getDataList().get("author_blacklist_naver").size()>0){
                            authorBlackListNaver.addAll(response.body().getDataList().get("author_blacklist_naver"));
                        }
                        if(response.body().getDataList().get("author_blacklist_tistory").size()>0){
                            authorBlackListTistory.addAll(response.body().getDataList().get("author_blacklist_tistory"));
                        }
                        if(response.body().getDataList().get("post_blacklist_youtube").size()>0){
                            postBlackListYoutube.addAll(response.body().getDataList().get("post_blacklist_youtube"));
                        }
                        if(response.body().getDataList().get("post_blacklist_naver").size()>0){
                            postBlackListNaver.addAll(response.body().getDataList().get("post_blacklist_naver"));
                        }
                        if(response.body().getDataList().get("post_blacklist_tistory").size()>0){
                            postBlackListTistory.addAll(response.body().getDataList().get("post_blacklist_tistory"));
                        }


                    }

                    Log.e("set_code : ", String.valueOf(SET_CODE));
                    if(SET_CODE == 0 ){
                        adapter = new SettingsListAdapter(getApplicationContext(),postBlackListYoutube,"post");
                    }else if(SET_CODE == 1){
                        adapter = new SettingsListAdapter(getApplicationContext(),authorBlackListYoutube,"author");
                    }else if(SET_CODE == 2){
                        adapter = new SettingsListAdapter(getApplicationContext(),postBlackListNaver,"post");
                    }else if(SET_CODE == 3){
                        adapter = new SettingsListAdapter(getApplicationContext(),authorBlackListNaver,"author");
                    }else if(SET_CODE == 4){
                        adapter = new SettingsListAdapter(getApplicationContext(),postBlackListTistory,"post");
                    }else if(SET_CODE == 5){
                        adapter = new SettingsListAdapter(getApplicationContext(),authorBlackListTistory,"author");
                    }



                    listView.setHasFixedSize(true);
                    listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    listView.setAdapter(adapter);

                    adapter.setItemClick(new SettingsListAdapter.ItemClick() {
                        @Override
                        public void onClick(View view, int position) {
                            //클릭시 실행될 함수 작성
                            Log.e("position",String.valueOf(position));
                            listPosition = position;
                            DELETE_FLAG = 1;
                            blackListControll.setRetrofitInit();
                        }
                    });
                }
            }

            @Override

            public void onFailure(Call<SettingsBlackListResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }
}
