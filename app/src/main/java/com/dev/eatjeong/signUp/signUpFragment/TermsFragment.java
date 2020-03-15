package com.dev.eatjeong.signUp.signUpFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.signUp.signUpActivity.PersonalTermsPopupActivity;
import com.dev.eatjeong.signUp.signUpActivity.ServiceTermsPopupActivity;
import com.dev.eatjeong.signUp.signUpActivity.SignUpActivity;

import java.util.Objects;

public class TermsFragment extends Fragment implements View.OnClickListener{

    private String TAG = "TermsFragment 생명주기";
    private AppCompatButton service_terms_button, personal_terms_button,next_appCompatButton_true,next_appCompatButton_false;
    private AppCompatTextView service_terms_appCompatTextView, personal_terms_appCompatTextView,all_terms_appCompatTextView;


    boolean service_terms_flag = false;
    boolean personal_terms_flag = false;
    boolean all_terms_flag = false;



    ImageView service_terms_correct_f,service_terms_correct_y,personal_terms_correct_y,personal_terms_correct_f,all_terms_correct_y,all_terms_correct_f;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView()");

        View v = inflater.inflate(R.layout.signup_terms, container, false);
        service_terms_button = v.findViewById(R.id.service_terms_appCompatButton);
        personal_terms_button = v.findViewById(R.id.personal_terms_appCompatButton);

        next_appCompatButton_true = v.findViewById(R.id.next_appCompatButton_true);
        next_appCompatButton_false = v.findViewById(R.id.next_appCompatButton_false);

        service_terms_appCompatTextView = v.findViewById(R.id.service_terms_appCompatTextView);
        personal_terms_appCompatTextView = v.findViewById(R.id.personal_terms_appCompatTextView);
        all_terms_appCompatTextView = v.findViewById(R.id.all_terms_appCompatTextView);

        service_terms_correct_f = v.findViewById(R.id.service_terms_correct_f);
        service_terms_correct_y = v.findViewById(R.id.service_terms_correct_y);

        personal_terms_correct_f = v.findViewById(R.id.personal_terms_correct_f);
        personal_terms_correct_y = v.findViewById(R.id.personal_terms_correct_y);

        all_terms_correct_f = v.findViewById(R.id.all_terms_correct_f);
        all_terms_correct_y = v.findViewById(R.id.all_terms_correct_y);


        service_terms_correct_f.setVisibility(View.VISIBLE);
        service_terms_correct_y.setVisibility(View.GONE);

        personal_terms_correct_f.setVisibility(View.VISIBLE);
        personal_terms_correct_y.setVisibility(View.GONE);

        all_terms_correct_f.setVisibility(View.VISIBLE);
        all_terms_correct_y.setVisibility(View.GONE);

        next_appCompatButton_false.setVisibility(View.VISIBLE);
        next_appCompatButton_true.setVisibility(View.GONE);


        service_terms_correct_f.setOnClickListener(this);
        service_terms_correct_y.setOnClickListener(this);
        personal_terms_correct_f.setOnClickListener(this);
        personal_terms_correct_y.setOnClickListener(this);
        all_terms_correct_f.setOnClickListener(this);
        all_terms_correct_y.setOnClickListener(this);

        service_terms_appCompatTextView.setOnClickListener(this);
        personal_terms_appCompatTextView.setOnClickListener(this);
        all_terms_appCompatTextView.setOnClickListener(this);

