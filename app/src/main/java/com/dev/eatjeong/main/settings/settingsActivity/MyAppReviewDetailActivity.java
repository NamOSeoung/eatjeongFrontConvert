package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsReviewMyListAdapter;
import com.dev.eatjeong.main.settings.SettingsListAdapter.SettingsReviewOtherListAdapter;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewDetailListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewListResponseVO;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAppReviewDetailActivity extends AppCompatActivity {


    ArrayList<SettingsMyReviewDetailListResponseVO.DataList.myReviewList> myReviewList = new ArrayList<>();
    ArrayList<SettingsMyReviewDetailListResponseVO.DataList.otherReviewList> otherReviewList = new ArrayList<>();

    ArrayList<SettingsMyReviewListResponseVO.DataList> myReviews = new ArrayList<SettingsMyReviewListResponseVO.DataList>();
    ArrayList<SettingsMyReviewListResponseVO.DataList> setMyReviews = new ArrayList<SettingsMyReviewListResponseVO.DataList>();

    int listPosition;
    private String portal, write_author, division;
    private String user_id, sns_division, place_name, review_id, place_id;

    TextView place_name_text;

    Button youtube_btn, naver_btn, tistory_btn, post_btn, author_btn;

    RecyclerView my_review_recycler_view, other_review_recycler_view;

//    ProgressBar progressBar;

    SettingsReviewMyListAdapter myReviewAdapter;
    SettingsReviewOtherListAdapter otherReviewAdapter;

    MyReviewListControll myReviewListControll = new MyReviewListControll();
    ConstraintLayout back_button;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_my_review_detail_list);

        place_name_text = findViewById(R.id.place_name);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        sns_division = intent.getStringExtra("sns_division");
        place_name = intent.getStringExtra("place_name");
        review_id = intent.getStringExtra("review_id");
        place_id = intent.getStringExtra("place_id");

        Log.e("place_id", place_id);
        Log.e("review_id", review_id);

        View action_bar = findViewById(R.id.action_bar);

        TextView back_text = action_bar.findViewById(R.id.back_text);
        TextView textview1 = action_bar.findViewById(R.id.textview1);
        back_button = action_bar.findViewById(R.id.back_button);
        back_text.setText("리뷰목록");
        textview1.setVisibility(View.INVISIBLE);
        place_name_text.setText(place_name);


