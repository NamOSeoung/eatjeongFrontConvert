package com.dev.eatjeong.main.bookmark.bookmarkListWebview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.eatjeong.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class BookmarkYoutubeWebviewActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings; //웹뷰세팅

    private Button bookmark_youtube_modal;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_youtube_webview);

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");

        bookmark_youtube_modal = (Button)findViewById(R.id.bookmark_youtube_modal);

        // 웹뷰 시작
        webView = (WebView) findViewById(R.id.bookmark_youtube_webview);

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


        bookmark_youtube_modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BookmarkYoutubeModalActivity.class);
//                intent.putExtra("data", "Test Popup");
//                startActivityForResult(intent, 1);

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BookmarkYoutubeWebviewActivity.this);
                bottomSheetDialog.setContentView(R.layout.bookmark_youtube_bottom_sheet);
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

}
