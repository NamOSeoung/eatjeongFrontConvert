package com.dev.eatjeong.signUp.signUpActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dev.eatjeong.R;
import com.dev.eatjeong.signUp.signUpFragment.PhoneAuthentication;
import com.dev.eatjeong.signUp.signUpFragment.TermsFragment;
import com.dev.eatjeong.signUp.signUpFragment.UserInfoSetUp;

public class SignUpActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity 생명주기";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private TermsFragment termsFragment = new TermsFragment();
    private PhoneAuthentication phoneFragment = new PhoneAuthentication();
    private UserInfoSetUp userInfoSetUp = new UserInfoSetUp();

    private String phone_number= "";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_wrap);
        setFragment();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }


    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.stay, R.anim.sliding_down);
        finish();
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

    public void setFragment() {

        View action_bar = findViewById(R.id.action_bar);
        ConstraintLayout exit_button = action_bar.findViewById(R.id.exit_button);
        ConstraintLayout back_button = action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView title_text = action_bar.findViewById(R.id.textview1);

        back_text.setText("로그인");
        title_text.setText("서비스 이용 약관");
        exit_button.setVisibility(View.INVISIBLE);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 서비스 이용 약관 Fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_wrap, termsFragment).commitAllowingStateLoss();
    }

    public void changeFragment(String display_division, String phone_number ,Fragment fragment) {
        View action_bar = findViewById(R.id.action_bar);
        ConstraintLayout exit_button = action_bar.findViewById(R.id.exit_button);
        ConstraintLayout back_button = action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView title_text = action_bar.findViewById(R.id.textview1);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        switch (display_division) {
            // 휴대폰 인증 화면
            case "phoneAuthentication": {
                title_text.setText("휴대폰인증");
                transaction.replace(R.id.frame_wrap, phoneFragment);
                transaction.commit();
                break;
            }
            case "userInfoSetUp":{
                this.phone_number = phone_number;
                title_text.setText("정보입력");
                transaction.replace(R.id.frame_wrap, userInfoSetUp);
                transaction.commit();
                break;
            }

        }
    }
    public void backLogin(){
        Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public String getPhone_number(){
        return phone_number;
    }
}
