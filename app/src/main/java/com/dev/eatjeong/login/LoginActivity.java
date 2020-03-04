package com.dev.eatjeong.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.PopularListAdapter;
import com.dev.eatjeong.main.search.PopularVO;
import com.dev.eatjeong.main.search.SearchResponseVO;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {


    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;



    private Retrofit mRetrofit;

    private LoginRetrofitAPI mLoginRetrofitAPI;

    private Call<LoginResponseVO> mCallLoginResponseVO;

    EditText user_id, password;

    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login_btn = findViewById(R.id.login_btn);
        Button menu_btn = findViewById(R.id.menu_btn);
        user_id = (EditText) findViewById(R.id.user_id);
        password = (EditText) findViewById(R.id.password);

        //자동로그인
        autoLogin();

        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(getApplicationContext(), MainWrapActivity.class);
                startActivityForResult(goHome, sub);//액티비티 띄우기
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                    return;
                }


                //레트로핏 초기화 후 호출작업 진행.
                callSearchResponse(user_id.getText().toString(), password.getText().toString());

            }
        });
    }


    public void autoLogin(){

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

        //자동 로그인
        if(sp.getString("user_id",null) != null){
            Intent goHome = new Intent(getApplicationContext(), MainWrapActivity.class);
            startActivityForResult(goHome, sub);//액티비티 띄우기
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }

    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            finishAffinity();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

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


        mLoginRetrofitAPI = mRetrofit.create(LoginRetrofitAPI.class);

    }

    private void callSearchResponse(String user_id, String password) {

        mCallLoginResponseVO = mLoginRetrofitAPI.getGeneralUserCheck(user_id, password);

        mCallLoginResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<LoginResponseVO> mRetrofitCallback = new Callback<LoginResponseVO>() {


        //요청 후 콜백부분

        @Override

        public void onResponse(Call<LoginResponseVO> call, Response<LoginResponseVO> response) {

            if (response.body().getDataList().get("result") == null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                builder.setTitle("").setMessage("이메일이나 비밀번호가 다릅니다.");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            } else {
                if (response.body().getDataList().get("result").equals("false")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                    builder.setTitle("").setMessage("이메일이나 비밀번호가 다릅니다.");

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();

                } else {
                    if (response.body().getDataList().get("login90_flag").equals("false")) {
                        Toast.makeText(getApplicationContext(), "90일경과", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

                        SharedPreferences.Editor editor = sp.edit();

                        editor.putString("user_id",user_id.getText().toString());

                        editor.putString("sns_division","C");

                        editor.commit();

                        Intent goHome = new Intent(getApplicationContext(), MainWrapActivity.class);
                        startActivityForResult(goHome, sub);//액티비티 띄우기
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                }
            }
        }


        @Override

        public void onFailure(Call<LoginResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

}
