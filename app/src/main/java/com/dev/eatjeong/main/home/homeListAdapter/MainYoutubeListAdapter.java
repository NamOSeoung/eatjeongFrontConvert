package com.dev.eatjeong.main.home.homeListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;

import java.util.ArrayList;
import java.util.List;


public class MainYoutubeListAdapter extends RecyclerView.Adapter<MainYoutubeListAdapter.Holder> {
    private final RequestManager glide;
    private Context context;
    private List<MainReviewVO> list = new ArrayList<>();
    View view;

    public MainYoutubeListAdapter(Context context, List<MainReviewVO> list, RequestManager glide) {
        this.context = context;
        this.list = list;
        this.glide = glide;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_youtube_list_item, parent, false);
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
        String image_url = list.get(itemposition).getThumbnail_url();

        holder.title.setText(list.get(itemposition).getTitle());
        glide.load(image_url)
                .into(holder.thumbnail_image);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public AppCompatTextView title;
        public AppCompatImageView thumbnail_image;

        public Holder(View view){
            super(view);
            title = (AppCompatTextView) view.findViewById(R.id.youtube_title);
            thumbnail_image = (AppCompatImageView) view.findViewById(R.id.youtube_image);
        }
    }
}