//        progressBar = (ProgressBar) findViewById(R.id.progress);

        myReviewListControll.setRetrofitInit();

        my_review_recycler_view = (RecyclerView) findViewById(R.id.my_review_recycler_view);
        other_review_recycler_view = (RecyclerView) findViewById(R.id.other_review_recycler_view);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // 검색 동작
        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

        setResult(1, intent);
        // finish();
        super.onBackPressed();

    }

    //내 리뷰 상세 검색 내부클래스
    public class MyReviewListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<SettingsMyReviewDetailListResponseVO> mCallSettingsMyReviewDetailListResponseVO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */


            if (user_id == null) {
                return;
            }

            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

            callResponse();

        }

        private void callResponse() {


            mCallSettingsMyReviewDetailListResponseVO = mSettingsRetrofitAPI.getMyReviewsDetail(place_id, review_id, user_id, sns_division);
            mCallSettingsMyReviewDetailListResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<SettingsMyReviewDetailListResponseVO> mRetrofitCallback = new Callback<SettingsMyReviewDetailListResponseVO>() {
            @Override
            public void onResponse(Call<SettingsMyReviewDetailListResponseVO> call, Response<SettingsMyReviewDetailListResponseVO> response) {

                Log.e("code : ", response.body().getCode());
                Log.e("message : ", response.body().getMessage());

                if (response.body().getCode().equals("200")) {
                    myReviewList.clear();
                    otherReviewList.clear();

                    myReviewList.addAll(response.body().mDatalist.myReviewList);
                    otherReviewList.addAll(response.body().mDatalist.otherReviewList);

                    my_review_recycler_view.setHasFixedSize(true);
                    myReviewAdapter = new SettingsReviewMyListAdapter(getApplicationContext(), myReviewList);
                    my_review_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    my_review_recycler_view.setAdapter(myReviewAdapter);

                    other_review_recycler_view.setHasFixedSize(true);
                    otherReviewAdapter = new SettingsReviewOtherListAdapter(getApplicationContext(), otherReviewList);
                    other_review_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    other_review_recycler_view.setAdapter(otherReviewAdapter);

                    myReviewAdapter.setItemClick(new SettingsReviewMyListAdapter.ItemClick() {
                        @Override
                        public void onClick(View view, int position) {
                            ReviewsControll reviewsControll = new ReviewsControll();
                            //클릭시 실행될 함수 작성
//                            if (view.getId() == R.id.like_add) {
//                                reviewsControll.controll_flag = 0;
//                                reviewsControll.view = view.getId();
//                                reviewsControll.setRetrofitInit();
//                            } else if (view.getId() == R.id.like_delete) {
//                                reviewsControll.controll_flag = 1;
//                                reviewsControll.view = view.getId();
//                                reviewsControll.setRetrofitInit();
//                            } else
                            if (view.getId() == R.id.review_delete) {
                                reviewsControll.controll_flag = 2;
                                reviewsControll.view = view.getId();
                                reviewsControll.setRetrofitInit();
                            } else if (view.getId() == R.id.review_update){
                                Intent reviewUpdate = new Intent(getApplicationContext(), ReviewUpdateActivity.class);
                                Toast.makeText(getApplicationContext(), myReviewList.get(position).getRating_point(), Toast.LENGTH_SHORT).show();
                                reviewUpdate.putExtra("review_id",review_id);
                                reviewUpdate.putExtra("place_id",place_id);
                                reviewUpdate.putExtra("review_contents",myReviewList.get(position).getReview_contents());
                                reviewUpdate.putExtra("rating_point",myReviewList.get(position).getRating_point());
                                reviewUpdate.putStringArrayListExtra("image_url",myReviewList.get(position).getImage_url());
                                startActivityForResult(reviewUpdate,0);//액티비티 띄우기
                                overridePendingTransition(R.anim.fadein,0);
                            }
                        }
                    });

                }
//                progressBar.setVisibility(View.GONE);

            }

            @Override

            public void onFailure(Call<SettingsMyReviewDetailListResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }

    //리뷰 삭제, 좋아요 추가,제거 컨트롤 위한 내부 클래스
    public class ReviewsControll {

        /*
         *
         * controll_flag = 0:좋아요 추가, 1:좋아요 삭제, 2:리뷰 삭제
         *
         *  */
        int controll_flag = 0;
        int view = 0;
        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */


            if (user_id == null) {
                return;
            }

            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

            callResponse();

        }

        private void callResponse() {
            if (controll_flag == 0) { //좋아요 추가
                mCallCommonMapResponseVO = mSettingsRetrofitAPI.setLikeFlag(review_id, user_id, sns_division);
            } else if (controll_flag == 1) { //좋아요 삭제
                mCallCommonMapResponseVO = mSettingsRetrofitAPI.deleteLikeFlag(review_id, user_id, sns_division);
            } else if (controll_flag == 2) { //내 리뷰 삭제
                mCallCommonMapResponseVO = mSettingsRetrofitAPI.deleteReview(place_id, review_id, user_id, sns_division);
            }
            mCallCommonMapResponseVO.enqueue(mRetrofitCallback);
        }

        private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
            @Override
            public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {

                Log.e("code : ", String.valueOf(response.body()));
                Log.e("code : ", response.body().getMessage());
                Log.e("message : ", response.body().getCode());

                if (String.valueOf(response.body().getCode()).equals("200")) {
                    if(controll_flag == 2){ //내 리뷰 삭제했을 경우는 그냥 뒤로감
                        onBackPressed();
                    }else {
                        myReviewListControll.setRetrofitInit();
                    }

                }
            }

            @Override

            public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){
                Toast.makeText(getApplicationContext(),"back 정상적으로 호출" , Toast.LENGTH_SHORT).show();
                //뒤로오면 무조건 다시 호출(내 리뷰 디테일 화면에서 리뷰 삭제됬을수도 있기 때문에.)
                myReviewListControll.setRetrofitInit();
            }

        }
    }
}
