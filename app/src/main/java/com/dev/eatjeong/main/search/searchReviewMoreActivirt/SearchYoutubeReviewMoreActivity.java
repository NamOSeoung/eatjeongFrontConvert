package com.dev.eatjeong.main.search.searchReviewMoreActivirt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.BookmarkRetrofitAPI;
import com.dev.eatjeong.main.bookmark.bookmarkListAdapter.BookmarkYoutubeListAdapter;
import com.dev.eatjeong.main.bookmark.bookmarkListVO.BookmarkYoutubeListVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkYoutubeResponseVO;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchListAdapter.YoutubeReviewListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.YoutubeReviewListMoreAdapter;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchYoutubeListResponseVO;
import com.dev.eatjeong.main.search.searchReviewWebview.SearchYoutubeReviewWebviewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchYoutubeReviewMoreActivity extends AppCompatActivity {

    String user_id,sns_division,place_id,place_address,place_name;

    private ArrayList<YoutubeReviewVO> arrayList = new ArrayList<YoutubeReviewVO>();

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchYoutubeListResponseVO> mCallSearchYoutubeListResponseVO;

    ListView listView;

    YoutubeReviewListMoreAdapter adapter;

    ProgressBar search_youtube_progress_bar;

    TextView review_more;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_youtube_review_more);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");
        place_id = intent.getStringExtra("place_id");
        place_address = intent.getStringExtra("place_address");
        place_name = intent.getStringExtra("place_name");

        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        search_youtube_progress_bar = (ProgressBar)findViewById(R.id.search_youtube_progress_bar);
        listView = (ListView)findViewById(R.id.search_youtube_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goWebview = new Intent(getApplicationContext(), SearchYoutubeReviewWebviewActivity.class);
                goWebview.putExtra("user_id",user_id);
                goWebview.putExtra("sns_division",sns_division);
                goWebview.putExtra("place_id",place_id);
                goWebview.putExtra("review_id",arrayList.get(position).getReview_id());
                goWebview.putExtra("url",arrayList.get(position).getUrl());

                startActivityForResult(goWebview,0);//액티비티 띄우기
                SearchYoutubeReviewMoreActivity.this.overridePendingTransition(R.anim.fadein,0);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

    }

    private void callSearchResponse() {

        String place_name_arr[] = place_name.split(" ");
        String place_address_arr[] = place_address.split(" ");

        if(place_address_arr.length>2){
            place_address_arr[1] = place_address_arr[1].replace("시","").replace("군","").replace("구","");
        }

        if(user_id == null){
            mCallSearchYoutubeListResponseVO = mSearchRetrofitAPI.getYoutubeReviewMore(place_id,user_id,sns_division,place_address_arr[1] + " " + place_name_arr[0] + " " + "맛집");
        }else{
            mCallSearchYoutubeListResponseVO = mSearchRetrofitAPI.getYoutubeReviewMore(place_id,"temp","T",place_address_arr[1] + " " + place_name_arr[0] + " " + "맛집");
        }

        mCallSearchYoutubeListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchYoutubeListResponseVO> mRetrofitCallback = new Callback<SearchYoutubeListResponseVO>() {



        @Override

        public void onResponse(Call<SearchYoutubeListResponseVO> call, Response<SearchYoutubeListResponseVO> response) {
            Log.e("dd",response.body().getCode());
            Log.e("dd",response.body().getMessage());
            arrayList.clear();
            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new YoutubeReviewVO(
                        response.body().mDatalist.get(i).getIndex(),
                        response.body().mDatalist.get(i).getReview_id(),
                        response.body().mDatalist.get(i).getTitle(),
                        response.body().mDatalist.get(i).getWrite_date(),
                        response.body().mDatalist.get(i).getAuthor(),
                        response.body().mDatalist.get(i).getUrl(),
                        response.body().mDatalist.get(i).getThumbnail_url(),
                        response.body().mDatalist.get(i).getBookmark_flag()
                ));
            }

            adapter = new YoutubeReviewListMoreAdapter(getApplicationContext(),arrayList);
            listView.setAdapter(adapter);

            search_youtube_progress_bar.setVisibility(View.GONE);

        }



        @Override

        public void onFailure(Call<SearchYoutubeListResponseVO> call, Throwable t) {


            Log.e("ss","asdasdasd");
            t.printStackTrace();

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){
                search_youtube_progress_bar.setVisibility(View.VISIBLE);
                //레트로핏 연결하기위한 초기화 작업.
                setRetrofitInit();

                //재호출
                callSearchResponse();

            }

        }
    }

}
