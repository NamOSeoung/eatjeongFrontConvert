package com.dev.eatjeong.main.bookmark.bookmarkListWebview;

import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.retrofitVO.CommonMapResponseVO;
import com.dev.eatjeong.main.bookmark.BookmarkRetrofitAPI;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BookmarkNaverWebviewActivity extends AppCompatActivity {

    String user_id;
    String sns_division;
    String place_id;
    String review_id;



    private boolean bookmark_flag = true; //true : 북마크 추가상태, false: 북마크 헤제상태

    private Button bookmark_naver_modal, bookmark_naver_cancel, bookmark_naver_add;

    private TextView bookmark_naver_cancel_text, bookmark_naver_add_text;

    private Retrofit mRetrofit;

    private BookmarkRetrofitAPI mBookmarkRetrofitAPI;

    private Call<CommonMapResponseVO> mCallBookmarkNaverResponseVO;




    private WebView webView;
    private WebSettings webSettings; //웹뷰세팅
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_naver_webview);

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);

        user_id = sp.getString("user_id",null);
        sns_division = sp.getString("sns_division",null);

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        place_id = intent.getStringExtra("place_id");
        review_id = intent.getStringExtra("review_id");

        bookmark_naver_modal = (Button)findViewById(R.id.bookmark_naver_modal);

        // 웹뷰 시작
        webView = (WebView) findViewById(R.id.bookmark_naver_webview);

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

        bookmark_naver_modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BookmarkNaverWebviewActivity.this);
                bottomSheetDialog.setContentView(R.layout.bookmark_naver_bottom_sheet);


                bookmark_naver_cancel = (Button) bottomSheetDialog.findViewById(R.id.bookmark_naver_cancel);
                bookmark_naver_add = (Button) bottomSheetDialog.findViewById(R.id.bookmark_naver_add);

                bookmark_naver_add_text = (TextView) bottomSheetDialog.findViewById(R.id.bookmark_naver_add_text) ;
                bookmark_naver_cancel_text = (TextView) bottomSheetDialog.findViewById(R.id.bookmark_naver_cancel_text) ;

                if(bookmark_flag){
                    bookmark_naver_add.setVisibility(View.GONE);
                    bookmark_naver_cancel.setVisibility(View.VISIBLE);

                    bookmark_naver_add_text.setVisibility(View.GONE);
                    bookmark_naver_cancel_text.setVisibility(View.VISIBLE);
                }else{
                    bookmark_naver_add.setVisibility(View.VISIBLE);
                    bookmark_naver_cancel.setVisibility(View.GONE);

                    bookmark_naver_add_text.setVisibility(View.VISIBLE);
                    bookmark_naver_cancel_text.setVisibility(View.GONE);
                }

                bookmark_naver_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //레트로핏 연결하기위한 초기화 작업.
                        setRetrofitInit();

                        //레트로핏 초기화 후 호출작업 진행.
                        callSearchResponse();
                    }
                });


                bookmark_naver_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //레트로핏 연결하기위한 초기화 작업.
                        setRetrofitInit();

                        //레트로핏 초기화 후 호출작업 진행.
                        callSearchResponse();

                    }
                });

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

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mBookmarkRetrofitAPI = mRetrofit.create(BookmarkRetrofitAPI.class);

    }

    private void callSearchResponse() {

        if(bookmark_flag){ //북마크 추가 된 상태면 지워야하고 삭제 된 상태면 추가 가능하도록 조치
            mCallBookmarkNaverResponseVO = mBookmarkRetrofitAPI.deleteBookmarkNaver("naver",place_id,review_id,user_id,sns_division);
        }else{
            mCallBookmarkNaverResponseVO = mBookmarkRetrofitAPI.setBookmarkNaver("naver",place_id,review_id,user_id,sns_division);
        }


        mCallBookmarkNaverResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {


        @Override

        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());

            if (bookmark_flag) {
                bookmark_flag = false;

                bookmark_naver_add.setVisibility(View.VISIBLE);
                bookmark_naver_cancel.setVisibility(View.GONE);

                bookmark_naver_add_text.setVisibility(View.VISIBLE);
                bookmark_naver_cancel_text.setVisibility(View.GONE);


            } else {
                bookmark_flag = true;

                bookmark_naver_add.setVisibility(View.GONE);
                bookmark_naver_cancel.setVisibility(View.VISIBLE);

                bookmark_naver_add_text.setVisibility(View.GONE);
                bookmark_naver_cancel_text.setVisibility(View.VISIBLE);
            }

        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();

        }
    };

    @Override
    public void onBackPressed() {
        // 검색 동작
        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

        setResult(1,intent);
        finish();
//        super.onBackPressed();


    }

}
