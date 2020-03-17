package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.search.searchFragment.LatelyFragment;
import com.dev.eatjeong.main.search.searchFragment.PopularFragment;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserInfoManagementActivity extends AppCompatActivity implements View.OnClickListener {
    String user_id;
    String sns_division;
    Button settings_logout;
    Button settings_login;

    private Retrofit mRetrofit;

    private SettingsRetrofitAPI mSettingsRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    AppCompatTextView nick_name,email,phone_number,password,account_out;

    private PopularFragment popularFragment = new PopularFragment();
    private LatelyFragment latelyFragment = new LatelyFragment();

    private Button my_info;

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_info_management);

        nick_name = findViewById(R.id.nick_name);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        account_out = findViewById(R.id.account_out);

        View action_bar = findViewById(R.id.action_bar);

        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);

        back_text.setText("내정보");
        textview1.setText("내 정보 관리");


        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");

        setRetrofitInit();

        callPlaceInfoResponse();

        nick_name.setOnClickListener(this);
        phone_number.setOnClickListener(this);
        password.setOnClickListener(this);
        account_out.setOnClickListener(this);
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
        mCallCommonMapResponseVO = mSettingsRetrofitAPI.getUserInfo(user_id,sns_division);
        mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
        @Override
        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());
            Log.e("dd", response.body().getDataList().get("nickname"));

            if(response.body().getCode().equals("200")){
                nick_name.setText(response.body().getDataList().get("nickname"));
                email.setText(response.body().getDataList().get("user_id"));
                phone_number.setText(response.body().getDataList().get("phone_number"));
            }

        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {

                setRetrofitInit();

                callPlaceInfoResponse();
                Log.e("LOG", "결과 받기 성공");
            }

        }
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
        switch (v.getId()) {
            case R.id.nick_name:
                Intent nickNameChange = new Intent(UserInfoManagementActivity.this, NickNameChangeActivity.class);
                nickNameChange.putExtra("user_id",user_id);
                nickNameChange.putExtra("sns_division",sns_division);
                nickNameChange.putExtra("nick_name",nick_name.getText().toString());

                startActivityForResult(nickNameChange,0);//액티비티 띄우기
                UserInfoManagementActivity.this.overridePendingTransition(R.anim.fadein,0);
                break;
            case R.id.phone_number:
                Intent phoneNumberChange = new Intent(UserInfoManagementActivity.this, PhoneNumberChangeActivity.class);
                phoneNumberChange.putExtra("user_id",user_id);
                phoneNumberChange.putExtra("sns_division",sns_division);
                phoneNumberChange.putExtra("phone_number",phone_number.getText().toString());

                startActivityForResult(phoneNumberChange,0);//액티비티 띄우기
                UserInfoManagementActivity.this.overridePendingTransition(R.anim.fadein,0);
                break;
            case R.id.password:
                Intent passwordChange = new Intent(UserInfoManagementActivity.this, PasswordChangeActivity.class);
                passwordChange.putExtra("user_id",user_id);
                passwordChange.putExtra("sns_division",sns_division);

                startActivityForResult(passwordChange,0);//액티비티 띄우기
                UserInfoManagementActivity.this.overridePendingTransition(R.anim.fadein,0);
                break;
            case R.id.account_out:
                Intent accountClose = new Intent(UserInfoManagementActivity.this, AccountCloseActivity.class);
                accountClose.putExtra("user_id",user_id);
                startActivityForResult(accountClose,0);//액티비티 띄우기
                UserInfoManagementActivity.this.overridePendingTransition(R.anim.fadein,0);
                break;

        }
    }
}
