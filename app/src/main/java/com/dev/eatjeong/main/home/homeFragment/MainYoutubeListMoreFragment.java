package com.dev.eatjeong.main.home.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.bookmarkListAdapter.BookmarkYoutubeListAdapter;
import com.dev.eatjeong.main.home.HomeRetrofitAPI;
import com.dev.eatjeong.main.home.homeListAdapter.MainYoutubeListAdapter;
import com.dev.eatjeong.main.home.homeListAdapter.MainYoutubeListMoreAdapter;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainReviewListResponseVO;
import com.dev.eatjeong.main.home.homeReviewMore.HomeReviewMoreActivity;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainYoutubeListMoreFragment extends Fragment{

    private ArrayList<MainReviewVO> arrayList = new ArrayList<MainReviewVO>();

    private Retrofit mRetrofit;

    private HomeRetrofitAPI mHomeRetrofitAPI;

    private Call<MainReviewListResponseVO> mCallMainReviewListResponseVO;

    MainYoutubeListMoreAdapter adapter;



    String address_arr[];

    String address = "";

    ProgressBar home_youtube_progress_bar;

    ListView listView;

    public static MainYoutubeListMoreFragment newInstance(){
        return new MainYoutubeListMoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_youtube_review_more_fragment, container, false);

        Intent intent = getActivity().getIntent();

        address = intent.getStringExtra("address");

//        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

//        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();
//
        home_youtube_progress_bar = (ProgressBar)v.findViewById(R.id.home_youtube_progress_bar);

        listView = (ListView) v.findViewById(R.id.home_youtube_list);

        return v;
    }

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mHomeRetrofitAPI = mRetrofit.create(HomeRetrofitAPI.class);

    }

    private void callSearchResponse() {

        String query = "";
        if(address.equals("")){
            query = "서울 맛집";
        }else{
            address_arr = (address.split(" "));
            query = address_arr[1] + " " + address_arr[2] +" " +address_arr[3] + " " + "맛집";
        }

        mCallMainReviewListResponseVO = mHomeRetrofitAPI.getMainReviewsMore(query,"YOUTUBE");

        mCallMainReviewListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<MainReviewListResponseVO> mRetrofitCallback = new Callback<MainReviewListResponseVO>() {

        @Override

        public void onResponse(Call<MainReviewListResponseVO> call, Response<MainReviewListResponseVO> response) {
            Log.e("title : ", response.body().getCode());
            Log.e("title : ", response.body().getMessage());
            arrayList.clear();
            for(int i = 0; i < response.body().mDatalist.size(); i ++){

                arrayList.add(new MainReviewVO(
                        response.body().mDatalist.get(i).getTitle()
                ));
            }

            adapter = new MainYoutubeListMoreAdapter(getContext(),arrayList);
            listView.setAdapter(adapter);

            home_youtube_progress_bar.setVisibility(View.GONE);
        }



        @Override

        public void onFailure(Call<MainReviewListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

}