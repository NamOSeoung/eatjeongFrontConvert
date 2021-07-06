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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.searchListVO.TistoryReviewVO;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TistoryReviewListAdapter extends RecyclerView.Adapter<TistoryReviewListAdapter.Holder> {

    private Context context;
    private List<TistoryReviewVO> list = new ArrayList<>();
    private RequestManager mGlide;
    private View view;

    public TistoryReviewListAdapter(Context context, List<TistoryReviewVO> list, RequestManager mGlideRequestManager) {
        this.context = context;
        this.list = list;
        this.mGlide = mGlideRequestManager;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tistory_review_list_item, parent, false);
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
        String thumbnail_url = list.get(itemposition).getThumbnail_url();

        ViewGroup.LayoutParams imageLayoutParams = (ViewGroup.LayoutParams)holder.tistory_image.getLayoutParams();

        if(Util.isNullOrEmpty(thumbnail_url)){
            view.findViewById(R.id.tistory_image).getLayoutParams();
            imageLayoutParams.width = 10;
            holder.tistory_image.setLayoutParams(imageLayoutParams);
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
                    .into(holder.tistory_image);
        }
        holder.tistory_title.setText(list.get(itemposition).getTitle());
        holder.tistory_write_date.setText(list.get(itemposition).getWrite_date());
        holder.tistory_author.setText(list.get(itemposition).getAuthor());
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        private AppCompatTextView tistory_title, tistory_description, tistory_write_date, tistory_author;
        private AppCompatImageView tistory_image;
        private ProgressBar progress_bar;

        public Holder(View view){
            super(view);
            tistory_image = (AppCompatImageView) view.findViewById(R.id.tistory_image);
            tistory_title = (AppCompatTextView) view.findViewById(R.id.tistory_title);
            tistory_write_date = (AppCompatTextView) view.findViewById(R.id.tistory_write_date);
            tistory_author = (AppCompatTextView) view.findViewById(R.id.tistory_author);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }
}
