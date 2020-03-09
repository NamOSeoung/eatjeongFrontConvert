package com.dev.eatjeong.main.search.searchReviewWebview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.retrofitVO.CommonMapResponseVO;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchYoutubeReviewWebviewActivity extends AppCompatActivity implements View.OnClickListener {

    String user_id;
    String sns_division;

    String place_id;
    String review_id;

    String author;

    private int SET_CODE = 0;  // 0 : bookmark 추가, 1 : 게시자 블랙리스트 추가, 2 : 게시물 블랙리스트 추가

    private String bookmark_flag = ""; //true : 북마크 추가상태, false: 북마크 헤제상태

    private Button
            search_youtube_modal,
            bookmark_cancel,
            bookmark_add,
            post_black_add,
            post_black_cancel,
            author_black_add,
            author_black_cancel;

    private TextView
            bookmark_add_text,
            bookmark_cancel_text,
            author_black_add_text,
            post_black_add_text,
            author_black_cancel_text,
            post_black_cancel_text;

    private WebView webView;
    private WebSettings webSettings; //웹뷰세팅

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_youtube_webview);


        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");
        place_id = intent.getStringExtra("place_id");
        review_id = intent.getStringExtra("review_id");
        bookmark_flag = intent.getStringExtra("bookmark_flag");
        author = intent.getStringExtra("author");

        Toast.makeText(getApplicationContext(), bookmark_flag, Toast.LENGTH_SHORT).show();
        search_youtube_modal = (Button) findViewById(R.id.search_youtube_modal);

        // 웹뷰 시작
        webView = (WebView) findViewById(R.id.search_youtube_webview);

        webView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        webSettings = webView.getSettings(); //세부 세팅 등록
        webSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        webSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        webSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        webSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        webSettings.setSupportZoom(false); // 화면 줌 허용 여부
        webSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        webSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        webView.loadUrl(url); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작


        search_youtube_modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchYoutubeReviewWebviewActivity.this);
                bottomSheetDialog.setContentView(R.layout.search_youtube_bottom_sheet);


                bookmark_cancel = (Button) bottomSheetDialog.findViewById(R.id.bookmark_cancel);
                bookmark_add = (Button) bottomSheetDialog.findViewById(R.id.bookmark_add);

                bookmark_add_text = (TextView) bottomSheetDialog.findViewById(R.id.bookmark_add_test);
                bookmark_cancel_text = (TextView) bottomSheetDialog.findViewById(R.id.bookmark_cancel_text);

                author_black_add = (Button) bottomSheetDialog.findViewById(R.id.author_black_add);
                post_black_add = (Button) bottomSheetDialog.findViewById(R.id.post_black_add);

                author_black_cancel = (Button) bottomSheetDialog.findViewById(R.id.author_black_cancel);
                post_black_cancel = (Button) bottomSheetDialog.findViewById(R.id.post_black_cancel);

                author_black_add_text = (TextView) bottomSheetDialog.findViewById(R.id.author_black_add_text);
                author_black_cancel_text = (TextView) bottomSheetDialog.findViewById(R.id.author_black_cancel_text);

                post_black_add_text = (TextView) bottomSheetDialog.findViewById(R.id.post_black_add_text);
                post_black_cancel_text = (TextView) bottomSheetDialog.findViewById(R.id.post_black_cancel_text);
                bookmark_add.setVisibility(View.VISIBLE);

