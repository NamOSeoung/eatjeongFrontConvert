package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewDetailListResponseVO;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SettingsReviewMyListAdapter extends RecyclerView.Adapter<SettingsReviewMyListAdapter.Holder> {
    public Button review_delete, like_delete, like_add;
    public TextView review_user_id, review_user_nickname, review_contents, rating_point, like_count, like_flag, image_url,review_update;
    //아이템 클릭시 실행 함수
    private ItemClick itemClick;

    public interface ItemClick {
        public void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void responseItemStatus(int controll_flag, int view_id) {

        //컨트롤러 부분에서 통신 성공하고 callback 호출하는 부분임.
        if (controll_flag == 0) {
            like_add.setVisibility(View.GONE);
            like_delete.setVisibility(View.VISIBLE);
            like_count.setText(String.valueOf(Integer.parseInt(like_count.getText().toString())+1));
            like_flag.setText("true");
        } else if (controll_flag == 1) {
            like_add.setVisibility(View.VISIBLE);
            like_delete.setVisibility(View.GONE);
            like_count.setText(String.valueOf(Integer.parseInt(like_count.getText().toString())-1));
            like_flag.setText("false");
        }
    }

    private Context context;
    private List<SettingsMyReviewDetailListResponseVO.DataList.myReviewList> review_list = new ArrayList<SettingsMyReviewDetailListResponseVO.DataList.myReviewList>();

    public SettingsReviewMyListAdapter(Context context,
                                       ArrayList<SettingsMyReviewDetailListResponseVO.DataList.myReviewList> review_list) {
        this.context = context;
        this.review_list = review_list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_review_list_detail_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        Handler handler = new Handler();  // 외부쓰레드 에서 메인 UI화면을 그릴때 사용

        final int Position = position;
        review_user_id.setText(review_list.get(itemposition).getReview_user_id());
        review_user_nickname.setText(review_list.get(itemposition).getReview_user_nickname());
        review_contents.setText(review_list.get(itemposition).getReview_contents());
        rating_point.setText(review_list.get(itemposition).getRating_point());
        like_flag.setText(review_list.get(itemposition).getLike_flag());
        like_count.setText(review_list.get(itemposition).getLike_count());
        image_url.setText(review_list.get(itemposition).getImage_url().get(0));


        if (review_list.get(itemposition).getLike_flag().equals("true")) {
            like_add.setVisibility(View.GONE);
            like_delete.setVisibility(View.VISIBLE);
        } else {
            like_add.setVisibility(View.VISIBLE);
            like_delete.setVisibility(View.GONE);
        }

        //좋아요 추가
        like_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(v, Position);
                }
            }
        });
        //좋아요 삭제
        like_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(v, Position);
                }
            }
        });
        //리뷰 삭제
        review_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(v, Position);
                }
            }
        });
        //리뷰 수정 화면이동
        review_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(v, Position);
                }
            }
        });


    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return review_list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder {


        public ImageView review_image1, review_image2, review_image3, review_image4;


        public Holder(View view) {
            super(view);
            review_user_id = (TextView) view.findViewById(R.id.review_user_id);
            review_user_nickname = (TextView) view.findViewById(R.id.review_user_nickname);
            review_contents = (TextView) view.findViewById(R.id.review_contents);
            rating_point = (TextView) view.findViewById(R.id.rating_point);
            like_flag = (TextView) view.findViewById(R.id.like_flag);
            like_count = (TextView) view.findViewById(R.id.like_count);
            image_url = (TextView) view.findViewById(R.id.image_url);
            review_image1 = view.findViewById(R.id.review_image1);
            review_image2 = view.findViewById(R.id.review_image2);
            review_image3 = view.findViewById(R.id.review_image3);
            review_image4 = view.findViewById(R.id.review_image4);

            review_delete = view.findViewById(R.id.review_delete);
            like_add = view.findViewById(R.id.like_add);
            like_delete = view.findViewById(R.id.like_delete);
            review_update = view.findViewById(R.id.review_update);
        }
    }
}