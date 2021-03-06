package com.dev.eatjeong.main.home.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.HomeRetrofitAPI;
import com.dev.eatjeong.main.home.homeListAdapter.MainNaverListAdapter;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainReviewListResponseVO;
import com.dev.eatjeong.main.home.homeReviewMore.HomeReviewMoreActivity;
import com.dev.eatjeong.main.home.homeReviewWebview.HomeReviewWebviewActivity;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainNaverListFragment extends Fragment{

    private ArrayList<MainReviewVO> arrayList = new ArrayList<MainReviewVO>();

    private Retrofit mRetrofit;

    private HomeRetrofitAPI mHomeRetrofitAPI;

    private Call<MainReviewListResponseVO> mCallMainReviewListResponseVO;

    RecyclerView listView;

    MainNaverListAdapter adapter;


    TextView review_more;

    String address_arr[];

    public RequestManager mGlideRequestManager;

    public static MainNaverListFragment newInstance(){
        return new MainNaverListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideRequestManager = Glide.with(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_naver_list_fragment, container, false);

//        youtube_progress_bar = (ProgressBar)v.findViewById(R.id.youtube_progress_bar);


        review_more = (TextView)v.findViewById(R.id.review_more);


        address_arr = ((MainWrapActivity)getActivity()).getCurrentLocationAddress().split(" ");


//
        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (RecyclerView) v.findViewById(R.id.recycler_view);

        review_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMore = new Intent(getContext(), HomeReviewMoreActivity.class);
                TextView textView = ((MainWrapActivity) getActivity()).findViewById(R.id.address);
                goMore.putExtra("main_address_textview", textView.getText());
                goMore.putExtra("address",((MainWrapActivity)getActivity()).getCurrentLocationAddress());
                goMore.putExtra("review_division","NAVER");

                startActivityForResult(goMore,0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.fadein,0);
            }
        });


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
                    Intent goWebview = new Intent(getContext(), HomeReviewWebviewActivity.class);
                    goWebview.putExtra("url",arrayList.get(position).getUrl());
                    startActivityForResult(goWebview,0);//액티비티 띄우기
                    getActivity().overridePendingTransition(R.anim.sliding_up,R.anim.stay);
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


        mHomeRetrofitAPI = mRetrofit.create(HomeRetrofitAPI.class);

    }

    private void callSearchResponse() {

        String query = "";
        Log.e("dd",((MainWrapActivity)getActivity()).getCurrentLocationAddress());
        if(((MainWrapActivity)getActivity()).getCurrentLocationAddress().equals("")){
            query = "서울 맛집";
        }else{
            address_arr = ((MainWrapActivity)getActivity()).getCurrentLocationAddress().split(" ");
            query = address_arr[1] + " " + address_arr[2] +" " +address_arr[3] + " " + "맛집";
        }
        mCallMainReviewListResponseVO = mHomeRetrofitAPI.getMainReviews(query,"NAVER","5");

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
                        response.body().mDatalist.get(i).getTitle(),
                        response.body().mDatalist.get(i).getUrl(),
                        response.body().mDatalist.get(i).getThumbnail_url(),
                        response.body().mDatalist.get(i).getDescription(),
                        response.body().mDatalist.get(i).getAuthor(),
                        response.body().mDatalist.get(i).getWrite_date()
                ));
            }

            listView.setHasFixedSize(true);
            adapter = new MainNaverListAdapter(getActivity(), arrayList, mGlideRequestManager);
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            listView.setAdapter(adapter);

            //가로 레이아웃
            LinearLayoutManager horizonalLayoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            listView.setLayoutManager(horizonalLayoutManager);
        }



        @Override

        public void onFailure(Call<MainReviewListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

}
