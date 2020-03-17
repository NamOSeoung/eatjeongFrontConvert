package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsTermsDetailListResponseVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TermsDetailActivity extends AppCompatActivity {

    private AppCompatTextView tv,header_text;
    private TextView title_text,back_text;
    private ConstraintLayout back_button;
    private ImageButton exit_button;
    private AppCompatSpinner date_selecter;

    String terms_code = "";
    TermsDetailListControll termsDetailListControll = new TermsDetailListControll();
    ArrayList<String> numberStringArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    ArrayList<String> code_list = new ArrayList<>();
    ArrayList<Map<String,String>> termsContentsList = new ArrayList<Map<String, String>>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_detail);

        View action_bar = findViewById(R.id.action_bar);
        exit_button = (ImageButton)action_bar.findViewById(R.id.exit_image);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        title_text = action_bar.findViewById(R.id.textview1);
        back_text = action_bar.findViewById(R.id.back_text);
        tv = (AppCompatTextView) findViewById(R.id.appCompatTextView1);
        date_selecter = findViewById(R.id.date_selecter);
        header_text = findViewById(R.id.header_text);
        back_text.setText("");
        exit_button.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        terms_code = intent.getStringExtra("terms_code");

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                numberStringArray);

//        date_selecter.setAdapter(new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, numberStringArray));
//        date_selecter.setAdapter(new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item,numberStringArray));

        if(terms_code.equals("terms001")){
            title_text.setText("서비스 이용약관");
            header_text.setText("서비스 이용약관");
        }else{
            title_text.setText("개인정보 처리방침");
            header_text.setText("개인정보 처리방침");
        }

        back_text.setText("약관 및 정책");
//        tv.setText(Html.fromHtml(service_terms));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        termsDetailListControll.setRetrofitInit();


        date_selecter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(Html.fromHtml(termsContentsList.get(position).get(code_list.get(position))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_in_left);
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

    public class MySpinner extends AppCompatSpinner {

        public MySpinner(Context context) {
            super(context);
        }

        public MySpinner(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MySpinner(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void setSelection(int position, boolean animate) {

            boolean sameSelected = position == getSelectedItemPosition();
            super.setSelection(position, animate);

            if (sameSelected) {
                // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
                getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());

            }

        }

        @Override

        public void setSelection(int position) {

            //boolean sameSelected = position == getSelectedItemPosition();
            super.setSelection(position);
            //if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
            //}

        }

    }

    //내 리뷰 상세 검색 내부클래스
    public class TermsDetailListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<SettingsTermsDetailListResponseVO> mCallSettingsTermsDetailListResponseVO;

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


            mCallSettingsTermsDetailListResponseVO = mSettingsRetrofitAPI.getTermsDetail(terms_code);
            mCallSettingsTermsDetailListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<SettingsTermsDetailListResponseVO> mRetrofitCallback = new Callback<SettingsTermsDetailListResponseVO>() {
            @Override
            public void onResponse(Call<SettingsTermsDetailListResponseVO> call, Response<SettingsTermsDetailListResponseVO> response) {

                Log.e("message : ", response.body().getMessage());

                if (response.body().getCode().equals("200")) {
                    numberStringArray.clear();
                    Log.e("size",String.valueOf(response.body().mDatalist.get("add_date_list").size()));
                    for(int i = 0; i < response.body().mDatalist.get("add_date_list").size();i++){
                        Map<String, String> temp = new HashMap<>();
                        numberStringArray.add(response.body().mDatalist.get("add_date_list").get(i).get("execute_date"));
//                        adapter.add(response.body().mDatalist.get("add_date_list").get(i).get("execute_date"));
                        temp.put(response.body().mDatalist.get("terms_contents_list").get(i).get("terms_id"),response.body().mDatalist.get("terms_contents_list").get(i).get("terms_contents"));
                        termsContentsList.add(temp);
                        code_list.add(response.body().mDatalist.get("add_date_list").get(i).get("terms_id"));
                    }

                    date_selecter.setAdapter(adapter);
                }

            }

            @Override

            public void onFailure(Call<SettingsTermsDetailListResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }


}
