package com.dev.eatjeong.main.search.searchListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.searchListVO.NaverReviewVO;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;

public class NaverReviewListMoreAdapter extends BaseAdapter {
    private ArrayList<NaverReviewVO> data;
    private Context context;
    private RequestManager mGlide;

    public NaverReviewListMoreAdapter(Context context, ArrayList<NaverReviewVO> data, RequestManager mGlideRequestManager){
        this.context = context;
        this.data = data;
        this.mGlide = mGlideRequestManager;
    }

    @Override
    public int getCount() {
        return data.size(); // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }
    @Override
    public NaverReviewVO getItem(int position) {
        //position %= 4; // 0~3의 데이터의 포지션 값으로 지정해야함.
        Log.e("position" , String.valueOf(position));

        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_naver_item, null);

        AppCompatTextView naver_title = v.findViewById(R.id.naver_title);
        AppCompatTextView naver_description = v.findViewById(R.id.naver_description);
        AppCompatTextView naver_write_date = v.findViewById(R.id.naver_write_date);
        AppCompatTextView naver_author = v.findViewById(R.id.naver_author);
        AppCompatImageView naver_image = v.findViewById(R.id.naver_image);

        naver_title.setText(data.get(position).getTitle());
        naver_description.setText(data.get(position).getDescription());
        naver_write_date.setText(data.get(position).getWrite_date());
        naver_author.setText(data.get(position).getAuthor());

        ViewGroup.LayoutParams naver_image_params = naver_image.getLayoutParams();

        if(Util.isNullOrEmpty(data.get(position).getThumbnail_url())){
            naver_image_params.width = 1;
            naver_image.setLayoutParams(naver_image_params);
        }else{
            mGlide.load(data.get(position).getThumbnail_url())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(naver_image);
        }

        return v;
    }
}
