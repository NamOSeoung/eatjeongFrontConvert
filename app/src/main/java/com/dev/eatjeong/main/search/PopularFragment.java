package com.dev.eatjeong.main.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;

public class PopularFragment extends Fragment {

    public static PopularFragment newInstance(){
        return new PopularFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_popular_fragment, container, false);

        Log.e("sss","sss");
        return v;
    }

}