package com.dev.eatjeong.main.search.searchListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.bookmark.bookmarkListVO.BookmarkYoutubeListVO;
import com.dev.eatjeong.main.search.searchFragment.YoutubeReviewFragment;
import com.dev.eatjeong.main.search.searchListVO.YoutubeReviewVO;

import java.util.ArrayList;
import java.util.List;

public class YoutubeReviewListAdapter extends RecyclerView.Adapter<YoutubeReviewListAdapter.Holder> {

    private Context context;
    private List<YoutubeReviewVO> list = new ArrayList<>();

    public YoutubeReviewListAdapter(Context context, List<YoutubeReviewVO> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_youtube_review_list_item, parent, false);
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
        holder.review_id.setText(list.get(itemposition).getReview_id());

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView review_id;

        public Holder(View view){
            super(view);
            review_id = (TextView) view.findViewById(R.id.review_id);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition() ;
                    Toast.makeText(context,list.get(position).getTitle(),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}