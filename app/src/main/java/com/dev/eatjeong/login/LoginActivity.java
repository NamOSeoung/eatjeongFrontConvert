package com.dev.eatjeong.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;
import com.dev.eatjeong.signUp.SignUpActivity;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    /* 카카오 로그인 */
    private SessionCallback sessionCallback;
    private final Handler handler = new Handler();
    public static OAuthLogin mOAuthLoginModule;
    OAuthLoginButton mOAuthLoginButton;
    String accessToken = "";
    String refreshToken = "";
    String deviceToken = "";


    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;



    private Retrofit mRetrofit;

    private LoginRetrofitAPI mLoginRetrofitAPI;

    private Call<LoginResponseVO> mCallLoginResponseVO;

    EditText user_id, password;

    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login_btn = findViewById(R.id.login_button);
        Button emailSignUp = findViewById(R.id.emailSignUp);
        Button kakaoSignUp = findViewById(R.id.kakaoSignUp);
        Button naverSignUp = findViewById(R.id.naverSignUp);
        LinearLayout menu_btn = findViewById(R.id.no_login_button);
        user_id = (EditText) findViewById(R.id.login_id_text);
        password = (EditText) findViewById(R.id.login_password_text);

        DisplayMetrics metrics = new DisplayMetrics();
        Activity activity = this;
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        float density = getResources().getDisplayMetrics().density;
        int dpi = (int)(density * 160f);
        display.getSize(size);
        int width = size.x;
        int widthDp = (int)(width * (160f / (float)dpi));
        int height = size.y;

        Log.i("density", "X " + density);
        Log.i("device dpi", dpi + "");
        Log.i("가로 dp", widthDp + "dp");
        Log.i("width", width + "px");
        Log.i("height", height + "px");
        if (dpi<=160) { // mdpi
            Log.i("device dpi", "mdpi");
        } else if (dpi<=240) { // hdpi
            Log.i("device dpi", "hdpi");
        } else if (dpi<=320) { // xhdpi
            Log.i("device dpi", "xhdpi");
        } else if (dpi<=480) { // xxhdpi
            Log.i("device dpi", "xxhdpi");
        } else if (dpi<=640) { // xxxhdpi
            Log.i("device dpi", "xxxhdpi");
        }

        mContext = getApplicationContext();



        getHashKey(mContext);

        //자동로그인
        autoLogin();

        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(getApplicationContext(), MainWrapActivity.class);
                startActivityForResult(goHome, sub);//액티비티 띄우기
                overridePendingTransition(R.anim.slide_out_right, R.anim.stay);
            }
        });

        kakaoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionCallback = new SessionCallback();
                com.kakao.auth.Session.getCurrentSession().addCallback(sessionCallback);
                com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
                com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK, LoginActivity.this);
            }
        });

        naverSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(getApplicationContext(), "a5jfHfWkZg_Fn8sIdjYD", "UV5L3wTZiT", "장친소");
                naverLogin();
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

        emailSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignUpTerms = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(goSignUpTerms,0);//액티비티 띄우기
                LoginActivity.this.overridePendingTransition(R.anim.fadein,0);
            }
        });
    }


    // 프로젝트의 해시키를 반환

    @Nullable

    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";

        String keyHash = null;

        try {

            PackageInfo info =

                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);



            for (Signature signature : info.signatures) {

                MessageDigest md;

                md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());

                keyHash = new String(Base64.encode(md.digest(), 0));

                Log.d(TAG, keyHash);

            }

        } catch (Exception e) {

            Log.e("name not found", e.toString());

        }



        if (keyHash != null) {

            return keyHash;

        } else {

            return null;

        }

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
                        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        LoginActivity.this.overridePendingTransition(R.anim.slide_out_right, R.anim.stay);
                    }
                }
            }
        }


        @Override

        public void onFailure(Call<LoginResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

    public class SessionCallback implements ISessionCallback {
        MeV2Response finalResult = null;
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    finalResult = result;
                    final String access_token = com.kakao.auth.Session.getCurrentSession().getTokenInfo().getAccessToken();
                    final String refresh_token = com.kakao.auth.Session.getCurrentSession().getTokenInfo().getRefreshToken();
                    UserAccount kakaoAccount = result.getKakaoAccount();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,  kakaoAccount.getEmail(), Toast.LENGTH_SHORT).show();
                            // 가져온 데이터 javascript 메인페이지로 전달
//                            mwv.loadUrl("javascript:setKakaoData("+finalResult+",'"+access_token+"','"+refresh_token+"','"+deviceToken+"')");
                        }

                    });

                    // finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void naverLogin(){


        runOnUiThread(new Runnable() {
            public void run(){
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this,mOAuthLoginHandler);
            }
        });

    }
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                accessToken = mOAuthLoginModule.getAccessToken(mContext); //naver Access token
                refreshToken = mOAuthLoginModule.getRefreshToken(mContext); //naver refresh token
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext); //naver expire time
                String tokenType = mOAuthLoginModule.getTokenType(mContext); //naver token type (Bearer)
                //Toast.makeText(mContext, "accessToken:" + accessToken
                //      + ", refreshToken:" + refreshToken, Toast.LENGTH_SHORT).show();


                // 원래 하고싶었던 일들 (UI변경작업 등...)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),accessToken,Toast.LENGTH_SHORT).show();
                        // 가져온 데이터 javascript 메인페이지로 전달
                       //mwv.loadUrl("javascript:setNaverData('"+accessToken+"','"+refreshToken+"','"+deviceToken+"')");
                    }

                });

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };


}
