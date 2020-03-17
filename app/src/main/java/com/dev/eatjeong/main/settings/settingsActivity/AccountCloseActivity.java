package com.dev.eatjeong.main.settings.settingsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.layout.ClearEditText;
import com.dev.eatjeong.login.LoginActivity;
import com.dev.eatjeong.main.search.searchFragment.PlaceListFragment;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AccountCloseActivity extends AppCompatActivity {
    private String user_id, sns_division;
    private Retrofit mRetrofit;
    ProgressDialog progressDialog;
    private SettingsRetrofitAPI mSettingsRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    ClearEditText password;
    AppCompatButton confirm,cancel;
    AppCompatTextView password_message;
    private ConstraintLayout back_button,exit_button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_account_close);
        View action_bar = findViewById(R.id.action_bar);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        exit_button = action_bar.findViewById(R.id.exit_button);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        password_message = findViewById(R.id.password_message);
        password = findViewById(R.id.password);
        back_text.setText("뒤로가기");
        textview1.setText("탈퇴안내");
        exit_button.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();

        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRetrofitInit();

            }
        });
//
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRetrofitInit();
//
//                callPlaceInfoResponse();
//            }
//        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        progressDialog.setMessage("탈퇴 처리중입니다.");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        progressDialog.show();
        mCallCommonMapResponseVO = mSettingsRetrofitAPI.setAccountClose(user_id,password.getText().toString());
        mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
        @Override
        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());

            if(response.body().getCode().equals("200")){
                if(response.body().getDataList().get("result_flag").equals("true")){ //비번 통과시
                    Intent intent = new Intent(getApplicationContext(), AccountClosePopupActivity.class);
                    intent.putExtra("data", "Test Popup");
                    startActivityForResult(intent, 1);
                }else{
                    password_message.setVisibility(View.VISIBLE);
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 1){
                SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();

                editor.clear();

                editor.commit();


                //로그인 화면으로 돌아가면서 기존 액티비티 스택 다 지움 (생명주기 영향x)
                Intent login = new Intent(AccountCloseActivity.this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(login);//액티비티 띄우기
                AccountCloseActivity.this.overridePendingTransition(R.anim.slide_in_left,0);
            }

        }
    }
}
