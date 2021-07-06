package com.dev.eatjeong.main.home.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.HomeRetrofitAPI;
import com.dev.eatjeong.main.home.homeActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.home.homeListAdapter.MainPlaceListMoreAdapter;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainPlaceListResponseVO;
import com.dev.eatjeong.main.home.homeVO.MainPlaceVO;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPlaceInfoMoreFragment extends Fragment {


    public static final int sub = 1003; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    String user_id;
    String sns_division;

    private ArrayList<MainPlaceVO> arrayList = new ArrayList<MainPlaceVO>();
    private Retrofit mRetrofit;
    private HomeRetrofitAPI mHomeRetrofitAPI;
    private RequestManager mGlideRequestManager;
    private Call<MainPlaceListResponseVO> mCallMainPlaceListResponseVO;
    ListView listView;
    MainPlaceListMoreAdapter adapter;
    ProgressBar home_place_progress_bar;
    String address_arr[];
    String address = "";
    String query = "";

    public static MainPlaceInfoMoreFragment newInstance() {
        return new MainPlaceInfoMoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGlideRequestManager = Glide.with(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_place_info_more_fragment, container, false);

        home_place_progress_bar = (ProgressBar) v.findViewById(R.id.home_place_progress_bar);

        Intent intent = getActivity().getIntent();

        address = intent.getStringExtra("address");

        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (ListView) v.findViewById(R.id.home_place_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goWebview = new Intent(getContext(), PlaceInfoActivity.class);
                goWebview.putExtra("place_id", arrayList.get(position).getPlace_id());
                goWebview.putExtra("place_name", arrayList.get(position).getPlace_name());
                goWebview.putExtra("place_address", arrayList.get(position).getPlace_address());
                goWebview.putExtra("latitude", arrayList.get(position).getLatitude());
                goWebview.putExtra("longitude", arrayList.get(position).getLongitude());
                goWebview.putExtra("call_division", "MAIN");

                startActivityForResult(goWebview, 0);//액티비티 띄우기
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
        mHomeRetrofitAPI = mRetrofit.create(HomeRetrofitAPI.class);
    }

    private void callSearchResponse() {
        if (address.equals("")) {
            query = "서울 맛집";
        } else {
            address_arr = (address.split(" "));
            query = address_arr[1] + " " + address_arr[2] + " " + address_arr[3] + " " + "맛집";
        }
        mCallMainPlaceListResponseVO = mHomeRetrofitAPI.getMainPlaceMore(query);
        mCallMainPlaceListResponseVO.enqueue(mRetrofitCallback);
    }

    private Callback<MainPlaceListResponseVO> mRetrofitCallback = new Callback<MainPlaceListResponseVO>() {

        @Override
        public void onResponse(Call<MainPlaceListResponseVO> call, Response<MainPlaceListResponseVO> response) {
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
            adapter = new MainPlaceListMoreAdapter(getContext(), arrayList, mGlideRequestManager);
            listView.setAdapter(adapter);
            home_place_progress_bar.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(Call<MainPlaceListResponseVO> call, Throwable t) {
            Log.e("ss", "asdasdasd");
            t.printStackTrace();
        }
    };
}
