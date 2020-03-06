package com.dev.eatjeong.mainWrap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class MainWrapActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

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

    public void appLogout(){
        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.remove("user_id");

        editor.remove("sns_division");

        editor.commit();

        super.onBackPressed();
    }

    public void backLoginPage(){
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        String user_id = sp.getString("user_id",null);

        if(user_id == null){
            super.onBackPressed();
        }else{
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;

            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finishAffinity(); //앱 종료해버리는 이벤트
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void changeText(String text)
    {
        SharedPreferences sf = getSharedPreferences("lately_keyword",MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString("keyword",text);

        editor.commit();
        searchTab.changeText(text);
        Log.e("lately",sf.getString("keyword",null));
    }

    public String getKeyword()
    {
        String keyword = searchTab.getKeyword();
        return keyword;
    }

    public void latelyKeywordSetting(){
        JSONArray array = new JSONArray();
        JSONArray array2;

        array.put("강남");
        array.put("서울");
        SharedPreferences sf = getSharedPreferences("lately_keyword",MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putString("keyword",array.toString());

        try{
            array2 = new JSONArray(sf.getString("keyword",null));

        }catch (JSONException e){

        }


    }

    public Map<String,String> getUserInfo(){
        Map<String,String> userInfo = new HashMap();

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

        userInfo.put("user_id",sp.getString("user_id",null));
        userInfo.put("sns_division",sp.getString("sns_division",null));

        return userInfo;
    }

}
