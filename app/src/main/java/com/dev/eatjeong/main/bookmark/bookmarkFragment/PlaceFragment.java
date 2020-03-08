package com.dev.eatjeong.main.bookmark.bookmarkFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.bookmarkListAdapter.BookmarkPlaceListAdapter;
import com.dev.eatjeong.main.bookmark.bookmarkListVO.BookmarkPlaceListVO;
import com.dev.eatjeong.main.bookmark.BookmarkRetrofitAPI;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkPlaceResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkActivity.PlaceInfoActivity;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceFragment extends Fragment {


    public static final int sub = 1003; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    String user_id;
    String sns_division;

    private ArrayList<BookmarkPlaceListVO> arrayList = new ArrayList<BookmarkPlaceListVO>();

    private Retrofit mRetrofit;

    private BookmarkRetrofitAPI mBookmarkRetrofitAPI;

    private Call<BookmarkPlaceResponseVO> mCallBookmarkPlaceResponseVO;

    ListView listView;

    BookmarkPlaceListAdapter adapter;

    ProgressBar bookmark_place_progress_bar;
    public static PlaceFragment newInstance(){
        return new PlaceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookmark_place_fragment, container, false);

        bookmark_place_progress_bar = (ProgressBar)v.findViewById(R.id.bookmark_place_progress_bar);
        user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");

        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (ListView) v.findViewById(R.id.bookmark_place_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goWebview = new Intent(getContext(), PlaceInfoActivity.class);
                goWebview.putExtra("place_id",arrayList.get(position).getPlace_id());
                goWebview.putExtra("place_name",arrayList.get(position).getPlace_name());
                goWebview.putExtra("place_address",arrayList.get(position).getPlace_address());
                goWebview.putExtra("latitude",arrayList.get(position).getLatitude());
                goWebview.putExtra("longitude",arrayList.get(position).getLongitude());
                goWebview.putExtra("user_id",user_id);
                goWebview.putExtra("sns_division",sns_division);
                goWebview.putExtra("call_division","BOOKMARK");


                startActivityForResult(goWebview,0);//액티비티 띄우기
                getActivity().overridePendingTransition(R.anim.fadein,0);
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


        mBookmarkRetrofitAPI = mRetrofit.create(BookmarkRetrofitAPI.class);

    }

    private void callSearchResponse() {

        mCallBookmarkPlaceResponseVO = mBookmarkRetrofitAPI.getBookmarkPlace("place",user_id,sns_division);

        mCallBookmarkPlaceResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<BookmarkPlaceResponseVO> mRetrofitCallback = new Callback<BookmarkPlaceResponseVO>() {


        @Override

        public void onResponse(Call<BookmarkPlaceResponseVO> call, Response<BookmarkPlaceResponseVO> response) {
            arrayList.clear();
            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new BookmarkPlaceListVO(
                        response.body().mDatalist.get(i).getPlace_id(),
                        response.body().mDatalist.get(i).getPlace_name(),
                        response.body().mDatalist.get(i).getPlace_address(),
                        response.body().mDatalist.get(i).getLatitude(),
                        response.body().mDatalist.get(i).getLongitude(),
                        response.body().mDatalist.get(i).getCategory_name()
                ));
            }

            adapter = new BookmarkPlaceListAdapter(getContext(),arrayList);
            listView.setAdapter(adapter);

            bookmark_place_progress_bar.setVisibility(View.GONE);

        }



        @Override

        public void onFailure(Call<BookmarkPlaceResponseVO> call, Throwable t) {


            Log.e("ss","asdasdasd");
            t.printStackTrace();

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){
                bookmark_place_progress_bar.setVisibility(View.VISIBLE);
                //레트로핏 연결하기위한 초기화 작업.
                setRetrofitInit();

                //재호출
                callSearchResponse();

            }

        }
    }

}