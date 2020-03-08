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

import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.search.searchListAdapter.PlaceListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.PopularListAdapter;
import com.dev.eatjeong.main.search.searchListVO.PlaceListVO;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceListFragment extends Fragment {

    private ArrayList<PlaceListVO> arrayList = new ArrayList<PlaceListVO>();

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchPlaceListResponseVO> mCallSearchPlaceListResponseVO;

    ListView listView;

    PlaceListAdapter adapter;

    ProgressBar place_list_progress_bar;
    public static PlaceListFragment newInstance(){
        return new PlaceListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_place_list_fragment, container, false);


        place_list_progress_bar = (ProgressBar)v.findViewById(R.id.place_list_progress_bar);
        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (ListView) v.findViewById(R.id.place_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String place_name = arrayList.get(position).getPlace_name();
                String place_id = arrayList.get(position).getPlace_id();
                String latitude = arrayList.get(position).getLatitute();
                String longitude = arrayList.get(position).getLongitude();
                String place_address = arrayList.get(position).getPlace_address();



                Intent goPlaceInfo = new Intent(getContext(), PlaceInfoActivity.class);
                goPlaceInfo.putExtra("place_name",place_name);
                goPlaceInfo.putExtra("place_id",place_id);
                goPlaceInfo.putExtra("latitude",latitude);
                goPlaceInfo.putExtra("longitude",longitude);
                goPlaceInfo.putExtra("place_address",place_address);
                goPlaceInfo.putExtra("call_division","SEARCH");
                startActivityForResult(goPlaceInfo, 1);//액티비티 띄우기
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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

        //Fragment 끼리는 호출이 불가능하기에 연결 된 Activity를 통해서 호출함.
        String keyword = ((MainWrapActivity)getActivity()).getKeyword();

        String user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        String sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");

        if(user_id == null){ //로그인 했을경우 검색어 + 로그인 아이디 추가
            mCallSearchPlaceListResponseVO = mSearchRetrofitAPI.getPlaceList(keyword);
        }else { //둘러보기일경우 검색어만 추가
            mCallSearchPlaceListResponseVO = mSearchRetrofitAPI.getPlaceList(keyword,user_id,sns_division);
        }



        mCallSearchPlaceListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchPlaceListResponseVO> mRetrofitCallback = new Callback<SearchPlaceListResponseVO>() {



        @Override

        public void onResponse(Call<SearchPlaceListResponseVO> call, Response<SearchPlaceListResponseVO> response) {

            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new PlaceListVO(
                        response.body().mDatalist.get(i).getPlace_id(),
                        response.body().mDatalist.get(i).getPlace_name(),
                        response.body().mDatalist.get(i).getLatitude(),
                        response.body().mDatalist.get(i).getLongitude(),
                        response.body().mDatalist.get(i).getPlace_address()));
            }

            adapter = new PlaceListAdapter(getContext(),arrayList);
            listView.setAdapter(adapter);

            place_list_progress_bar.setVisibility(View.GONE);

        }



        @Override

        public void onFailure(Call<SearchPlaceListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };

}