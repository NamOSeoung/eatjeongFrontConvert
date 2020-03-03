package com.dev.eatjeong.mainWrap;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.BookmarkTab;
import com.dev.eatjeong.main.home.HomeTab;
import com.dev.eatjeong.main.search.SearchTab;
import com.dev.eatjeong.main.settings.SettingsTab;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainWrapActivity extends AppCompatActivity {
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private HomeTab homeTab = new HomeTab();
    private SearchTab searchTab = new SearchTab();
    private BookmarkTab bookmarkTab = new BookmarkTab();
    private SettingsTab settingsTab = new SettingsTab();
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;
    private MenuItem prevBottomNavigation;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_wrap);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeTab).commitAllowingStateLoss();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.home: {
                        transaction.replace(R.id.frame_layout, homeTab);
                        transaction.commit();
                        break;
                    }
                    case R.id.search: {
                        transaction.replace(R.id.frame_layout, searchTab);
                        transaction.commit();
                        break;
                    }
                    case R.id.bookmark: {
                        transaction.replace(R.id.frame_layout, bookmarkTab);
                        transaction.commit();
                        break;
                    }
                    case R.id.settings: {
                        transaction.replace(R.id.frame_layout, settingsTab);
                        transaction.commit();
                        break;
                    }
                }
                return true;
            }
        });



        // 버튼 누른 결과를 보여주기 위해 TextView를 사용합니다.

//        // 버튼 클릭시 사용되는 리스너를 구현합니다.
//
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_main_menu);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
//                        switch (item.getItemId()) {
//
//                            case R.id.searchItem:
//
//                                message.setText("Up 버튼을 눌렀습니다.");
//
//                                return true;
//
//                            case R.id.cameraItem:
//
//                                message.setText("Down 버튼을 눌렀습니다.");
//
//                                return true;
//
//                            case R.id.callItem:
//
//                                message.setText("Search 버튼을 눌렀습니다.");
//
//                                return true;
//                        }
//                        return false;
//                    }
//                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
