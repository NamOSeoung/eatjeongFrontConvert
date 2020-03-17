package com.dev.eatjeong.main.settings.settingsActivity;

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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.layout.ClearEditText;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PasswordChangeActivity extends AppCompatActivity {
    private String user_id, sns_division;
    private Retrofit mRetrofit;

    private SettingsRetrofitAPI mSettingsRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    Button confirm;
    ClearEditText password,password_check;
    private ConstraintLayout back_button,exit_button;

    AppCompatTextView password_message,password_check_message;

    boolean password_flag = false;
    boolean password_check_flag = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.password_change);

        View action_bar = findViewById(R.id.action_bar);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        exit_button = action_bar.findViewById(R.id.exit_button);
        back_text.setText("뒤로가기");
        textview1.setText("비밀번호 변경");
        exit_button.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();

        password = findViewById(R.id.password);
        password_message = findViewById(R.id.password_message);
        password_check_message = findViewById(R.id.password_check_message);
        password_check = findViewById(R.id.password_check);
        confirm = (Button)findViewById(R.id.confirm);

        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password_flag&&password_check_flag){
                    setRetrofitInit();
                }

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String password_edit = password.getText().toString();
                String password_check_edit = password_check.getText().toString();;

                password_flag = false;
                password_check.setText("");

                if(password_edit.length()>=8){
                    if(getPasswordCheck()){ //비밀번호 체크 통과 시 숨김
                        password_message.setVisibility(View.INVISIBLE);
                        password_flag = true;
                        if(password_check_flag){
                            confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_true));
                        }
                    }else{ //비밀번호 체크 미 통과시 문구 출력
                        password_message.setTextColor(Color.parseColor("#DB522B"));
                        password_message.setText("8~15자 이내로 영문,숫자,특수문자 중 2가지 이상 조합으로 입력해주세요.");
                        password_message.setVisibility(View.VISIBLE);
                        password_flag = false;
                        confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                    }
                }else if(password.length() == 0){
                    password_message.setTextColor(Color.parseColor("#888888"));
                    password_message.setText("8~15자 이내로 영문,숫자,특수문자 중 2가지 이상 조합으로 입력해주세요.");
                    password_message.setVisibility(View.VISIBLE);
                    password_flag = false;
                    confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                }else if(password_edit.length()>0&&password_edit.length()<8){
                    password_message.setTextColor(Color.parseColor("#DB522B"));
                    password_message.setText("8~15자 이내로 영문,숫자,특수문자 중 2가지 이상 조합으로 입력해주세요.");
                    password_message.setVisibility(View.VISIBLE);
                    password_flag = false;
                    confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                }
            }
        });


        password_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password_edit = password.getText().toString();
                String password_check_edit = password_check.getText().toString();


                if(password_flag){ // 상단 비밀번호 입력박스 통과시에만 비밀번호 동일여부 체크 진행.
                    if(!password_edit.equals(password_check_edit)){
                        password_check_message.setVisibility(View.VISIBLE);
                        password_check_flag = false;
                        confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                    }else {
                        password_check_flag = true;
                        password_check_message.setVisibility(View.INVISIBLE);
                        if(password_flag){
                            confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_true));
                        }
                    }
                }else{
                    password_check_flag = false;
                    confirm.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.signup_next_button_false));
                }
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

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);
        callPlaceInfoResponse();
    }

    private void callPlaceInfoResponse() {
        mCallCommonMapResponseVO = mSettingsRetrofitAPI.updatePassword(user_id,sns_division,password.getText().toString());
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
        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();

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

    public boolean getPasswordCheck()
    {
        String password_edit = password.getText().toString();
        String passwordStr[] = password_edit.split("");
        final String specialString = "^[a-zA-Z0-9]*$"; // 특수문자만
        final String engString = ".*[a-zA-Z]+.*"; // 영문자
        final String numberString = ".*[0-9]+.*"; // 숫자만

        boolean check = false;

        if(!password_edit.matches(specialString)){
            if(password_edit.matches(engString)){
                check = true;
            }
            if(password_edit.matches(numberString)){
                check = true;
            }
        }else if(password_edit.matches(engString)){
            if(!password_edit.matches(specialString)){
                check = true;
            }
            if(password_edit.matches(numberString)){
                check = true;
            }
        }else if(password_edit.matches(numberString)){
            if(password_edit.matches(engString)){
                check = true;
            }
            if(!password_edit.matches(specialString)){
                check = true;
            }
        }

        return check;
    }

}
