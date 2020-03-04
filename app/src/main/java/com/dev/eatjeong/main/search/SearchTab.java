package com.dev.eatjeong.main.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

public class SearchTab extends Fragment implements View.OnClickListener{
    public static final int sub = 1002; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    Button search_map,search_lately_keyword,search_popular_keyword;
    EditText search_keyword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.search_tab, container, false);

        initFragment(v);

        search_map = (Button)v.findViewById(R.id.search_map);
        search_map.setOnClickListener(this);

        search_keyword = (EditText)v.findViewById(R.id.search_keyword) ;

        search_keyword.requestFocus();

        //자동키보드 적용소스
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        search_lately_keyword = (Button) v.findViewById(R.id.search_lately_keyword) ;
        search_lately_keyword.setOnClickListener(this);
        search_popular_keyword = (Button)v.findViewById(R.id.search_popular_keyword) ;
        search_popular_keyword.setOnClickListener(this);


        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fg;
        switch (v.getId()) {
            case R.id.search_map:
                Intent intent = new Intent(getContext(), MapSearchActivity.class);
                startActivityForResult(intent,0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.fadein,0);
                break;
            case R.id.search_lately_keyword:
                fg = LatelyFragment.newInstance();
                setChildFragment(fg);
                break;
            case R.id.search_popular_keyword:
                fg = PopularFragment.newInstance();
                setChildFragment(fg);
                break;
        }
    }

    public void initFragment(View v){
        Fragment fg;
        fg = LatelyFragment.newInstance();
        setChildFragment(fg);

    }

    public void setChildFragment(Fragment fr) {

        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();


        if (!fr.isAdded()) {
            childFt.replace(R.id.child_fragment_container, fr);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){

                Log.e("LOG", data.getStringExtra("keyword"));
                Log.e("LOG", "결과 받기 성공");
                search_keyword.setText(data.getStringExtra("keyword"));
            }

           // search_keyword.setText("23123");
        }
    }

    public void changeText(String text)
    {
        search_keyword.setText(text);
    }


}