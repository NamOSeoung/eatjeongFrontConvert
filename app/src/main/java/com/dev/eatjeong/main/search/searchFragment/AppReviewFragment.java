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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.search.searchListAdapter.AppReviewListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.GoogleReviewListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.PopularListAdapter;
import com.dev.eatjeong.main.search.searchListVO.AppReviewVO;
import com.dev.eatjeong.main.search.searchListVO.GoogleReviewVO;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAppListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchReviewMoreActivirt.SearchAppReviewMoreActivity;
import com.dev.eatjeong.main.search.searchReviewMoreActivirt.SearchYoutubeReviewMoreActivity;
import com.dev.eatjeong.mainWrap.MainWrapActivity;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppReviewFragment extends Fragment {

    String user_id,sns_division,place_id,place_address,place_name;
    private ArrayList<AppReviewVO> arrayList = new ArrayList<AppReviewVO>();
    private Retrofit mRetrofit;
    private SearchRetrofitAPI mSearchRetrofitAPI;
    private Call<SearchAppListResponseVO> mCallSearchAppListResponseVO;

    RecyclerView listView;
    AppReviewListAdapter adapter;
    ProgressBar app_progress_bar;
    TextView review_more;
    private RequestManager mGlideRequestManager;
    private ConstraintLayout data_layout, nodata_layout, container_1;

    public static AppReviewFragment newInstance(){
        return new AppReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGlideRequestManager = Glide.with(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_app_review_fragment, container, false);
        container_1 = v.findViewById(R.id.container);
        data_layout = v.findViewById(R.id.constraintLayout8);
        nodata_layout = v.findViewById(R.id.nodata_layout);

//        app_progress_bar = (ProgressBar)v.findViewById(R.id.app_progress_bar);

        Intent intent = getActivity().getIntent();

        if(intent.getStringExtra("call_division").equals("MAIN")){
            user_id = ((com.dev.eatjeong.main.home.homeActivity.PlaceInfoActivity)getActivity()).getUserInfo().get("user_id");
            sns_division = ((com.dev.eatjeong.main.home.homeActivity.PlaceInfoActivity)getActivity()).getUserInfo().get("user_id");
            place_id = intent.getStringExtra("place_id");
            place_name = intent.getStringExtra("place_name");
            place_address = intent.getStringExtra("place_address");
        }else if(intent.getStringExtra("call_division").equals("BOOKMARK")){
            user_id = intent.getStringExtra("user_id");
            sns_division = intent.getStringExtra("sns_division");
            place_id = intent.getStringExtra("place_id");
            place_name = intent.getStringExtra("place_name");
            place_address = intent.getStringExtra("place_address");
        }else{
            user_id = ((PlaceInfoActivity)getActivity()).getUserInfo().get("user_id");
            sns_division = ((PlaceInfoActivity)getActivity()).getUserInfo().get("sns_division");
            place_name = ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_name");
            place_id =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_id");
            place_address =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_address");
        }
//
        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();


        listView = (RecyclerView) v.findViewById(R.id.recycler_view);
        review_more = (TextView)v.findViewById(R.id.review_more);

        review_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMore = new Intent(getContext(), SearchAppReviewMoreActivity.class);
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
//        listView = (ListView) v.findViewById(R.id.app_review_list);
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

        if(Util.isNullOrEmpty(user_id)){
            mCallSearchAppListResponseVO = mSearchRetrofitAPI.getAppReview(place_id,"temp","T","5");
        }else{
            mCallSearchAppListResponseVO = mSearchRetrofitAPI.getAppReview(place_id,user_id,sns_division,"5");
        }

        mCallSearchAppListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchAppListResponseVO> mRetrofitCallback = new Callback<SearchAppListResponseVO>() {



        @Override

        public void onResponse(Call<SearchAppListResponseVO> call, Response<SearchAppListResponseVO> response) {

            arrayList.clear();

            if(response.body().mDatalist.size() > 0) {
                for (int i = 0; i < response.body().mDatalist.size(); i++) {
                    arrayList.add(new AppReviewVO(
                            response.body().mDatalist.get(i).getIndex(),
                            response.body().mDatalist.get(i).getReview_id(),
                            response.body().mDatalist.get(i).getReview_user_id(),
                            response.body().mDatalist.get(i).getAuthor(),
                            response.body().mDatalist.get(i).getProfile_image_url(),
                            response.body().mDatalist.get(i).getRating_point(),
                            response.body().mDatalist.get(i).getReview_contents(),
                            response.body().mDatalist.get(i).getLike_count(),
                            response.body().mDatalist.get(i).getWrite_date(),
                            response.body().mDatalist.get(i).getImage_url()
                    ));
                }

                listView.setHasFixedSize(true);
                adapter = new AppReviewListAdapter(getActivity(), arrayList, mGlideRequestManager);
                listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                listView.setAdapter(adapter);

                //가로 레이아웃
                LinearLayoutManager horizonalLayoutManager
                        = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                listView.setLayoutManager(horizonalLayoutManager);
            }else{
                ConstraintSet constraintSet = new ConstraintSet();
                data_layout.setVisibility(View.GONE);
                constraintSet.clone(container_1);
                constraintSet.connect(R.id.appreview_header, ConstraintSet.BOTTOM, R.id.nodata_layout, ConstraintSet.TOP);
                constraintSet.applyTo(container_1);
                nodata_layout.setVisibility(View.VISIBLE);
            }
        }



        @Override

        public void onFailure(Call<SearchAppListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){
                //레트로핏 연결하기위한 초기화 작업.
                setRetrofitInit();

                //재호출
                callSearchResponse();

            }

        }
    }


}
