package com.dev.eatjeong.main.home.homeListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;

import java.util.ArrayList;

public class MainYoutubeListMoreAdapter extends BaseAdapter {
    private ArrayList<MainReviewVO> data;
    private Context context;
    private RequestManager mGlide;

    public MainYoutubeListMoreAdapter(Context context, ArrayList<MainReviewVO> data, RequestManager mGlideRequestManager) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.home_youtube_review_more_item, null);


        AppCompatTextView youtube_title = v.findViewById(R.id.youtube_title);
        AppCompatTextView youtube_write_date = v.findViewById(R.id.youtube_write_date);
        AppCompatImageView youtube_image = v.findViewById(R.id.youtube_image);

        youtube_title.setText(data.get(position).getTitle());
        youtube_write_date.setText(data.get(position).getWrite_date());
        mGlide.load(data.get(position).getThumbnail_url())
                .override(300, 200)
                .fitCenter()
                .into(youtube_image);

        return v;
    }
}
