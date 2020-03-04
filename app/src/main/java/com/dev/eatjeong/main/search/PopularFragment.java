package com.dev.eatjeong.main.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularFragment extends Fragment {

    private ArrayList<PopularVO> arrayList = new ArrayList<PopularVO>();

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchResponseVO> mCallSearchResponseVO;

    ListView listView;

    PopularListAdapter adapter;
    public static PopularFragment newInstance(){
        return new PopularFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_popular_fragment, container, false);


        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (ListView) v.findViewById(R.id.popular_keyword_list);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("as",arrayList.get(position).getKeyword());

                //Fragment 끼리는 호출이 불가능하기에 연결 된 Activity를 통해서 호출함.
                ((MainWrapActivity)getActivity()).changeText(arrayList.get(position).getKeyword());



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

        mCallSearchResponseVO = mSearchRetrofitAPI.getPopularSearches();

        mCallSearchResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchResponseVO> mRetrofitCallback = new Callback<SearchResponseVO>() {



        @Override

        public void onResponse(Call<SearchResponseVO> call, Response<SearchResponseVO> response) {

            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new PopularVO(response.body().mDatalist.get(i).getSearch_word()));
            }

            adapter = new PopularListAdapter(getContext(),arrayList);
            listView.setAdapter(adapter);

        }



        @Override

        public void onFailure(Call<SearchResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };


}