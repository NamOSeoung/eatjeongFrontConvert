package com.dev.eatjeong.main.settings.settingsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NickNameChangeActivity extends AppCompatActivity {
    private String user_id, sns_division;
    private Retrofit mRetrofit;

    private SettingsRetrofitAPI mSettingsRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    EditText nick_name;
    AppCompatTextView nick_name_validation;
    AppCompatButton confirm;
    boolean nickname_flag = false;
    private ConstraintLayout back_button,exit_button;
    ProgressDialog progressDialog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nickname_change);
        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();

        View action_bar = findViewById(R.id.action_bar);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        exit_button = action_bar.findViewById(R.id.exit_button);
        back_text.setText("뒤로가기");
        textview1.setText("닉네임 수정");
        exit_button.setVisibility(View.INVISIBLE);
        nick_name = (EditText)findViewById(R.id.nick_name);
        confirm = findViewById(R.id.confirm);
        nick_name_validation = findViewById(R.id.nick_name_validation);

        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");
        nick_name.setText(intent.getStringExtra("nick_name"));

        nick_name.setSelection(nick_name.length()); // 포커스 맨뒤로

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nickname_flag){
                    setRetrofitInit();

                    callPlaceInfoResponse();
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(nick_name.getText().toString().length()<2){
                    nick_name_validation.setTextColor(Color.parseColor("#888888"));
                    nick_name_validation.setText("한글, 영문, 숫자 2 ~ 10자");
                    confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                    nickname_flag = false;
                }else {
                    if(!hasSpecialCharacter()){//특수문자 존재함
                        nick_name_validation.setTextColor(Color.parseColor("#DB522B"));
                        nick_name_validation.setText("특수문자를 제외한 한글, 영문, 숫자 2 ~ 10자");
                        confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                        nickname_flag = false;
                    }else { //특수문자 미존재
                        nick_name_validation.setText("");
                        confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_true));
                        nickname_flag = true;
                    }
                }
            }
        });


    }

    public boolean hasSpecialCharacter() {
        boolean bool = false;

        // 공백 포함 특수문자 체크
        Pattern pattern1 = Pattern.compile("[ !@#$%^&*(),.?\":{}|<>]");

        if(pattern1.matcher(nick_name.getText().toString()).find()){
            bool = false;
        }else {
            bool = true;
        }

        return bool;
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


        mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

    }

    private void callPlaceInfoResponse() {


        progressDialog.setMessage("변경중입니다.");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        progressDialog.show();
        mCallCommonMapResponseVO = mSettingsRetrofitAPI.updateNickName(user_id,nick_name.getText().toString());
        mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
        @Override
        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());

            if(response.body().getCode().equals("200")){
                onBackPressed();
            }
            progressDialog.dismiss();
        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();
            progressDialog.dismiss();
        }
    };

    @Override
    public void onBackPressed() {
        // 검색 동작
        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

        setResult(1,intent);
        // finish();
        super.onBackPressed();


    }
}
