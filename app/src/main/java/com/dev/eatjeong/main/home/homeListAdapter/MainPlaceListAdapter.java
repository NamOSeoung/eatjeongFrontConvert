package com.dev.eatjeong.main.home.homeListAdapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
import com.dev.eatjeong.layout.RoundedCornersTransformation;
import com.dev.eatjeong.main.home.homeVO.MainPlaceVO;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;
import com.dev.eatjeong.main.search.searchListVO.PlaceListVO;

import java.util.ArrayList;
import java.util.List;


public class MainPlaceListAdapter extends RecyclerView.Adapter<MainPlaceListAdapter.Holder> {
    private final RequestManager glide;
    private Context context;
    private List<MainPlaceVO> list = new ArrayList<>();
    View view;

    public MainPlaceListAdapter(Context context, List<MainPlaceVO> list, RequestManager glide) {
        this.context = context;
        this.list = list;
        this.glide = glide;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_place_list_item, parent, false);
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
        String image_url = list.get(itemposition).getBlog_thumbnail();
        System.out.println("URL" + list.get(itemposition).getBlog_thumbnail());
        if(image_url != null){
            holder.place_image.setPadding(0,0,0,0);
        }
       glide.load(image_url)
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(30))
                )
                .placeholder(R.drawable.dinner_w_64)
                .into(holder.place_image);
        holder.place_name.setText(list.get(itemposition).getPlace_name());
        holder.place_category.setText(list.get(itemposition).getCategory_name());
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public AppCompatTextView place_name;
        public AppCompatImageView place_image;
        public AppCompatTextView place_category;

        public Holder(View view){
            super(view);
            place_image = (AppCompatImageView) view.findViewById(R.id.place_image);
            place_name = (AppCompatTextView) view.findViewById(R.id.place_name);
            place_category = (AppCompatTextView) view.findViewById(R.id.place_category);
        }
    }
}
