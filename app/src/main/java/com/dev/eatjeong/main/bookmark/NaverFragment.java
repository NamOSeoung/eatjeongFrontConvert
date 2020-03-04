package com.dev.eatjeong.main.bookmark;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class NaverFragment extends Fragment {

    String user_id;
    String sns_division;

    private ArrayList<BookmarkNaverListVO> arrayList = new ArrayList<BookmarkNaverListVO>();

    private Retrofit mRetrofit;

    private BookmarkRetrofitAPI mBookmarkRetrofitAPI;

    private Call<BookmarkNaverResponseVO> mCallBookmarkNaverResponseVO;

    ListView listView;

    BookmarkNaverListAdapter adapter;

    public static NaverFragment newInstance(){
        return new NaverFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookmark_naver_fragment, container, false);


        user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");


        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (ListView) v.findViewById(R.id.bookmark_naver_list);


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

        mCallBookmarkNaverResponseVO = mBookmarkRetrofitAPI.getBookmarkNaver("naver",user_id,sns_division);

        mCallBookmarkNaverResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<BookmarkNaverResponseVO> mRetrofitCallback = new Callback<BookmarkNaverResponseVO>() {



        @Override

        public void onResponse(Call<BookmarkNaverResponseVO> call, Response<BookmarkNaverResponseVO> response) {
            Log.e("dd",response.body().getCode());
            Log.e("dd",response.body().getMessage());

            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                Log.e("dd",response.body().mDatalist.get(i).toString());
                arrayList.add(new BookmarkNaverListVO(
                        response.body().mDatalist.get(i).getPlace_name()
                        //response.body().mDatalist.get(i).getCategory_name()
                ));

            }

            adapter = new BookmarkNaverListAdapter(getContext(),arrayList);
            listView.setAdapter(adapter);

        }



        @Override

        public void onFailure(Call<BookmarkNaverResponseVO> call, Throwable t) {


            Log.e("ss","asdasdasd");
            t.printStackTrace();

        }

    };
}