package com.dev.eatjeong.main.home.homeListAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;

import java.util.ArrayList;
import java.util.List;


public class MainTistoryListAdapter extends RecyclerView.Adapter<MainTistoryListAdapter.Holder> {

    private final RequestManager glide;
    private Context context;
    private List<MainReviewVO> list = new ArrayList<>();
    View view;

    public MainTistoryListAdapter(Context context, List<MainReviewVO> list, RequestManager glide) {
        this.context = context;
        this.list = list;
        this.glide = glide;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_tistory_list_item, parent, false);
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

        System.out.println("thumbnail_url : " + thumbnail_url);
        System.out.println("thumbnail_url class : " + thumbnail_url.getClass());
        if(thumbnail_url.equals("")){
            view.findViewById(R.id.tistory_image).getLayoutParams();
            imageLayoutParams.width = 20;
            holder.tistory_image.setLayoutParams(imageLayoutParams);
        }else{
            glide.load(thumbnail_url)
                    .override(200,200)
                    .apply(new RequestOptions()
                            .transform(new RoundedCorners(30))
                    )
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
        public AppCompatTextView tistory_title, tistory_description, tistory_write_date, tistory_author;
        public AppCompatImageView tistory_image;

        public Holder(View view){
            super(view);
            tistory_image = (AppCompatImageView) view.findViewById(R.id.tistory_image);
            tistory_title = (AppCompatTextView) view.findViewById(R.id.tistory_title);
            tistory_write_date = (AppCompatTextView) view.findViewById(R.id.tistory_write_date);
            tistory_author = (AppCompatTextView) view.findViewById(R.id.tistory_author);
        }
    }
}
