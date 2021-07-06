package com.dev.eatjeong.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.Objects;

public class HomeTab extends Fragment{


    String address_arr[];
    TextView address;

    ConstraintLayout address_layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.home_tab, container, false);
        address_layout = v.findViewById(R.id.address_layout);
        address = (TextView)v.findViewById(R.id.address);
        if(((MainWrapActivity)getActivity()).getCurrentLocationAddress().equals("")){
            address.setText("서울 맛집");
        }else{
            address_arr = ((MainWrapActivity)getActivity()).getCurrentLocationAddress().split(" ");
            address.setText(address_arr[1] + " " +address_arr[2] + " " +address_arr[3]);
        }

        address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainWrapActivity) Objects.requireNonNull(getActivity())).changeFragment(0, HomeTab.this);
            }
        });

        return v;
    }
}
