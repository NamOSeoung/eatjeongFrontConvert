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
import com.dev.eatjeong.main.search.searchListVO.NaverReviewVO;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;
import java.util.List;

public class NaverReviewListAdapter extends RecyclerView.Adapter<NaverReviewListAdapter.Holder> {

    private Context context;
    private List<NaverReviewVO> list = new ArrayList<>();
    private RequestManager mGlide;
    private View view;

    public NaverReviewListAdapter(Context context, List<NaverReviewVO> list, RequestManager mGlideRequestManager) {
        this.context = context;
        this.list = list;
        this.mGlide = mGlideRequestManager;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_naver_review_list_item, parent, false);
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

        ViewGroup.LayoutParams imageLayoutParams = (ViewGroup.LayoutParams)holder.naver_image.getLayoutParams();

        if(Util.isNullOrEmpty(thumbnail_url)){
            view.findViewById(R.id.naver_image).getLayoutParams();
            imageLayoutParams.width = 10;
            holder.naver_image.setLayoutParams(imageLayoutParams);
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
                    .into(holder.naver_image);
        }
        holder.naver_title.setText(list.get(itemposition).getTitle());
        holder.naver_description.setText(list.get(itemposition).getDescription());
        holder.naver_write_date.setText(list.get(itemposition).getWrite_date());
        holder.naver_author.setText(list.get(itemposition).getAuthor());
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        private AppCompatTextView naver_title, naver_description, naver_write_date, naver_author;
        private AppCompatImageView naver_image;
        private ProgressBar progress_bar;

        public Holder(View view){
            super(view);
            naver_image = (AppCompatImageView) view.findViewById(R.id.naver_image);
            naver_title = (AppCompatTextView) view.findViewById(R.id.naver_title);
            naver_description = (AppCompatTextView) view.findViewById(R.id.naver_description);
            naver_write_date = (AppCompatTextView) view.findViewById(R.id.naver_write_date);
            naver_author = (AppCompatTextView) view.findViewById(R.id.naver_author);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }
}
