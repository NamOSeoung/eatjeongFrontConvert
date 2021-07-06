package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsNoticeListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsTermsListAdapter;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsNoticeListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsTermsListResponseVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TermsActivity extends AppCompatActivity {
    SettingsTermsListAdapter adapter;

    RecyclerView listView;

    TermsListControll myTermsListControll = new TermsListControll();
    ProgressBar progress;

    private ConstraintLayout back_button;
    ArrayList<SettingsTermsListResponseVO.DataList> terms_list = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notice);
        View action_bar = findViewById(R.id.action_bar);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        back_text.setText("내정보");
        textview1.setText("약관 및 정책");

        listView = findViewById(R.id.recycler_view);
        progress = findViewById(R.id.progress);
        myTermsListControll.setRetrofitInit();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //터치를 하고 손을 뗴는 순간 적용되는 이벤트 적용위한 추가.
        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });

        listView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = listView.findChildViewUnder(e.getX(), e.getY());
                int position = listView.getChildAdapterPosition(child);
                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
//                    Toast.makeText(getApplicationContext(),notice_list.get(position).getSubject(),Toast.LENGTH_SHORT).show();
                    Intent termsDetail = new Intent(getApplicationContext(), TermsDetailActivity.class);
                    termsDetail.putExtra("terms_code",terms_list.get(position).getTerms_code());
                    startActivityForResult(termsDetail,0);//액티비티 띄우기
                    overridePendingTransition(R.anim.slide_out_right,R.anim.stay);
//                    Intent goWebview = new Intent(getApplicationContext(), HomeReviewWebviewActivity.class);
//                    goWebview.putExtra("url",arrayList.get(position).getUrl());
//                    startActivityForResult(goWebview,0);//액티비티 띄우기
//                    getActivity().overridePendingTransition(R.anim.sliding_up,R.anim.stay);
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
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


    //내 리뷰 상세 검색 내부클래스
    public class TermsListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<SettingsTermsListResponseVO> mCallSettingsTermsListResponseVO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

            callResponse();

        }

        private void callResponse() {


            mCallSettingsTermsListResponseVO = mSettingsRetrofitAPI.getTerms();
            mCallSettingsTermsListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<SettingsTermsListResponseVO> mRetrofitCallback = new Callback<SettingsTermsListResponseVO>() {
            @Override
            public void onResponse(Call<SettingsTermsListResponseVO> call, Response<SettingsTermsListResponseVO> response) {

                Log.e("message : ", response.body().getMessage());

                if (response.body().getCode().equals("200")) {
                    terms_list.addAll(response.body().mDatalist);
                    adapter = new SettingsTermsListAdapter(getApplicationContext(),response.body().mDatalist);
                    listView.setHasFixedSize(true);
                    listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    listView.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                }

            }

            @Override

            public void onFailure(Call<SettingsTermsListResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_in_left);
    }

}