//                if(bookmark_flag.equals("true")){
//                    bookmark_add.setVisibility(View.GONE);
//                    bookmark_cancel.setVisibility(View.VISIBLE);
//
//                    bookmark_add_text.setVisibility(View.GONE);
//                    bookmark_cancel_text.setVisibility(View.VISIBLE);
//                }else{
//                    bookmark_add.setVisibility(View.VISIBLE);
//                    bookmark_cancel.setVisibility(View.GONE);
//
//                    bookmark_add_text.setVisibility(View.VISIBLE);
//                    bookmark_cancel_text.setVisibility(View.GONE);
//                }

                bookmark_add.setOnClickListener(SearchYoutubeReviewWebviewActivity.this);
                bookmark_cancel.setOnClickListener(SearchYoutubeReviewWebviewActivity.this);

                author_black_add.setOnClickListener(SearchYoutubeReviewWebviewActivity.this);
                author_black_cancel.setOnClickListener(SearchYoutubeReviewWebviewActivity.this);

                post_black_add.setOnClickListener(SearchYoutubeReviewWebviewActivity.this);
                post_black_cancel.setOnClickListener(SearchYoutubeReviewWebviewActivity.this);

                bottomSheetDialog.show();

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
    public void onBackPressed() {
        // 검색 동작
        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

        setResult(1, intent);
        // finish();
        super.onBackPressed();


    }

    @Override
    public void onClick(View v) {
        /*
            SET_CODE = 0 : bookmark 추가, 1 : 게시자 블랙리스트 추가, 2 : 게시물 블랙리스트 추가
            SET_CODE = 3 : bookmark 삭제, 4 : 게시자 블랙리스트 삭제, 5 : 게시물 블랙리스트 삭제
        */

        BookmarkBlackListControll bookmarkBlackListControll = new BookmarkBlackListControll();

        switch (v.getId()) {

            case R.id.bookmark_add:
                SET_CODE = 0;
                bookmarkBlackListControll.setRetrofitInit(0);
                break;
            case R.id.bookmark_cancel:
                SET_CODE = 3;
                bookmarkBlackListControll.setRetrofitInit(3);
                break;
            case R.id.author_black_add:
                SET_CODE = 1;
                bookmarkBlackListControll.setRetrofitInit(1);
                break;
            case R.id.author_black_cancel:
                SET_CODE = 4;
                bookmarkBlackListControll.setRetrofitInit(4);
                break;
            case R.id.post_black_add:
                SET_CODE = 2;
                bookmarkBlackListControll.setRetrofitInit(2);
                break;
            case R.id.post_black_cancel:
                SET_CODE = 5;
                 bookmarkBlackListControll.setRetrofitInit(5);
                break;
        }
    }

    //북마크와 블랙리스트 베타적으로 적용시키기위한 내부 클래스
    public class BookmarkBlackListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SearchRetrofitAPI mSearchRetrofitAPI;

        private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRetrofitInit(int code_division) {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */


            if (user_id == null) {
                return;
            }

            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

            callPlaceInfoResponse(code_division);

        }

        private void callPlaceInfoResponse(int code_division) {

            /*
            SET_CODE = 0 : bookmark 추가, 1 : 게시자 블랙리스트 추가, 2 : 게시물 블랙리스트 추가
            SET_CODE = 3 : bookmark 삭제, 4 : 게시자 블랙리스트 삭제, 5 : 게시물 블랙리스트 삭제
            */

            if (code_division == 0) {
                mCallCommonMapResponseVO = mSearchRetrofitAPI.setBookmarkYoutube("youtube", place_id, review_id, user_id, sns_division);
            } else if (code_division == 1) {
                mCallCommonMapResponseVO = mSearchRetrofitAPI.setBlackList(review_id,place_id, user_id, sns_division, "youtube", author, "author");
            } else if (code_division == 2) {
                mCallCommonMapResponseVO = mSearchRetrofitAPI.setBlackList(review_id,place_id,user_id, sns_division, "youtube", author, "post");
            } else if (code_division == 3) {
                mCallCommonMapResponseVO = mSearchRetrofitAPI.deleteBookmarkYoutube("youtube", place_id, review_id, user_id, sns_division);
            } else if (code_division == 4) {
                mCallCommonMapResponseVO = mSearchRetrofitAPI.deleteBlackList(place_id,user_id, sns_division, "youtube", author, "author", review_id);
            } else if (code_division == 5) {
                mCallCommonMapResponseVO = mSearchRetrofitAPI.deleteBlackList(place_id,user_id, sns_division, "youtube", author, "post", review_id);
            }

            mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
            @Override
            public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {

                Log.e("code : ", response.body().getCode());
                Log.e("message : ", response.body().getMessage());
                if(response.body().getCode().equals("200")){ //호출 성공 시 버튼 변경
                    setButtionVisible();
                }
//                if(response.body().getCode().equals("200")){
//                    if(bookmark_flag.equals("true")){
//                        bookmark_flag = "false";
//                    }else {
//                        bookmark_flag = "true";
//                    }
//
//                }
//            setCode(response.body().getCode());

            }

            @Override

            public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }

    public void setButtionVisible(){
          /*
            SET_CODE = 0 : bookmark 추가, 1 : 게시자 블랙리스트 추가, 2 : 게시물 블랙리스트 추가
            SET_CODE = 3 : bookmark 삭제, 4 : 게시자 블랙리스트 삭제, 5 : 게시물 블랙리스트 삭제
         */
        if(SET_CODE == 0){

            bookmark_add.setVisibility(View.GONE);
            bookmark_add_text.setVisibility(View.GONE);
            bookmark_cancel.setVisibility(View.VISIBLE);
            bookmark_cancel_text.setVisibility(View.VISIBLE);

            author_black_add.setVisibility(View.VISIBLE);
            author_black_add_text.setVisibility(View.VISIBLE);
            author_black_cancel.setVisibility(View.GONE);
            author_black_cancel_text.setVisibility(View.GONE);

            post_black_add.setVisibility(View.VISIBLE);
            post_black_add_text.setVisibility(View.VISIBLE);
            post_black_cancel.setVisibility(View.GONE);
            post_black_cancel_text.setVisibility(View.GONE);

        }else if(SET_CODE == 1){

            bookmark_add.setVisibility(View.VISIBLE);
            bookmark_add_text.setVisibility(View.VISIBLE);
            bookmark_cancel.setVisibility(View.GONE);
            bookmark_cancel_text.setVisibility(View.GONE);

            author_black_add.setVisibility(View.GONE);
            author_black_add_text.setVisibility(View.GONE);
            author_black_cancel.setVisibility(View.VISIBLE);
            author_black_cancel_text.setVisibility(View.VISIBLE);

            post_black_add.setVisibility(View.VISIBLE);
            post_black_add_text.setVisibility(View.VISIBLE);
            post_black_cancel.setVisibility(View.GONE);
            post_black_cancel_text.setVisibility(View.GONE);

        }else if(SET_CODE == 2){

            bookmark_add.setVisibility(View.VISIBLE);
            bookmark_add_text.setVisibility(View.VISIBLE);
            bookmark_cancel.setVisibility(View.GONE);
            bookmark_cancel_text.setVisibility(View.GONE);

            author_black_add.setVisibility(View.VISIBLE);
            author_black_add_text.setVisibility(View.VISIBLE);
            author_black_cancel.setVisibility(View.GONE);
            author_black_cancel_text.setVisibility(View.GONE);

            post_black_add.setVisibility(View.GONE);
            post_black_add_text.setVisibility(View.GONE);
            post_black_cancel.setVisibility(View.VISIBLE);
            post_black_cancel_text.setVisibility(View.VISIBLE);

        }else if(SET_CODE == 3||SET_CODE == 4||SET_CODE == 5){

            bookmark_add.setVisibility(View.VISIBLE);
            bookmark_add_text.setVisibility(View.VISIBLE);
            bookmark_cancel.setVisibility(View.GONE);
            bookmark_cancel_text.setVisibility(View.GONE);

            author_black_add.setVisibility(View.VISIBLE);
            author_black_add_text.setVisibility(View.VISIBLE);
            author_black_cancel.setVisibility(View.GONE);
            author_black_cancel_text.setVisibility(View.GONE);

            post_black_add.setVisibility(View.VISIBLE);
            post_black_add_text.setVisibility(View.VISIBLE);
            post_black_cancel.setVisibility(View.GONE);
            post_black_cancel_text.setVisibility(View.GONE);

        }
    }
}
