package com.dev.eatjeong.main.search.searchListAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.searchListVO.AppReviewVO;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;
import java.util.List;

public class AppReviewListAdapter extends RecyclerView.Adapter<AppReviewListAdapter.Holder> {

    private Context context;
    private View view;
    private List<AppReviewVO> list = new ArrayList<>();
    private RequestManager mGlide;

    public AppReviewListAdapter(Context context, List<AppReviewVO> list, RequestManager mGlideRequestManager) {
        this.context = context;
        this.list = list;
        this.mGlide = mGlideRequestManager;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_app_review_list_item, parent, false);
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
        Log.e("AppReviewListAdapter", list.get(itemposition).toString());

        String rating_point = list.get(itemposition).getRating_point();
        List<String> thumbnail_url_list = list.get(itemposition).getImage_url();
        String thumbnail_url = thumbnail_url_list.get(0);

        holder.write_date.setText(list.get(itemposition).getWrite_date());
        holder.google_text_view.setText(list.get(itemposition).getReview_contents());

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

        ViewGroup.LayoutParams imageLayoutParams = (ViewGroup.LayoutParams)holder.appreview_image.getLayoutParams();

        if(Util.isNullOrEmpty(thumbnail_url)){
            view.findViewById(R.id.tistory_image).getLayoutParams();
            imageLayoutParams.width = 10;
            holder.appreview_image.setLayoutParams(imageLayoutParams);
            holder.progress_bar.setVisibility(View.GONE);
        }else{
            mGlide.load(thumbnail_url)
                    .override(200,200)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progress_bar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progress_bar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.appreview_image);
        }


    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        private AppCompatImageView appreview_image, star_y1, star_y2, star_y3, star_y4, star_y5;
        private AppCompatTextView write_date, google_text_view;
        private ProgressBar progress_bar;

        public Holder(View view){
            super(view);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
            appreview_image = view.findViewById(R.id.appreview_image);
            star_y1 = view.findViewById(R.id.star_y1);
            star_y2 = view.findViewById(R.id.star_y2);
            star_y3 = view.findViewById(R.id.star_y3);
            star_y4 = view.findViewById(R.id.star_y4);
            star_y5 = view.findViewById(R.id.star_y5);
            write_date = view.findViewById(R.id.write_date);
            google_text_view = view.findViewById(R.id.google_text_view);
        }
    }
}
