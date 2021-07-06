package com.dev.eatjeong.main.search.searchListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.searchListVO.PlaceListVO;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;

public class PlaceListAdapter extends BaseAdapter {
    private ArrayList<PlaceListVO> data;
    private Context context;
    private RequestManager mGlide;
    public PlaceListAdapter(Context context, ArrayList<PlaceListVO> data, RequestManager mGlideRequestManager){
        this.context = context;
        this.data = data;
        this.mGlide = mGlideRequestManager;
    }

    @Override
    public int getCount() {
        return data.size(); // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }
    @Override
    public PlaceListVO getItem(int position) {
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
        // position %= 4; // 0~3의 데이터의 포지션 값으로 지정해야함.
        View v = LayoutInflater.from(context).inflate(R.layout.place_list_item, null);
        AppCompatTextView place_name = v.findViewById(R.id.place_name);
        AppCompatImageView place_image = v.findViewById(R.id.place_image);
        AppCompatTextView place_category = v.findViewById(R.id.place_category);
        AppCompatTextView rating_point = v.findViewById(R.id.rating_point);
        String image_url = data.get(position).getBlog_thumbnail();

        place_name.setText(data.get(position).getPlace_name());
        place_category.setText(data.get(position).getCategory_name());

        if(!Util.isNullOrEmpty(data.get(position).getAppreview_rating())) {
            rating_point.setText(data.get(position).getAppreview_rating());
        }else if(!Util.isNullOrEmpty(data.get(position).getGoogle_rating())){
            rating_point.setText(data.get(position).getGoogle_rating());
        }else{
            rating_point.setText("0");
        }

        if(!Util.isNullOrEmpty(image_url)){
            place_image.setPadding(0,0,0,0);
        }

        mGlide.load(image_url)
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(30))
                )
                .placeholder(R.drawable.dinner_w_64)
                .into(place_image);

        return v;
    }
}