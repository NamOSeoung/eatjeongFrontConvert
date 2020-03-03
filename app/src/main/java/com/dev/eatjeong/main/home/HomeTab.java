package com.dev.eatjeong.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.CustomAdapter;
import com.dev.eatjeong.model.BookmarkDataModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeTab extends Fragment {


    private ArrayList<User> arrayList = new ArrayList<User>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.home_tab, container, false);

        ListView listView = (ListView) v.findViewById(R.id.listview);

        arrayList.add(new User("123123","email@email.com","내용1"));
        arrayList.add(new User("23444","email라2@email.com","내용2"));
        arrayList.add(new User("5555","email@3email.com","내용3"));
        arrayList.add(new User("5555","email@3email.com","내용3"));


        CustomAdapter adapter = new CustomAdapter(getContext(),arrayList);
        listView.setAdapter(adapter);

//        FragmentManager fragmentManager = getFragmentManager();
//
//        BookmarkTabsPagerAdapter tabsPagerAdapter = new BookmarkTabsPagerAdapter(getContext(), fragmentManager);

       // ViewPager viewPager = findViewById(R.id.view_pager);
//
//        ViewPager viewPager = v.findViewById(R.id.view_pager);
//        viewPager.setAdapter(tabsPagerAdapter);


//        TabLayout tabs = v.findViewById(R.id.tabs);
//        TabLayout tabs = findViewById(R.id.tabs);
//
//        tabs.setupWithViewPager(viewPager);
      //  tabs.addTab(tabs.newTab().setText("TAB-5"));

//        //처음 childfragment 지정
//        getFragmentManager().beginTransaction().add(R.id.child_fragment, new HomeChildFragment1()).commit();
//
//        //하위버튼
//        LinearLayout subButton1 = (LinearLayout) v.findViewById(R.id.subButton1);
//        LinearLayout subButton2 = (LinearLayout) v.findViewById(R.id.subButton2);
//        LinearLayout subButton3 = (LinearLayout) v.findViewById(R.id.subButton3);
//
//        //클릭 이벤트
//        subButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.child_fragment, new HomeChildFragment1()).commit();
//            }
//        });
//        subButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.child_fragment, new HomeChildFragment2()).commit();
//
//            }
//        });
//        subButton3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.child_fragment, new HomeChildFragment3()).commit();
//
//            }
//        });

        return v;
    }
}