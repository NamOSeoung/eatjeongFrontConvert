package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeListAdapter.MainNaverListAdapter;
import com.dev.eatjeong.main.home.homeReviewWebview.HomeReviewWebviewActivity;
import com.dev.eatjeong.main.search.searchActivity.MapSearchActivity;
import com.dev.eatjeong.main.search.searchFragment.PlaceListFragment;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsCategoryListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsReviewListAdapter;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsBlackListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewListResponseVO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAppReviewListActivity extends AppCompatActivity{

    ArrayList<SettingsMyReviewListResponseVO.DataList> myReviews = new ArrayList<SettingsMyReviewListResponseVO.DataList>();
    ArrayList<SettingsMyReviewListResponseVO.DataList> setMyReviews = new ArrayList<SettingsMyReviewListResponseVO.DataList>();

    int listPosition;
    private String portal,write_author,division,review_id;
    private String user_id, sns_division;

    Button youtube_btn,naver_btn,tistory_btn,post_btn,author_btn;

    RecyclerView review_recycler_view,category_recycler_view;

    ProgressBar progressBar;

    SettingsCategoryListAdapter adapter;
    SettingsReviewListAdapter review_adapter;

    MyReviewListControll myReviewListControll = new MyReviewListControll();

    AppCompatTextView review_count;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_my_review_list);

        View action_bar = findViewById(R.id.action_bar);
        TextView back_text = (TextView) action_bar.findViewById(R.id.back_text);
        TextView title_text = action_bar.findViewById(R.id.textview1);

        back_text.setText("내정보");
        title_text.setText("내 리뷰");
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");

        progressBar = (ProgressBar)findViewById(R.id.progress);

        myReviewListControll.setRetrofitInit();

        review_count = findViewById(R.id.review_count);


        ArrayList<Map<String,String>> mapList = new ArrayList<Map<String,String>>();


        List<String> code_list = new ArrayList<String>();
        code_list.add("ALL");
        code_list.add("CT1");
        code_list.add("CT2");
        code_list.add("CT3");
        code_list.add("CT4");
        code_list.add("CT5");
        code_list.add("CT6");
        code_list.add("CT7");
        code_list.add("CT8");
        code_list.add("CE7");

        List<String> name_list = new ArrayList<String>();
        name_list.add("전체");
        name_list.add("한식");
        name_list.add("중식");
        name_list.add("양식");
        name_list.add("일식");
        name_list.add("뷔페");
        name_list.add("술집");
        name_list.add("분식");
        name_list.add("기타");
        name_list.add("카페");


        adapter = new SettingsCategoryListAdapter(getApplicationContext(),code_list,name_list);

        category_recycler_view = (RecyclerView)findViewById(R.id.category_recycler_view);
        review_recycler_view = (RecyclerView)findViewById(R.id.review_recycler_view);


        category_recycler_view.setHasFixedSize(true);
        adapter = new SettingsCategoryListAdapter(getApplicationContext(), code_list,name_list);
        category_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        category_recycler_view.setAdapter(adapter);

        //가로 레이아웃
        LinearLayoutManager horizonalLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        category_recycler_view.setLayoutManager(horizonalLayoutManager);


        //터치를 하고 손을 뗴는 순간 적용되는 이벤트 적용위한 추가.
        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });



        category_recycler_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = category_recycler_view.findChildViewUnder(e.getX(), e.getY());
                int position = category_recycler_view.getChildAdapterPosition(child);
                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
                    int item_count = 0;

                    if(position == 0){
                        item_count = myReviews.size();
                        setMyReviews.addAll(myReviews);

                    }else {
                        setMyReviews.clear();
                        for(int i = 0 ;i<myReviews.size();i++){
                            if(code_list.get(position).equals(myReviews.get(i).getCategory())){
                                item_count++;

                                setMyReviews.add(myReviews.get(i));

                            }
                        }
                    }

                    review_count.setText(String.valueOf(item_count) + "개 리뷰");

                    review_recycler_view.setHasFixedSize(true);
                    review_adapter = new SettingsReviewListAdapter(getApplicationContext(), setMyReviews,code_list.get(position),item_count);
                    review_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    review_recycler_view.setAdapter(review_adapter);
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });

        review_recycler_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = review_recycler_view.findChildViewUnder(e.getX(), e.getY());
                int position = review_recycler_view.getChildAdapterPosition(child);
                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
                    Toast.makeText(getApplicationContext(),String.valueOf(setMyReviews.get(position).getPlace_name()),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MyAppReviewDetailActivity.class);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("sns_division",sns_division);
                    intent.putExtra("place_name",setMyReviews.get(position).getPlace_name());
                    intent.putExtra("place_id",setMyReviews.get(position).getPlace_id());
                    intent.putExtra("review_id",setMyReviews.get(position).getReview_id());
                    startActivityForResult(intent,0);//액티비티 띄우기
                    overridePendingTransition(R.anim.slide_out_right,R.anim.stay);
                }

                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });
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

    //내 리뷰 검색 내부클래스
    public class MyReviewListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<SettingsMyReviewListResponseVO> mCallSettingsMyReviewListResponseVO;

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


            mCallSettingsMyReviewListResponseVO = mSettingsRetrofitAPI.getMyReviews(user_id,sns_division);
            mCallSettingsMyReviewListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<SettingsMyReviewListResponseVO> mRetrofitCallback = new Callback<SettingsMyReviewListResponseVO>() {
            @Override
            public void onResponse(Call<SettingsMyReviewListResponseVO> call, Response<SettingsMyReviewListResponseVO> response) {

                Log.e("code : ", response.body().getCode());
                Log.e("message : ", response.body().getMessage());

                Log.e("review_count",String.valueOf(response.body().mDatalist.size()));

                if(response.body().getCode().equals("200")){
                    myReviews.clear();
                    setMyReviews.clear();
                    myReviews.addAll(response.body().mDatalist);
                    setMyReviews.addAll(myReviews);
                    Log.e("ss",String.valueOf(myReviews.size()));
                    review_count.setText(String.valueOf(myReviews.size()) + "개 리뷰");
                    review_recycler_view.setHasFixedSize(true);
                    review_adapter = new SettingsReviewListAdapter(getApplicationContext(), myReviews,"ALL",myReviews.size());
                    review_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    review_recycler_view.setAdapter(review_adapter);


                }
                progressBar.setVisibility(View.GONE);

            }

            @Override

            public void onFailure(Call<SettingsMyReviewListResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){
                Toast.makeText(getApplicationContext(),"back 정상적으로 호출" , Toast.LENGTH_SHORT).show();
                //뒤로오면 무조건 다시 호출(내 리뷰 디테일 화면에서 리뷰 삭제됬을수도 있기 때문에.)
                myReviewListControll.setRetrofitInit();
            }

        }
    }

}
