package com.dev.eatjeong.main.search.searchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.search.searchListAdapter.GoogleReviewListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.PopularListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.YoutubeReviewListAdapter;
import com.dev.eatjeong.main.search.searchListVO.GoogleReviewVO;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchGoogleListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchReviewMoreActivirt.SearchYoutubeReviewMoreActivity;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleReviewFragment extends Fragment {

    String user_id,sns_division,place_id,place_address,place_name;

    private ArrayList<GoogleReviewVO> arrayList = new ArrayList<GoogleReviewVO>();

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchGoogleListResponseVO> mCallSearchGoogleListResponseVO;

    RecyclerView listView;

    GoogleReviewListAdapter adapter;

    ProgressBar google_progress_bar;

    TextView review_more;
    public static GoogleReviewFragment newInstance(){
        return new GoogleReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_google_review_fragment, container, false);

//        google_progress_bar = (ProgressBar)v.findViewById(R.id.google_progress_bar);

        user_id = ((PlaceInfoActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((PlaceInfoActivity)getActivity()).getUserInfo().get("sns_division");
        place_name = ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_name");
        place_id =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_id");
        place_address =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_address");

//
        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (RecyclerView) v.findViewById(R.id.recycler_view);

        review_more = (TextView)v.findViewById(R.id.review_more);

//        listView = (ListView) v.findViewById(R.id.google_review_list);
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("as",arrayList.get(position).getKeyword());
//
//                //Fragment 끼리는 호출이 불가능하기에 연결 된 Activity를 통해서 호출함.
//                ((MainWrapActivity)getActivity()).changeText(arrayList.get(position).getKeyword());
//
//
//
//            }
//        });




        return v;
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

        if(user_id == null){
            mCallSearchGoogleListResponseVO = mSearchRetrofitAPI.getGoogleReview(place_id,user_id,sns_division,"5");
        }else{
            mCallSearchGoogleListResponseVO = mSearchRetrofitAPI.getGoogleReview(place_id,"temp","T","5");
        }


        mCallSearchGoogleListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchGoogleListResponseVO> mRetrofitCallback = new Callback<SearchGoogleListResponseVO>() {



        @Override

        public void onResponse(Call<SearchGoogleListResponseVO> call, Response<SearchGoogleListResponseVO> response) {
            arrayList.clear();
            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new GoogleReviewVO(
                        response.body().mDatalist.get(i).getIndex(),
                        response.body().mDatalist.get(i).getReview_id(),
                        response.body().mDatalist.get(i).getAuthor(),
                        response.body().mDatalist.get(i).getG_content(),
                        response.body().mDatalist.get(i).getG_rating(),
                        response.body().mDatalist.get(i).getWrite_date()
                ));
            }

            listView.setHasFixedSize(true);
            adapter = new GoogleReviewListAdapter(getActivity(), arrayList);
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            listView.setAdapter(adapter);

            //가로 레이아웃
            LinearLayoutManager horizonalLayoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            listView.setLayoutManager(horizonalLayoutManager);
        }



        @Override

        public void onFailure(Call<SearchGoogleListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };


}