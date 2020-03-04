package com.dev.eatjeong.main.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

public class BookmarkTab extends Fragment implements  View.OnClickListener{

    Button bookmark_place, bookmark_youtube, bookmark_naver, bookmark_tistory,bookmark_login;

    String user_id;
    String sns_division;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;


        user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");

        if(user_id != null) {
            v = inflater.inflate(R.layout.bookmark_tab, container, false);
            initFragment(v);
            bookmark_place = (Button)v.findViewById(R.id.bookmark_place);
            bookmark_youtube = (Button)v.findViewById(R.id.bookmark_youtube);
            bookmark_naver = (Button)v.findViewById(R.id.bookmark_naver);
            bookmark_tistory = (Button)v.findViewById(R.id.bookmark_tistory);

            bookmark_place.setOnClickListener(this);
            bookmark_youtube.setOnClickListener(this);
            bookmark_naver.setOnClickListener(this);
            bookmark_tistory.setOnClickListener(this);

        }else{
            v = inflater.inflate(R.layout.bookmark_logout_tab, container, false);

            bookmark_login = (Button)v.findViewById(R.id.bookmark_login);


            bookmark_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainWrapActivity)getActivity()).backLoginPage();
                }
            });
        }





        return v;
    }

    public void initFragment(View v){
        Fragment fg;
        fg = PlaceFragment.newInstance();
        setChildFragment(fg);

    }


    @Override
    public void onClick(View v) {
        Fragment fg;
        switch (v.getId()) {
            case R.id.bookmark_place:
                fg = PlaceFragment.newInstance();
                setChildFragment(fg);
                break;
            case R.id.bookmark_youtube:
                fg = YoutubeFragment.newInstance();
                setChildFragment(fg);
                break;
            case R.id.bookmark_naver:
                fg = NaverFragment.newInstance();
                setChildFragment(fg);
                break;
            case R.id.bookmark_tistory:
                fg = TistoryFragment.newInstance();
                setChildFragment(fg);
                break;

        }
    }


    public void setChildFragment(Fragment fr) {

        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();


        if (!fr.isAdded()) {
            childFt.replace(R.id.child_fragment_container, fr);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}