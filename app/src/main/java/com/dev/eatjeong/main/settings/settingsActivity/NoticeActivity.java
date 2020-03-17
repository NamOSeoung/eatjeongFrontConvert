package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.login.LoginActivity;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsNoticeListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsReviewMyListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsReviewOtherListAdapter;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewDetailListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsNoticeListResponseVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoticeActivity extends AppCompatActivity {
    SettingsNoticeListAdapter adapter;

    RecyclerView listView;
    MyReviewListControll myReviewListControll = new MyReviewListControll();
    ProgressBar progress;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notice);
        View action_bar = findViewById(R.id.action_bar);

        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        back_text.setText("내정보");
        textview1.setText("공지사항");

        listView = findViewById(R.id.recycler_view);
        progress = findViewById(R.id.progress);
        myReviewListControll.setRetrofitInit();

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
    public class MyReviewListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<SettingsNoticeListResponseVO> mCallSettingsNoticeListResponseVO;

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


            mCallSettingsNoticeListResponseVO = mSettingsRetrofitAPI.getNotice();
            mCallSettingsNoticeListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<SettingsNoticeListResponseVO> mRetrofitCallback = new Callback<SettingsNoticeListResponseVO>() {
            @Override
            public void onResponse(Call<SettingsNoticeListResponseVO> call, Response<SettingsNoticeListResponseVO> response) {

                Log.e("code : ", response.body().mDatalist.get(0).getSubject());
                Log.e("message : ", response.body().getMessage());

                if (response.body().getCode().equals("200")) {
                    adapter = new SettingsNoticeListAdapter(getApplicationContext(),response.body().mDatalist);
                    listView.setHasFixedSize(true);
                    listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    listView.setAdapter(adapter);
                    progress.setVisibility(View.GONE);
                }

            }

            @Override

            public void onFailure(Call<SettingsNoticeListResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }
}
