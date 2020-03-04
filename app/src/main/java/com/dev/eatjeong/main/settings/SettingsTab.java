package com.dev.eatjeong.main.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

public class SettingsTab extends Fragment {

    String user_id;
    String sns_division;
    Button settings_logout;
    Button settings_login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v;

        user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");


        if(user_id != null){
            v = inflater.inflate(R.layout.settings_tab, container, false);

            settings_logout = v.findViewById(R.id.settings_logout);

            settings_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainWrapActivity)getActivity()).appLogout();
                }
            });
        }else {
            v = inflater.inflate(R.layout.settings_logout_tab, container, false);

            settings_login = v.findViewById(R.id.settings_login);

            settings_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainWrapActivity)getActivity()).backLoginPage();
                }
            });

        }


        return v;
    }
}