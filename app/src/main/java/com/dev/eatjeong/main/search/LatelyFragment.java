package com.dev.eatjeong.main.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;

public class LatelyFragment extends Fragment {

    public static LatelyFragment newInstance(){
        return new LatelyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_lately_fragment, container, false);

        return v;
    }

}