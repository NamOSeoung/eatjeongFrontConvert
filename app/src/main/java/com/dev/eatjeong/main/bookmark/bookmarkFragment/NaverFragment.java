package com.dev.eatjeong.main.bookmark.bookmarkFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.BookmarkRetrofitAPI;
import com.dev.eatjeong.main.bookmark.bookmarkListAdapter.BookmarkNaverListAdapter;
import com.dev.eatjeong.main.bookmark.bookmarkListVO.BookmarkReviewListVO;
import com.dev.eatjeong.main.bookmark.bookmarkListWebview.BookmarkNaverWebviewActivity;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkReviewResponseVO;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverFragment extends Fragment {


    String user_id;
    String sns_division;

    private ArrayList<BookmarkReviewListVO> arrayList = new ArrayList<BookmarkReviewListVO>();
    private Retrofit mRetrofit;
    private BookmarkRetrofitAPI mBookmarkRetrofitAPI;
    private Call<BookmarkReviewResponseVO> mCallBookmarkNaverResponseVO;

    private ScrollView data_layout;
    private ConstraintLayout nodata_layout;
    private ListView listView;
    private BookmarkNaverListAdapter adapter;
    private ProgressBar bookmark_naver_progress_bar;
    private RequestManager mGlideRequestManager;

    private Callback<BookmarkReviewResponseVO> mRetrofitCallback = new Callback<BookmarkReviewResponseVO>() {

        @Override
        public void onResponse(Call<BookmarkReviewResponseVO> call, Response<BookmarkReviewResponseVO> response) {

            arrayList.clear();

            if (response.body().mDatalist.size() > 0) {
            for (int i = 0; i < response.body().mDatalist.size(); i++) {
                arrayList.add(new BookmarkReviewListVO(
                        response.body().mDatalist.get(i).getPlace_name(),
                        response.body().mDatalist.get(i).getPlace_id(),
                        response.body().mDatalist.get(i).getReview_id(),
                        response.body().mDatalist.get(i).getThumbnail_url(),
                        response.body().mDatalist.get(i).getUrl(),
                        response.body().mDatalist.get(i).getTitle(),
                        response.body().mDatalist.get(i).getDescription(),
                        response.body().mDatalist.get(i).getAuthor(),
                        response.body().mDatalist.get(i).getWrite_date()
                ));
            }

            adapter = new BookmarkNaverListAdapter(getContext(), arrayList, mGlideRequestManager);
            listView.setAdapter(adapter);

            bookmark_naver_progress_bar.setVisibility(View.GONE);
            } else {
                data_layout.setVisibility(View.GONE);
                bookmark_naver_progress_bar.setVisibility(View.GONE);
                nodata_layout.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onFailure(Call<BookmarkReviewResponseVO> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public static NaverFragment newInstance() {
        return new NaverFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGlideRequestManager = Glide.with(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookmark_naver_fragment, container, false);


        user_id = ((MainWrapActivity) getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity) getActivity()).getUserInfo().get("sns_division");

        bookmark_naver_progress_bar = v.findViewById(R.id.bookmark_naver_progress_bar);

        data_layout = v.findViewById(R.id.data_layout);
        nodata_layout = v.findViewById(R.id.nodata_layout);

        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = v.findViewById(R.id.bookmark_naver_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent naverWebview = new Intent(getContext(), BookmarkNaverWebviewActivity.class);
                naverWebview.putExtra("url", arrayList.get(position).getUrl());
                naverWebview.putExtra("place_id", arrayList.get(position).getPlace_id());
                naverWebview.putExtra("review_id", arrayList.get(position).getReview_id());
                startActivityForResult(naverWebview, 0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.fadein, 0);

            }
        });

        return v;
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
        mCallBookmarkNaverResponseVO = mBookmarkRetrofitAPI.getBookmarkNaver("naver", user_id, sns_division);
        mCallBookmarkNaverResponseVO.enqueue(mRetrofitCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {
                //레트로핏 연결하기위한 초기화 작업.
                setRetrofitInit();

                //재호출
                callSearchResponse();

            }

        }
    }
}
