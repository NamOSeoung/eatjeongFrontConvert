package com.dev.eatjeong.signUp.signUpFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.layout.ClearEditText;
import com.dev.eatjeong.login.LoginActivity;
import com.dev.eatjeong.signUp.signUpActivity.SignUpActivity;
import com.dev.eatjeong.signUp.SignUpRetrofitAPI;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoSetUp extends Fragment implements View.OnClickListener {

    private String TAG = "UserInfoSetUp 생명주기";
    private String db_birthday = ""; // db기입용 최종 생년월일
    //-1 : 아예 선택 안함, 0 : 선택안함 선택 , 1 : 여자 , 2 : 남자
    int gender_flag = -1; //default 미 선택
    ClearEditText email_edittext,nickname_edittext,birth_edittext,password_edittext,password_check_edittext;
    AppCompatTextView email_message,nickname_message,birth_message,password_check_message,password_message;
    AppCompatRadioButton not_check_gender_radio,female_radio,male_radio;
    AppCompatButton signup_button_false,signup_button_true;
    boolean email_validation = false;
    boolean nickname_validation = false;
    boolean birthday_validation = false;
    boolean password_validation = false;
    boolean password_check_validation = false;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_info_setup, container, false);
        email_edittext = v.findViewById(R.id.email_edittext);
        email_message = v.findViewById(R.id.email_message);
        nickname_edittext = v.findViewById(R.id.nickname_edittext);
        nickname_message = v.findViewById(R.id.nickname_message);
        birth_edittext = v.findViewById(R.id.birth_edittext);
        birth_message = v.findViewById(R.id.birth_message);
        password_edittext = v.findViewById(R.id.password_edittext);
        password_check_edittext = v.findViewById(R.id.password_check_edittext);
        password_check_message = v.findViewById(R.id.password_check_message);
        password_message = v.findViewById(R.id.password_message);
        signup_button_false = v.findViewById(R.id.signup_button_false);
        signup_button_true = v.findViewById(R.id.signup_button_true);
        progressDialog = new ProgressDialog(getContext());
        //성별
        not_check_gender_radio = v.findViewById(R.id.not_check_gender_radio);
        female_radio = v.findViewById(R.id.female_radio);
        male_radio = v.findViewById(R.id.male_radio);

        not_check_gender_radio.setOnClickListener(this);
        female_radio.setOnClickListener(this);
        male_radio.setOnClickListener(this);
        signup_button_false.setOnClickListener(this);
        signup_button_true.setOnClickListener(this);

        Toast.makeText(getContext(),((SignUpActivity)getActivity()).getPhone_number(),Toast.LENGTH_SHORT).show();

        email_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                //바뀐후
                if(email_edittext.getText().length()<1){
                    email_message.setVisibility(View.INVISIBLE);
                    email_validation = false;
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }else{
                    if(!emailCheck()){ //이메일 형식이 올바르지 않을 시
                        email_message.setTextColor(Color.parseColor("#DB522B"));
                        email_message.setText("올바르지 않은 이메일 형식입니다.");
                        email_message.setVisibility(View.VISIBLE);
                        email_validation = false;
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }else{ //이메일 형식이 올바를시
                        email_message.setVisibility(View.INVISIBLE);
                        email_validation = true;
                        if(nickname_validation){
                            if(birthday_validation){
                                if(password_validation){
                                    if(password_check_validation){
                                        if(gender_flag>-1){
                                            signup_button_false.setVisibility(View.INVISIBLE);
                                            signup_button_true.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });
        nickname_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(nickname_edittext.getText().toString().length()<2){
                    nickname_message.setTextColor(Color.parseColor("#888888"));
                    nickname_message.setText("한글, 영문, 숫자 2 ~ 10자");
                    nickname_validation = false;
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }else {
                    if(!hasSpecialCharacter()){//특수문자 존재함
                        nickname_message.setTextColor(Color.parseColor("#DB522B"));
                        nickname_message.setText("특수문자를 제외한 한글, 영문, 숫자 2 ~ 10자");
                        nickname_validation = false;
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }else { //특수문자 미존재
                        nickname_message.setText("");
                        nickname_validation = true;
                        if(email_validation){
                            if(birthday_validation){
                                if(password_validation){
                                    if(password_check_validation){
                                        if(gender_flag>-1){
                                            signup_button_false.setVisibility(View.INVISIBLE);
                                            signup_button_true.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        birth_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                if(birth_edittext.getText().length() == 6){
                    String birth[] = {birth_edittext.getText().toString().substring(0,2),birth_edittext.getText().toString().substring(2,4),birth_edittext.getText().toString().substring(4,6)};
                    birth_edittext.setText(birth[0] + "/" + birth[1] + "/" + birth[2]);
                    birth_edittext.setSelection(birth_edittext.length());

                    if(Integer.parseInt(birth[1])>12){
                        birth_message.setTextColor(Color.parseColor("#DB522B"));
                        birth_message.setText("올바른 생년월일을 입력 해 주세요.");
                        birth_message.setVisibility(View.VISIBLE);
                        birth_edittext.setText("");
                        birthday_validation = false;
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                        return;
                    }

                    if(Integer.parseInt(birth[2])>31){
                        birth_message.setTextColor(Color.parseColor("#DB522B"));
                        birth_message.setText("올바른 생년월일을 입력 해 주세요.");
                        birth_message.setVisibility(View.VISIBLE);
                        birth_edittext.setText("");
                        birthday_validation = false;
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                        return;
                    }

                    if(Integer.parseInt(birth[0].substring(0,1))>=0&&Integer.parseInt(birth[0].substring(0,1))<=2){
                        if(getAge(Integer.parseInt("20"+birth[0]),Integer.parseInt(birth[1]),Integer.parseInt(birth[2]))<14){ //만14세 이상 체크
                            birth_message.setTextColor(Color.parseColor("#DB522B"));
                            birth_message.setText("만 14세 이상만 회원가입이 가능합니다");
                            birth_message.setVisibility(View.VISIBLE);
                            birth_edittext.setText("");
                            birthday_validation = false;
                            signup_button_false.setVisibility(View.VISIBLE);
                            signup_button_true.setVisibility(View.INVISIBLE);
                            return;
                        }
                    }

                    if(Integer.parseInt(birth[0].substring(0,1))>=0&&Integer.parseInt(birth[0].substring(0,1))<=2){ //2000년대 이상여부 체크 후 앞자리 세팅.
                        db_birthday = "20" + birth[0] + "-" + birth[1] + "-" + birth[2];
                    }else{
                        db_birthday = "19" + birth[0] + "-" + birth[1] + "-" + birth[2];
                    }


                    birth_message.setVisibility(View.INVISIBLE);
                    birthday_validation = true;
                    if(email_validation){
                        if(nickname_validation){
                            if(password_validation){
                                if(password_check_validation){
                                    if(gender_flag>-1){
                                        signup_button_false.setVisibility(View.INVISIBLE);
                                        signup_button_true.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }

                }else if(birth_edittext.getText().toString().length()==7){
                    birth_edittext.setText(birth_edittext.getText().toString().replaceAll("/",""));
                    birth_edittext.setSelection(birth_edittext.length());
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }
            }
        });

        password_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String password = password_edittext.getText().toString();
                String password_check = password_check_edittext.getText().toString();;

                password_check_validation = false;
                password_check_edittext.setText("");

                if(password.length()>=8){
                    if(getPasswordCheck()){ //비밀번호 체크 통과 시 숨김
                        password_message.setVisibility(View.INVISIBLE);
                        password_validation = true;
                        if(email_validation){
                            if(nickname_validation){
                                if(birthday_validation){
                                    if(password_check_validation){
                                        if(gender_flag>-1){
                                            signup_button_false.setVisibility(View.INVISIBLE);
                                            signup_button_true.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    }else{ //비밀번호 체크 미 통과시 문구 출력
                        password_message.setTextColor(Color.parseColor("#DB522B"));
                        password_message.setText("8~15자 이내로 영문,숫자,특수문자 중 2가지 이상 조합으로 입력해주세요.");
                        password_message.setVisibility(View.VISIBLE);
                        password_validation = false;
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }
                }else if(password.length() == 0){
                    password_message.setTextColor(Color.parseColor("#888888"));
                    password_message.setText("8~15자 이내로 영문,숫자,특수문자 중 2가지 이상 조합으로 입력해주세요.");
                    password_message.setVisibility(View.VISIBLE);
                    password_validation = false;
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }else if(password.length()>0&&password.length()<8){
                    password_message.setTextColor(Color.parseColor("#DB522B"));
                    password_message.setText("8~15자 이내로 영문,숫자,특수문자 중 2가지 이상 조합으로 입력해주세요.");
                    password_message.setVisibility(View.VISIBLE);
                    password_validation = false;
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }
            }
        });
        password_check_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = password_edittext.getText().toString();
                String password_check = password_check_edittext.getText().toString();

                if(password_validation){ // 상단 비밀번호 입력박스 통과시에만 비밀번호 동일여부 체크 진행.
                    if(!password.equals(password_check)){
                        password_check_message.setVisibility(View.VISIBLE);
                        password_check_validation = false;
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }else {
                        password_check_validation = true;
                        password_check_message.setVisibility(View.INVISIBLE);
                        if(email_validation){
                            if(nickname_validation){
                                if(birthday_validation){
                                    if(password_check_validation){
                                        if(gender_flag>-1){
                                            signup_button_false.setVisibility(View.INVISIBLE);
                                            signup_button_true.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        return v;
    }


//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Log.d(TAG, "onViewCreated()");
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        Log.d(TAG, "onAttach()");
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate()");
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.d(TAG, "onActivityCreated()");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart()");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume()");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause()");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop()");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d(TAG, "onDestroyView()");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy()");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.d(TAG, "onDetach()");
//    }

    public boolean emailCheck(){
        boolean email_check = false;
        //이메일형식체크
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email_edittext.getText().toString()).matches())
        {
//            Toast.makeText(getActivity(),"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
            email_check = false;
        }else {
            email_check = true;
        }
        return email_check;
    }

    public boolean hasSpecialCharacter() {
        boolean bool = false;

        // 공백 포함 특수문자 체크
        Pattern pattern1 = Pattern.compile("[ !@#$%^&*(),.?\":{}|<>]");

        if(pattern1.matcher(nickname_edittext.getText().toString()).find()){
            bool = false;
        }else {
            bool = true;
        }

        return bool;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.not_check_gender_radio:
                gender_flag = 0;
                if(email_validation){
                    if(nickname_validation){
                        if(birthday_validation){
                            if(password_validation){
                                if(password_check_validation){
                                    signup_button_false.setVisibility(View.INVISIBLE);
                                    signup_button_true.setVisibility(View.VISIBLE);
                                }else {
                                    signup_button_false.setVisibility(View.VISIBLE);
                                    signup_button_true.setVisibility(View.INVISIBLE);
                                }
                            }else {
                                signup_button_false.setVisibility(View.VISIBLE);
                                signup_button_true.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            signup_button_false.setVisibility(View.VISIBLE);
                            signup_button_true.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }
                }else{
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.female_radio:
                gender_flag = 1;
                if(email_validation){
                    if(nickname_validation){
                        if(birthday_validation){
                            if(password_validation){
                                if(password_check_validation){
                                    signup_button_false.setVisibility(View.INVISIBLE);
                                    signup_button_true.setVisibility(View.VISIBLE);
                                }else {
                                    signup_button_false.setVisibility(View.VISIBLE);
                                    signup_button_true.setVisibility(View.INVISIBLE);
                                }
                            }else {
                                signup_button_false.setVisibility(View.VISIBLE);
                                signup_button_true.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            signup_button_false.setVisibility(View.VISIBLE);
                            signup_button_true.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }
                }else{
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.male_radio:
                gender_flag = 2;
                if(email_validation){
                    if(nickname_validation){
                        if(birthday_validation){
                            if(password_validation){
                                if(password_check_validation){
                                    signup_button_false.setVisibility(View.INVISIBLE);
                                    signup_button_true.setVisibility(View.VISIBLE);
                                }else {
                                    signup_button_false.setVisibility(View.VISIBLE);
                                    signup_button_true.setVisibility(View.INVISIBLE);
                                }
                            }else {
                                signup_button_false.setVisibility(View.VISIBLE);
                                signup_button_true.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            signup_button_false.setVisibility(View.VISIBLE);
                            signup_button_true.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        signup_button_false.setVisibility(View.VISIBLE);
                        signup_button_true.setVisibility(View.INVISIBLE);
                    }
                }else{
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.signup_button_false:
//                UserSignUpControll userSignUpControll = new UserSignUpControll();
//                userSignUpControll.setRetrofitInit();
                break;
            case R.id.signup_button_true:
                UserSignUpControll userSignUpControll = new UserSignUpControll();
                userSignUpControll.setRetrofitInit();
                break;
        }

    }

    //사용자 회원가입 관련 내부 클래스
    public class UserSignUpControll {

        String code = "";

        private Retrofit mRetrofit;

        private SignUpRetrofitAPI mSignUpRetrofitAPI;

        private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

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


            mSignUpRetrofitAPI = mRetrofit.create(SignUpRetrofitAPI.class);

            callResponse();

        }

        private void callResponse() {

            String email = email_edittext.getText().toString();
            String password = password_edittext.getText().toString();
            String nickname = nickname_edittext.getText().toString();
            String birthday = db_birthday;

            //성별 선택 여부에 따른 분기처리
            String gender="";
            if(gender_flag==0){
                gender = "none";
            }else if(gender_flag==1){
                gender = "female";
            }else if(gender_flag==2){
                gender = "male";
            }

            if(!email_validation){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("").setMessage("이메일 기입란을 확인 해주세요");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                return;
            }
            if(!nickname_validation){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("").setMessage("닉네임 기입란을 확인 해주세요");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                return;
            }
            if(!birthday_validation){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("").setMessage("생년월일을 확인 해주세요");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }
            if(!password_validation){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("").setMessage("비밀번호 기입란을 확인 해주세요");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                return;
            }
            if(!password_check_validation){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("").setMessage("비밀번호 확인란을 확인 해주세요");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                return;
            }
            if(gender_flag==-1){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("").setMessage("성별 선택란을 선택 해 주세요");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                return;
            }


            String phone_number = ((SignUpActivity)getActivity()).getPhone_number();

            progressDialog.setMessage("가입 처리중입니다.");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            progressDialog.show();
            mCallCommonMapResponseVO = mSignUpRetrofitAPI.signUser(email,password,gender,nickname,birthday,phone_number);

            mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
            @Override
            public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {

                Log.e("code : ", String.valueOf(response.body().getCode()));
                Log.e("code : ", String.valueOf(response.body().getMessage()));

                //200 : 성공 , 409 : 이미 존재하는 아이디인 경우
                if(response.body().getCode().equals("200") ){ // 회원가입 완료의 의미 후속처리로 로그인화면으로 이동하기
                    ((SignUpActivity)getActivity()).onBackPressed();
                }else if(response.body().getCode().equals("409")){ //이미 존재하는 회원인 경우=
                    email_message.setTextColor(Color.parseColor("#DB522B"));
                    email_message.setText("이미 가입된 이메일입니다.");
                    email_message.setVisibility(View.VISIBLE);
                    email_validation = false;
                    signup_button_false.setVisibility(View.VISIBLE);
                    signup_button_true.setVisibility(View.INVISIBLE);
                }
                progressDialog.dismiss();
            }

            @Override

            public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();
                progressDialog.dismiss();
            }
        };
    }
    public int getAge(int birthYear, int birthMonth, int birthDay)
    {
        Calendar current = Calendar.getInstance();
        int currentYear  = current.get(Calendar.YEAR);
        int currentMonth = current.get(Calendar.MONTH) + 1;
        int currentDay   = current.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;
        // 생일 안 지난 경우 -1
        if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay)
            age--;

        return age;
    }
    public boolean getPasswordCheck()
    {
        String password = password_edittext.getText().toString();
        String passwordStr[] = password.split("");
        final String specialString = "^[a-zA-Z0-9]*$"; // 특수문자만
        final String engString = ".*[a-zA-Z]+.*"; // 영문자
        final String numberString = ".*[0-9]+.*"; // 숫자만

        boolean check = false;

        if(!password.matches(specialString)){
            if(password.matches(engString)){
                check = true;
            }
            if(password.matches(numberString)){
                check = true;
            }
        }else if(password.matches(engString)){
            if(!password.matches(specialString)){
                check = true;
            }
            if(password.matches(numberString)){
                check = true;
            }
        }else if(password.matches(numberString)){
            if(password.matches(engString)){
                check = true;
            }
            if(!password.matches(specialString)){
                check = true;
            }
        }

        return check;
    }


}

