package com.dev.eatjeong.main.home.homeReviewMore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeFragment.MainNaverListMoreFragment;
import com.dev.eatjeong.main.home.homeFragment.MainPlaceInfoMoreFragment;
import com.dev.eatjeong.main.home.homeFragment.MainTistoryListMoreFragment;
import com.dev.eatjeong.main.home.homeFragment.MainYoutubeListMoreFragment;


public class HomeReviewMoreActivity extends AppCompatActivity {

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private MainYoutubeListMoreFragment mainYoutubeListMoreFragment = new MainYoutubeListMoreFragment();
    private MainNaverListMoreFragment mainNaverListMoreFragment = new MainNaverListMoreFragment();
    private MainTistoryListMoreFragment mainTistoryListMoreFragment = new MainTistoryListMoreFragment();
    private MainPlaceInfoMoreFragment mainPlaceInfoMoreFragment = new MainPlaceInfoMoreFragment();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_review_more);

        Intent intent  = getIntent();
        String review_division = intent.getStringExtra("review_division");
        setFragment(review_division);


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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }


    public void setFragment(String review_division){
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(review_division.equals("YOUTUBE")){
            transaction.replace(R.id.home_review_more_frame_layout, mainYoutubeListMoreFragment).commitAllowingStateLoss();
        }else if(review_division.equals("NAVER")){
            transaction.replace(R.id.home_review_more_frame_layout, mainNaverListMoreFragment).commitAllowingStateLoss();
        }else if(review_division.equals("TISTORY")){
            transaction.replace(R.id.home_review_more_frame_layout, mainTistoryListMoreFragment).commitAllowingStateLoss();
        }else if(review_division.equals("PLACE")){
            transaction.replace(R.id.home_review_more_frame_layout, mainPlaceInfoMoreFragment).commitAllowingStateLoss();
        }
    }

}
