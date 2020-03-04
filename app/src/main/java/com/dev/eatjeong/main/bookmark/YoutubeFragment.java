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

public class YoutubeFragment extends Fragment {

    String user_id;
    String sns_division;

    private ArrayList<BookmarkYoutubeListVO> arrayList = new ArrayList<BookmarkYoutubeListVO>();

    private Retrofit mRetrofit;

    private BookmarkRetrofitAPI mBookmarkRetrofitAPI;

    private Call<BookmarkYoutubeResponseVO> mCallBookmarkYoutubeResponseVO;

    ListView listView;

    BookmarkYoutubeListAdapter adapter;

    public static YoutubeFragment newInstance(){
        return new YoutubeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookmark_youtube_fragment, container, false);

        user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");


        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (ListView) v.findViewById(R.id.bookmark_youtube_list);

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

        mCallBookmarkYoutubeResponseVO = mBookmarkRetrofitAPI.getBookmarkYoutube("youtube",user_id,sns_division);

        mCallBookmarkYoutubeResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<BookmarkYoutubeResponseVO> mRetrofitCallback = new Callback<BookmarkYoutubeResponseVO>() {



        @Override

        public void onResponse(Call<BookmarkYoutubeResponseVO> call, Response<BookmarkYoutubeResponseVO> response) {
            Log.e("dd",response.body().getCode());
            Log.e("dd",response.body().getMessage());

            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                Log.e("dd",response.body().mDatalist.get(i).toString());
                arrayList.add(new BookmarkYoutubeListVO(
                        response.body().mDatalist.get(i).getPlace_name()
                        //response.body().mDatalist.get(i).getCategory_name()
                ));

            }

            adapter = new BookmarkYoutubeListAdapter(getContext(),arrayList);
            listView.setAdapter(adapter);

        }



        @Override

        public void onFailure(Call<BookmarkYoutubeResponseVO> call, Throwable t) {


            Log.e("ss","asdasdasd");
            t.printStackTrace();

        }

    };
}