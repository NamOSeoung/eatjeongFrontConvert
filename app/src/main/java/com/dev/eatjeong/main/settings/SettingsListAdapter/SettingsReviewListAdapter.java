package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewListResponseVO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SettingsReviewListAdapter extends RecyclerView.Adapter<SettingsReviewListAdapter.Holder> {

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    private Context context;
    private String category_code;
    private int item_count;
    private List<SettingsMyReviewListResponseVO.DataList> review_list = new ArrayList<SettingsMyReviewListResponseVO.DataList>();

    public SettingsReviewListAdapter(Context context,
                                     ArrayList<SettingsMyReviewListResponseVO.DataList> review_list,String category_code,int item_count) {
        this.category_code = category_code;
        this.context = context;
        this.review_list = review_list;
        this.item_count = item_count;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_my_review_list_item, parent, false);
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

        final int Position = position;
        holder.place_name.setText(review_list.get(itemposition).getPlace_name());
        String rating_point = review_list.get(itemposition).getRating();
        holder.avg_point.setText(review_list.get(itemposition).getRating_avg());
        holder.write_date.setText(review_list.get(itemposition).getWrite_date());
        holder.like_count.setText(review_list.get(itemposition).getLike_count());


        if(review_list.get(itemposition).getLike_flag().equals("true")){
            holder.like_image_d.setImageResource(R.drawable.like_r_128);
        }else {
            holder.like_image_d.setImageResource(R.drawable.heart_d_128);
        }
        if(rating_point.equals("1")){
            holder.star_y1.setImageResource(R.drawable.star_y2_128);
            holder.star_y2.setImageResource(R.drawable.star_d_128);
            holder.star_y3.setImageResource(R.drawable.star_d_128);
            holder.star_y4.setImageResource(R.drawable.star_d_128);
            holder.star_y5.setImageResource(R.drawable.star_d_128);
        }else if(rating_point.equals("2")){
            holder.star_y1.setImageResource(R.drawable.star_y2_128);
            holder.star_y2.setImageResource(R.drawable.star_y2_128);
            holder.star_y3.setImageResource(R.drawable.star_d_128);
            holder.star_y4.setImageResource(R.drawable.star_d_128);
            holder.star_y5.setImageResource(R.drawable.star_d_128);
        }else if(rating_point.equals("3")){
            holder.star_y1.setImageResource(R.drawable.star_y2_128);
            holder.star_y2.setImageResource(R.drawable.star_y2_128);
            holder.star_y3.setImageResource(R.drawable.star_y2_128);
            holder.star_y4.setImageResource(R.drawable.star_d_128);
            holder.star_y5.setImageResource(R.drawable.star_d_128);
        }else if(rating_point.equals("4")){
            holder.star_y1.setImageResource(R.drawable.star_y2_128);
            holder.star_y2.setImageResource(R.drawable.star_y2_128);
            holder.star_y3.setImageResource(R.drawable.star_y2_128);
            holder.star_y4.setImageResource(R.drawable.star_y2_128);
            holder.star_y5.setImageResource(R.drawable.star_d_128);
        }else if(rating_point.equals("5")){
            holder.star_y1.setImageResource(R.drawable.star_y2_128);
            holder.star_y2.setImageResource(R.drawable.star_y2_128);
            holder.star_y3.setImageResource(R.drawable.star_y2_128);
            holder.star_y4.setImageResource(R.drawable.star_y2_128);
            holder.star_y5.setImageResource(R.drawable.star_y2_128);
        }

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return item_count; // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView place_name;
        AppCompatTextView avg_point,write_date,like_count;
        public AppCompatImageView like_image_d,star_y1,star_y2,star_y3,star_y4,star_y5;

        public Holder(View view){
            super(view);
            place_name = (TextView) view.findViewById(R.id.place_name);
            like_image_d = view.findViewById(R.id.like_image_d);
            star_y1 = view.findViewById(R.id.star_y1);
            star_y2 = view.findViewById(R.id.star_y2);
            star_y3 = view.findViewById(R.id.star_y3);
            star_y4 = view.findViewById(R.id.star_y4);
            star_y5 = view.findViewById(R.id.star_y5);
            avg_point = view.findViewById(R.id.avg_point);
            write_date = view.findViewById(R.id.write_date);
            like_count = view.findViewById(R.id.like_count);
        }
    }
}