package com.dev.eatjeong.main.home.homeListAdapter;

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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;
import com.dev.eatjeong.util.Util;

import java.util.ArrayList;

public class MainTistoryListMoreAdapter extends BaseAdapter {
    private ArrayList<MainReviewVO> data;
    private Context context;
    private RequestManager mGlide;

    public MainTistoryListMoreAdapter(Context context, ArrayList<MainReviewVO> data, RequestManager mGlideRequestManager) {
        this.context = context;
        this.data = data;
        this.mGlide = mGlideRequestManager;
    }

    @Override
    public int getCount() {
        return data.size(); // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }

    @Override
    public MainReviewVO getItem(int position) {
        //position %= 4; // 0~3의 데이터의 포지션 값으로 지정해야함.
        Log.e("position", String.valueOf(position));

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_tistory_review_more_item, null);

        AppCompatTextView tistory_title = v.findViewById(R.id.tistory_title);
        AppCompatTextView tistory_description = v.findViewById(R.id.tistory_description);
        AppCompatTextView tistory_write_date = v.findViewById(R.id.tistory_write_date);
        AppCompatTextView tistory_author = v.findViewById(R.id.tistory_author);
        AppCompatImageView tistory_image = v.findViewById(R.id.tistory_image);

        tistory_title.setText(data.get(position).getTitle());
        tistory_description.setText(data.get(position).getDescription());
        tistory_write_date.setText(data.get(position).getWrite_date());
        tistory_author.setText(data.get(position).getAuthor());

        ViewGroup.LayoutParams tistory_image_params = tistory_image.getLayoutParams();

        if(Util.isNullOrEmpty(data.get(position).getThumbnail_url())){
            tistory_image_params.width = 1;
            tistory_image.setLayoutParams(tistory_image_params);
        }else{
            mGlide.load(data.get(position).getThumbnail_url())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(tistory_image);
        }

        return v;
    }
}
