package com.dev.eatjeong.signUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.Objects;

public class TermsFragment extends Fragment {

    private String TAG = "TermsFragment 생명주기";
    private AppCompatButton service_terms_button, personal_terms_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView()");

        View v = inflater.inflate(R.layout.signup_terms, container, false);
        AppCompatButton next_appCompatButton_true = v.findViewById(R.id.next_appCompatButton_true);
        service_terms_button = v.findViewById(R.id.service_terms_appCompatButton);
        personal_terms_button = v.findViewById(R.id.personal_terms_appCompatButton);

        next_appCompatButton_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity) Objects.requireNonNull(getActivity())).changeFragment("phoneAuthentication", TermsFragment.this);
            }
        });

        service_terms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service_terms = new Intent(getActivity(), ServiceTermsPopupActivity.class);
                startActivityForResult(service_terms, 0);//액티비티 띄우기
            }
        });

        personal_terms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personal_terms = new Intent(getActivity(), PersonalTermsPopupActivity.class);
                startActivityForResult(personal_terms, 0);//액티비티 띄우기
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
}
