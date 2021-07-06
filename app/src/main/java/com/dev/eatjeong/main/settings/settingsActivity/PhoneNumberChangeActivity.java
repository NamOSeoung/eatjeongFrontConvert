package com.dev.eatjeong.main.settings.settingsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.layout.ClearEditText;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.signUp.signUpActivity.SignUpActivity;
import com.dev.eatjeong.signUp.signUpFragment.PhoneAuthentication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PhoneNumberChangeActivity extends AppCompatActivity implements View.OnClickListener {
    private Retrofit mRetrofit;

    private SettingsRetrofitAPI mSettingsRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;


    CountDownTimer timer;

    FirebaseAuth mAuth;
    FirebaseUser user;
    String codeSent;
    String phoneNumber;
    String phone;

    EditText editTextPhone, editTextCode;
    Button getVerification, signIn,signOut, resend_button;
    boolean timeout_check = false;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    //키보드 관련
    InputMethodManager imm;
    private String TAG = "PhoneAuthFragment 생명주기";
    private ClearEditText phone_number_edittext,apply_number_edtitext;
    private AppCompatButton next_appCompatButton_true,next_appCompatButton_false,phone_number_apply_button_false,phone_number_apply_button_true,phone_number_reapply_button;

    private AppCompatTextView remain_textview2,phone_number_text,apply_false_textview;
    private ConstraintLayout authentication_response_layout;
    private boolean code_send_flag;
    ProgressDialog progressDialog;
    //phone_validation = 0 : 올바른 휴대폰 번호 ,1 : 올바르지 않은 휴대폰 번호 ,2
    int phone_validation = 0;
    String user_id = "";
    String sns_division = "";
    boolean phone_flag = false;
    private ConstraintLayout back_button,exit_button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.phone_number_change);

        View action_bar = findViewById(R.id.action_bar);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        exit_button = action_bar.findViewById(R.id.exit_button);
        back_text.setText("뒤로가기");
        textview1.setText("휴대폰 번호 변경");
        exit_button.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();

        phone_number_edittext = findViewById(R.id.phone_number_edittext);
        next_appCompatButton_true = findViewById(R.id.phone_next_appCompatButton_true);

        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");
        mAuth = FirebaseAuth.getInstance();
        remain_textview2 =findViewById(R.id.remain_textview2);
        apply_false_textview =findViewById(R.id.apply_false_textview);
        phone_number_text = findViewById(R.id.phone_number_text);
        authentication_response_layout = findViewById(R.id.authentication_response_layout);
        phone_number_edittext = findViewById(R.id.phone_number_edittext);
        imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE); //키보드 관련.


        progressDialog = new ProgressDialog(this);
        next_appCompatButton_true = findViewById(R.id.phone_next_appCompatButton_true);
        next_appCompatButton_false = findViewById(R.id.phone_next_appCompatButton_false);
        phone_number_apply_button_false = findViewById(R.id.phone_number_apply_button_false); //인증하기 버튼 비활성화
        phone_number_apply_button_true = findViewById(R.id.phone_number_apply_button_true); //인증하기 버튼 활성화
        phone_number_reapply_button = findViewById(R.id.phone_number_reapply_button); //재전송 버튼

        apply_number_edtitext = findViewById(R.id.apply_number_edtitext); //인증번호 기입 란

        phone_number_reapply_button.setVisibility(View.INVISIBLE);
        phone_number_apply_button_false.setVisibility(View.VISIBLE);
        phone_number_apply_button_true.setVisibility(View.INVISIBLE);

        next_appCompatButton_true.setVisibility(View.INVISIBLE);
        next_appCompatButton_false.setVisibility(View.VISIBLE);

        authentication_response_layout.setVisibility(View.GONE);

        apply_false_textview.setVisibility(View.GONE);
        phone_number_edittext.setOnClickListener(this);
        phone_number_apply_button_true.setOnClickListener(this);
        next_appCompatButton_false.setOnClickListener(this);
        next_appCompatButton_true.setOnClickListener(this);
        phone_number_reapply_button.setOnClickListener(this);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        phone_number_edittext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        phone_number_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String phone_init = phone_number_edittext.getText().toString().replaceAll("-","");
                if(phone_number_edittext.getText().length() == 1){
                    if(Integer.parseInt(phone_number_edittext.getText().toString())!=0){
                        phone_number_text.setText("올바른 휴대폰번호를 입력 해 주세요.");
                        phone_validation = 1;
                        return;
                    }else {
                        phone_validation = 0;
                        phone_number_text.setText("");
                        // 아이디, 비밀번호 찾기 시 필요한 정보입니다.
                    }
                }


                // 입력되는 텍스트에 변화가 있을 때
                if(phone_number_edittext.getText().toString().length() > 12){
                    if(phone_validation==0){
                        phone_number_reapply_button.setVisibility(View.INVISIBLE);
                        phone_number_apply_button_false.setVisibility(View.INVISIBLE);
                        phone_number_apply_button_true.setVisibility(View.VISIBLE);
                        phone_number_edittext.setCursorVisible(false);
                        hideKeyboard();
                    }
                }else if(phone_number_edittext.getText().toString().length() < 13 && phone_number_edittext.getText().toString().length() > 0){
                    authentication_response_layout.setVisibility(View.GONE);
                    phone_number_reapply_button.setVisibility(View.INVISIBLE);
                    phone_number_apply_button_false.setVisibility(View.VISIBLE);
                    phone_number_apply_button_true.setVisibility(View.INVISIBLE);
                }else {
                    authentication_response_layout.setVisibility(View.GONE);
                    phone_number_reapply_button.setVisibility(View.INVISIBLE);
                    phone_number_apply_button_false.setVisibility(View.VISIBLE);
                    phone_number_apply_button_true.setVisibility(View.INVISIBLE);
                    phone_number_text.setText("아이디, 비밀번호 찾기 시 필요한 정보입니다.");
                }


            }
            @Override
            public void afterTextChanged(Editable arg0) {

                // 입력이 끝났을 때

            }

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // 입력하기 전에

            }
        });

        apply_number_edtitext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(apply_number_edtitext.length()>5){ //인증번호 코드가 6자리 되면 적용
                    next_appCompatButton_true.setVisibility(View.VISIBLE);
                    next_appCompatButton_false.setVisibility(View.INVISIBLE);
                }else{
                    next_appCompatButton_true.setVisibility(View.INVISIBLE);
                    next_appCompatButton_false.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        mCallCommonMapResponseVO = mSettingsRetrofitAPI.updatePhoneNumber(user_id,phone_number_edittext.getText().toString());
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

    public void countDown(String time) {

        long conversionTime = 0;

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간

        String getHour = time.substring(0, 2);
        String getMin = time.substring(2, 4);
        String getSecond = time.substring(4, 6);

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) == "0") {
            getHour = getHour.substring(1, 2);
        }

        if (getMin.substring(0, 1) == "0") {
            getMin = getMin.substring(1, 2);
        }

        if (getSecond.substring(0, 1) == "0") {
            getSecond = getSecond.substring(1, 2);
        }

        // 변환시간
        conversionTime = Long.valueOf(getHour) * 1000 * 3600 + Long.valueOf(getMin) * 60 * 1000 + Long.valueOf(getSecond) * 1000;

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        timer = new CountDownTimer(conversionTime, 1000) {

            // 특정 시간마다 뷰 변경
            public void onTick(long millisUntilFinished) {

//                // 시간단위
//                String hour = String.valueOf(millisUntilFinished / (60 * 60 * 1000));

                // 분단위
                long getMin = millisUntilFinished - (millisUntilFinished / (60 * 60 * 1000)) ;
                String min = String.valueOf(getMin / (60 * 1000)); // 몫

                // 초단위
                String second = String.valueOf((getMin % (60 * 1000)) / 1000); // 나머지

                // 밀리세컨드 단위
                String millis = String.valueOf((getMin % (60 * 1000)) % 1000); // 몫
//
//                // 시간이 한자리면 0을 붙인다
//                if (hour.length() == 1) {
//                    hour = "0" + hour;
//                }

                // 분이 한자리면 0을 붙인다
                if (min.length() == 1) {
                    min = "0" + min;
                }

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                remain_textview2.setText(min + ":" + second);
            }

            // 제한시간 종료시
            public void onFinish() {

                // 변경 후
                remain_textview2.setText("00:00");
                timeout_check = true; //타임아웃됨
                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지

            }
        }.start();

    }

    //키보드 관련
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(phone_number_edittext.getWindowToken(), 0);
    }




    /**
     * 인증 검사
     * @param credential
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            System.out.println("인증 성공!!!");
//                            Toast.makeText(getContext(),"인증성공",Toast.LENGTH_LONG).show();
                            user = task.getResult().getUser();
//                            System.out.println("인증한 유저 전화번호 : " + user.getPhoneNumber());
                            verifySignOut();
                            phone_flag = true;
                            setRetrofitInit();
//                            ((SignUpActivity) Objects.requireNonNull(this)).changeFragment("userInfoSetUp",phone_number_edittext.getText().toString() , PhoneAuthentication.this);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                apply_false_textview.setText("인증번호가 틀립니다.");
                                apply_false_textview.setVisibility(View.VISIBLE);
                                System.out.println("인증 실패ㅜㅜ");
//                                Toast.makeText(getContext(),"인증실패",Toast.LENGTH_LONG).show();
                                Log.e("인증","인증실패");
                                phone_flag = false;

                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    /**
     * 인증코드 확인
     */
    private void verifySignIn(){
        String code = apply_number_edtitext.getText().toString().trim();
        if(code.isEmpty()){
            apply_number_edtitext.setError("인증번호를 입력하세요");
            apply_number_edtitext.requestFocus();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    /**
     * 인증번호 전송(인증번호 요청)
     * @param str1
     */
    private void sendVerification(String str1) {
        phone= str1;
        if(phone.length()!=0){
            phone= str1.substring(1);
        }
        Log.i("전화",phone);
        phoneNumber = phone;
        if(!phoneNumber.isEmpty()){
            phoneNumber ="+82"+phone;
        }
        Log.i("전화번호 인증버튼",phoneNumber);

        if(phoneNumber.isEmpty()){
            editTextPhone.setError("전화번호를 입력하세요!");
            editTextPhone.requestFocus();
            return;
        }
        if(phoneNumber.length()<10){
            editTextPhone.setError("유효한 전화번호를 입력하세요!");
            editTextPhone.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    /**
     * 인증 번호 재전송
     */
    private void resendVerification(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        System.out.println("재전송!!!!");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//            Toast.makeText(getContext(), "인증 요청 성공", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
//            Toast.makeText(getContext(), "인증 요청 실패", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            codeSent =verificationId;
            Log.d("VerificationId", verificationId);
            mVerificationId = verificationId;
            mResendToken = token;
        }
    };

    /**
     * 로그아웃
     */
    private void verifySignOut(){
        Log.d("유저 번호 : ",user.getPhoneNumber());
        FirebaseAuth.getInstance().signOut();
        Log.d("로그아웃 : ", "성공!!");
        System.out.println("로그아웃 성공");
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.phone_number_apply_button_true:
                code_send_flag = true;
                phone_number_reapply_button.setVisibility(View.VISIBLE);
                phone_number_apply_button_true.setVisibility(View.INVISIBLE);
                authentication_response_layout.setVisibility(View.VISIBLE);
                // TODO : 타이머 돌릴 총시간
                // 카운트 다운 시작
                if (timer != null) {
                    timer.cancel();
                }
                countDown("000200");
                sendVerification(phone_number_edittext.getText().toString().replaceAll("-", ""));
                break;
            case R.id.phone_number_edittext:
                phone_number_edittext.setCursorVisible(true);
                break;
            case R.id.phone_next_appCompatButton_true:
                if (timeout_check) {
                    apply_false_textview.setText("인증시간이 초과하였습니다. 재 전송하여 잔행 해 주세요.");
                    apply_false_textview.setVisibility(View.VISIBLE);
//                    verifySignOut();
                } else {
                    apply_false_textview.setVisibility(View.GONE);

                    progressDialog.setMessage("인증 확인중입니다.");
                    progressDialog.setCancelable(true);
                    progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                    progressDialog.show();
                    verifySignIn();
//
                }
                break;
            case R.id.phone_number_reapply_button: //인증번호 재전송
                timeout_check = false;
                code_send_flag = true;
                apply_false_textview.setVisibility(View.GONE);
                phone_number_reapply_button.setVisibility(View.VISIBLE);
                phone_number_apply_button_true.setVisibility(View.INVISIBLE);
                authentication_response_layout.setVisibility(View.VISIBLE);
                // TODO : 타이머 돌릴 총시간
                // 카운트 다운 시작
                if (timer != null) {
                    timer.cancel();
                }
                countDown("000200");
                sendVerification(phone_number_edittext.getText().toString().replaceAll("-", ""));
                break;
            case R.id.phone_next_appCompatButton_false: //
//                ((SignUpActivity) Objects.requireNonNull(getActivity())).changeFragment("userInfoSetUp","010-1234-5678" ,PhoneAuthentication.this);
                break;

            }
        }
}
