package com.dev.eatjeong.main.search.searchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.bookmarkListAdapter.BookmarkNaverListAdapter;
import com.dev.eatjeong.main.bookmark.bookmarkListWebview.BookmarkPlaceWebviewActivity;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchActivity.PlaceInfoActivity;
import com.dev.eatjeong.main.search.searchListAdapter.PopularListAdapter;
import com.dev.eatjeong.main.search.searchListAdapter.YoutubeReviewListAdapter;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchYoutubeListResponseVO;
import com.dev.eatjeong.main.search.searchReviewMoreActivirt.SearchYoutubeReviewMoreActivity;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeReviewFragment extends Fragment {
    String user_id,sns_division,place_id,place_address,place_name;

    private ArrayList<YoutubeReviewVO> arrayList = new ArrayList<YoutubeReviewVO>();

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<SearchYoutubeListResponseVO> mCallSearchYoutubeListResponseVO;

    RecyclerView listView;

    YoutubeReviewListAdapter adapter;

    ProgressBar youtube_progress_bar;

    TextView review_more;

    public static YoutubeReviewFragment newInstance(){
        return new YoutubeReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_youtube_review_fragment, container, false);

//        youtube_progress_bar = (ProgressBar)v.findViewById(R.id.youtube_progress_bar);

        user_id = ((PlaceInfoActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((PlaceInfoActivity)getActivity()).getUserInfo().get("sns_division");
        place_name = ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_name");
        place_id =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_id");
        place_address =   ((PlaceInfoActivity)getActivity()).getPlaceInfo().get("place_address");


        review_more = (TextView)v.findViewById(R.id.review_more);
//
        //레트로핏 연결하기위한 초기화 작업.
        setRetrofitInit();

        //레트로핏 초기화 후 호출작업 진행.
        callSearchResponse();

        listView = (RecyclerView) v.findViewById(R.id.recycler_view);



        review_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMore = new Intent(getContext(), SearchYoutubeReviewMoreActivity.class);
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
        String place_name_arr[] = place_name.split(" ");
        String place_address_arr[] = place_address.split(" ");

        if(place_address_arr.length>2){
            place_address_arr[1] = place_address_arr[1].replace("시","").replace("군","").replace("구","");
        }

        if(user_id == null){
            mCallSearchYoutubeListResponseVO = mSearchRetrofitAPI.getYoutubeReview(place_id,user_id,sns_division,place_address_arr[1] + " " + place_name_arr[0] + " " + "맛집","5");
        }else{
            mCallSearchYoutubeListResponseVO = mSearchRetrofitAPI.getYoutubeReview(place_id,"temp","T",place_address_arr[1] + " " + place_name_arr[0] + " " + "맛집","5");
        }


        mCallSearchYoutubeListResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<SearchYoutubeListResponseVO> mRetrofitCallback = new Callback<SearchYoutubeListResponseVO>() {

        @Override

        public void onResponse(Call<SearchYoutubeListResponseVO> call, Response<SearchYoutubeListResponseVO> response) {
            arrayList.clear();
            for(int i = 0; i < response.body().mDatalist.size(); i ++){
                arrayList.add(new YoutubeReviewVO(
                        response.body().mDatalist.get(i).getIndex(),
                        response.body().mDatalist.get(i).getReview_id(),
                        response.body().mDatalist.get(i).getTitle(),
                        response.body().mDatalist.get(i).getWrite_date(),
                        response.body().mDatalist.get(i).getAuthor(),
                        response.body().mDatalist.get(i).getUrl(),
                        response.body().mDatalist.get(i).getThumbnail_url(),
                        response.body().mDatalist.get(i).getBookmark_flag()
                ));
            }

            listView.setHasFixedSize(true);
            adapter = new YoutubeReviewListAdapter(getActivity(), arrayList);
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));
            listView.setAdapter(adapter);

            //가로 레이아웃
            LinearLayoutManager horizonalLayoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            listView.setLayoutManager(horizonalLayoutManager);
        }



        @Override

        public void onFailure(Call<SearchYoutubeListResponseVO> call, Throwable t) {

            t.printStackTrace();

        }

    };
}