        next_appCompatButton_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity) Objects.requireNonNull(getActivity())).changeFragment("phoneAuthentication", "",TermsFragment.this);
            }
        });

        service_terms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service_terms = new Intent(getActivity(), ServiceTermsPopupActivity.class);

                startActivityForResult(service_terms, 0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.sliding_up,R.anim.stay);
            }
        });

        personal_terms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personal_terms = new Intent(getActivity(), PersonalTermsPopupActivity.class);
                startActivityForResult(personal_terms, 0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.sliding_up,R.anim.stay);

            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_terms_correct_f:
                if(personal_terms_flag){
                    all_terms_flag = true;
                    all_terms_correct_f.setVisibility(View.GONE);
                    all_terms_correct_y.setVisibility(View.VISIBLE);
                }
                service_terms_flag = true;
                service_terms_correct_y.setVisibility(View.VISIBLE);
                service_terms_correct_f.setVisibility(View.GONE);
                break;
            case R.id.service_terms_correct_y:
                service_terms_flag = false;
                all_terms_flag = false;

                all_terms_correct_y.setVisibility(View.GONE);
                all_terms_correct_f.setVisibility(View.VISIBLE);

                service_terms_correct_y.setVisibility(View.GONE);
                service_terms_correct_f.setVisibility(View.VISIBLE);
                break;
            case R.id.personal_terms_correct_f:
                if(service_terms_flag){
                    all_terms_flag = true;
                    all_terms_correct_f.setVisibility(View.GONE);
                    all_terms_correct_y.setVisibility(View.VISIBLE);
                }

                personal_terms_flag = true;
                personal_terms_correct_y.setVisibility(View.VISIBLE);
                personal_terms_correct_f.setVisibility(View.GONE);
                break;
            case R.id.personal_terms_correct_y:
                personal_terms_flag = false;
                all_terms_flag = false;

                all_terms_correct_y.setVisibility(View.GONE);
                all_terms_correct_f.setVisibility(View.VISIBLE);

                personal_terms_correct_y.setVisibility(View.GONE);
                personal_terms_correct_f.setVisibility(View.VISIBLE);
                break;
            case R.id.all_terms_correct_f:
                service_terms_flag = true;
                personal_terms_flag = true;
                all_terms_flag = true;
                service_terms_correct_y.setVisibility(View.VISIBLE);
                service_terms_correct_f.setVisibility(View.GONE);
                personal_terms_correct_y.setVisibility(View.VISIBLE);
                personal_terms_correct_f.setVisibility(View.GONE);
                all_terms_correct_y.setVisibility(View.VISIBLE);
                all_terms_correct_f.setVisibility(View.GONE);
                break;
            case R.id.all_terms_correct_y:
                service_terms_flag = false;
                personal_terms_flag = false;
                all_terms_flag = false;
                service_terms_correct_y.setVisibility(View.GONE);
                service_terms_correct_f.setVisibility(View.VISIBLE);
                personal_terms_correct_y.setVisibility(View.GONE);
                personal_terms_correct_f.setVisibility(View.VISIBLE);
                all_terms_correct_y.setVisibility(View.GONE);
                all_terms_correct_f.setVisibility(View.VISIBLE);
                break;
            case R.id.service_terms_appCompatTextView:
                if(service_terms_flag){
                    service_terms_flag = false;
                    all_terms_flag = false;

                    all_terms_correct_y.setVisibility(View.GONE);
                    all_terms_correct_f.setVisibility(View.VISIBLE);

                    service_terms_correct_y.setVisibility(View.GONE);
                    service_terms_correct_f.setVisibility(View.VISIBLE);
                }else {
                    if(personal_terms_flag){
                        all_terms_flag = true;
                        all_terms_correct_f.setVisibility(View.GONE);
                        all_terms_correct_y.setVisibility(View.VISIBLE);
                    }

                    service_terms_flag = true;
                    service_terms_correct_y.setVisibility(View.VISIBLE);
                    service_terms_correct_f.setVisibility(View.GONE);
                }
                break;
            case R.id.personal_terms_appCompatTextView:
                if(personal_terms_flag){
                    personal_terms_flag = false;
                    all_terms_flag = false;

                    all_terms_correct_y.setVisibility(View.GONE);
                    all_terms_correct_f.setVisibility(View.VISIBLE);

                    personal_terms_correct_f.setVisibility(View.VISIBLE);
                    personal_terms_correct_y.setVisibility(View.GONE);
                }else {
                    if(service_terms_flag){
                        all_terms_flag = true;
                        all_terms_correct_f.setVisibility(View.GONE);
                        all_terms_correct_y.setVisibility(View.VISIBLE);
                    }
                    personal_terms_flag = true;
                    personal_terms_correct_f.setVisibility(View.GONE);
                    personal_terms_correct_y.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.all_terms_appCompatTextView:
                if(all_terms_flag){
                    all_terms_flag = false;
                    service_terms_flag = false;
                    personal_terms_flag = false;
                    service_terms_correct_y.setVisibility(View.GONE);
                    service_terms_correct_f.setVisibility(View.VISIBLE);
                    personal_terms_correct_y.setVisibility(View.GONE);
                    personal_terms_correct_f.setVisibility(View.VISIBLE);
                    all_terms_correct_y.setVisibility(View.GONE);
                    all_terms_correct_f.setVisibility(View.VISIBLE);
                }else{
                    service_terms_flag = true;
                    personal_terms_flag = true;
                    all_terms_flag = true;
                    service_terms_correct_y.setVisibility(View.VISIBLE);
                    service_terms_correct_f.setVisibility(View.GONE);
                    personal_terms_correct_y.setVisibility(View.VISIBLE);
                    personal_terms_correct_f.setVisibility(View.GONE);
                    all_terms_correct_y.setVisibility(View.VISIBLE);
                    all_terms_correct_f.setVisibility(View.GONE);
                }

                break;

        }
        if(all_terms_flag){
            next_appCompatButton_true.setVisibility(View.VISIBLE);
        }else {
            next_appCompatButton_true.setVisibility(View.GONE);
        }
    }
}
