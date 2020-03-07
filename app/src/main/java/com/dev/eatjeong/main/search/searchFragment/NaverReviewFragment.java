package com.dev.eatjeong.main.search.searchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.search.searchListAdapter.NaverReviewListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.PopularListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.YoutubeReviewListAdapter;
import com.dev.eatjeong.main.search.searchListVO.NaverReviewVO;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchNaverListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchReviewMoreActivirt.SearchNaverReviewMoreActivity;
import com.dev.eatjeong.main.search.searchReviewMoreActivirt.SearchYoutubeReviewMoreActivity;
import com.dev.eatjeong.main.search.searchReviewWebview.SearchNaverReviewWebviewActivity;
import com.dev.eatjeong.main.search.searchReviewWebview.SearchYoutubeReviewWebviewActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverReviewFragment extends Fragment {

    String user_id,sns_division,place_id,place_address,place_name;

    private ArrayList<NaverReviewVO> arrayList = new ArrayList<NaverReviewVO>();

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchNaverListResponseVO> mCallSearchNaverListResponseVO;

//    ListView listView;
    RecyclerView listView;
    NaverReviewListAdapter adapter;

    ProgressBar naver_progress_bar;

    TextView review_more;
    public static NaverReviewFragment newInstance(){
        return new NaverReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_naver_review_fragment, container, false);

//        naver_progress_bar = (ProgressBar)v.findViewById(R.id.naver_progress_bar);

        user_id = ((PlaceInfoActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((PlaceInfoActivity)getActivity()).getUserInfo().get("sns_division");
        place_name = ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_name");
        place_id =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_id");
        place_address =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_address");


        review_more = (TextView)v.findViewById(R.id.review_more);


        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (RecyclerView) v.findViewById(R.id.recycler_view);

        review_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMore = new Intent(getContext(), SearchNaverReviewMoreActivity.class);
                goMore.putExtra("user_id",user_id);
                goMore.putExtra("sns_division",sns_division);
                goMore.putExtra("place_name",place_name);
                goMore.putExtra("place_id",place_id);
                goMore.putExtra("place_address",place_address);
                startActivityForResult(goMore,0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.fadein,0);
            }
        });
//

        //터치를 하고 손을 뗴는 순간 적용되는 이벤트 적용위한 추가.
        final GestureDetector gestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });

        listView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = listView.findChildViewUnder(e.getX(), e.getY());
                int position = listView.getChildAdapterPosition(child);
                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
                    Intent goWebview = new Intent(getContext(), SearchNaverReviewWebviewActivity.class);
                    goWebview.putExtra("user_id",user_id);
                    goWebview.putExtra("sns_division",sns_division);
                    goWebview.putExtra("place_id",place_id);
                    goWebview.putExtra("review_id",arrayList.get(position).getReview_id());
                    goWebview.putExtra("url",arrayList.get(position).getUrl());

                    startActivityForResult(goWebview,0);//액티비티 띄우기
                    getActivity().overridePendingTransition(R.anim.fadein,0);
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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


        mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

    }

    private void callSearchResponse() {

        String place_address_arr[] = place_address.split(" ");

        if(user_id == null){
            mCallSearchNaverListResponseVO = mSearchRetrofitAPI.getNaverReview(place_id,user_id,sns_division,place_address_arr[0] + " " +place_address_arr[1] +" " + place_name + " " + "맛집","5");
        }else{
            mCallSearchNaverListResponseVO = mSearchRetrofitAPI.getNaverReview(place_id,"temp","T",place_address_arr[0] + " " +place_address_arr[1] +" " + place_name + " " + "맛집","5");
        }

        mCallSearchNaverListResponseVO.enqueue(mRetrofitCallback);
    }

    private Callback<SearchNaverListResponseVO> mRetrofitCallback = new Callback<SearchNaverListResponseVO>() {



        @Override

        public void onResponse(Call<SearchNaverListResponseVO> call, Response<SearchNaverListResponseVO> response) {

            arrayList.clear();
            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new NaverReviewVO(
                        response.body().mDatalist.get(i).getIndex(),
                        response.body().mDatalist.get(i).getReview_id(),
                        response.body().mDatalist.get(i).getTitle(),
                        response.body().mDatalist.get(i).getWrite_date(),
                        response.body().mDatalist.get(i).getAuthor(),
                        response.body().mDatalist.get(i).getDescription(),
                        response.body().mDatalist.get(i).getUrl(),
                        response.body().mDatalist.get(i).getThumbnail_url(),
                        response.body().mDatalist.get(i).getBookmark_flag()
                ));
            }

            listView.setHasFixedSize(true);
            adapter = new NaverReviewListAdapter(getActivity(), arrayList);
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            listView.setAdapter(adapter);

            //가로 레이아웃
            LinearLayoutManager horizonalLayoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            listView.setLayoutManager(horizonalLayoutManager);
        }



        @Override

        public void onFailure(Call<SearchNaverListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };


}