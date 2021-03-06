package com.dev.eatjeong.main.home.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.HomeRetrofitAPI;
import com.dev.eatjeong.main.home.homeActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.home.homeListAdapter.MainPlaceListAdapter;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainPlaceListResponseVO;
import com.dev.eatjeong.main.home.homeReviewMore.HomeReviewMoreActivity;
import com.dev.eatjeong.main.home.homeVO.MainPlaceVO;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPlaceListFragment extends Fragment {

    private ArrayList<MainPlaceVO> arrayList = new ArrayList<MainPlaceVO>();
    private Retrofit mRetrofit;
    private HomeRetrofitAPI mHomeRetrofitAPI;
    private Call<MainPlaceListResponseVO> mCallMainPlaceListResponseVO;
    RecyclerView listView;
    MainPlaceListAdapter adapter;
    ProgressBar youtube_progress_bar;
    TextView review_more;
    String address_arr[];
    public RequestManager mGlideRequestManager;
    public static MainPlaceListFragment newInstance() {
        return new MainPlaceListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideRequestManager = Glide.with(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_place_list_fragment, container, false);
//        youtube_progress_bar = (ProgressBar)v.findViewById(R.id.youtube_progress_bar);

        review_more = (TextView) v.findViewById(R.id.review_more);
        address_arr = ((MainWrapActivity) getActivity()).getCurrentLocationAddress().split(" ");

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
                goMore.putExtra("address", ((MainWrapActivity) getActivity()).getCurrentLocationAddress());
                goMore.putExtra("review_division", "PLACE");

                startActivityForResult(goMore, 0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.fadein, 0);
            }
        });


//터치를 하고 손을 뗴는 순간 적용되는 이벤트 적용위한 추가.
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        listView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = listView.findChildViewUnder(e.getX(), e.getY());
                int position = listView.getChildAdapterPosition(child);
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    Intent goPlaceInfo = new Intent(getContext(), PlaceInfoActivity.class);
                    goPlaceInfo.putExtra("place_id", arrayList.get(position).getPlace_id());
                    goPlaceInfo.putExtra("place_name", arrayList.get(position).getPlace_name());
                    goPlaceInfo.putExtra("place_address", arrayList.get(position).getPlace_address());
                    goPlaceInfo.putExtra("latitude", arrayList.get(position).getLatitude());
                    goPlaceInfo.putExtra("longitude", arrayList.get(position).getLongitude());
                    goPlaceInfo.putExtra("call_division", "MAIN");

                    startActivityForResult(goPlaceInfo, 0);//액티비티 띄우기
                    getActivity().overridePendingTransition(R.anim.fadein, 0);
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
        Log.e("dd", ((MainWrapActivity) getActivity()).getCurrentLocationAddress());
        if (((MainWrapActivity) getActivity()).getCurrentLocationAddress().equals("")) {
            query = "서울 맛집";
        } else {
            address_arr = ((MainWrapActivity) getActivity()).getCurrentLocationAddress().split(" ");
            query = address_arr[1] + " " + address_arr[2] + " " + address_arr[3] + " " + "맛집";
        }
        mCallMainPlaceListResponseVO = mHomeRetrofitAPI.getMainPlace(query, "5");
        mCallMainPlaceListResponseVO.enqueue(mRetrofitCallback);
    }

    private Callback<MainPlaceListResponseVO> mRetrofitCallback = new Callback<MainPlaceListResponseVO>() {

        @Override
        public void onResponse(Call<MainPlaceListResponseVO> call, Response<MainPlaceListResponseVO> response) {
            arrayList.clear();
            for (int i = 0; i < response.body().mDatalist.size(); i++) {
                arrayList.add(new MainPlaceVO(
                        response.body().mDatalist.get(i).getPlace_id(),
                        response.body().mDatalist.get(i).getPlace_name(),
                        response.body().mDatalist.get(i).getPlace_address(),
                        response.body().mDatalist.get(i).getLatitude(),
                        response.body().mDatalist.get(i).getLongitude(),
                        response.body().mDatalist.get(i).getBlog_thumbnail(),
                        response.body().mDatalist.get(i).getApp_thumbnail(),
                        response.body().mDatalist.get(i).getCategory_name(),
                        "4.2",     // Todo:getGoogle_rating() 만들어야함!!!
                        response.body().mDatalist.get(i).getAppreview_rating()
                ));
            }

            listView.setHasFixedSize(true);
            adapter = new MainPlaceListAdapter(getContext(), arrayList, mGlideRequestManager);
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            listView.setAdapter(adapter);

            //가로 레이아웃
            LinearLayoutManager horizonalLayoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            listView.setLayoutManager(horizonalLayoutManager);
        }


        @Override
        public void onFailure(Call<MainPlaceListResponseVO> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
