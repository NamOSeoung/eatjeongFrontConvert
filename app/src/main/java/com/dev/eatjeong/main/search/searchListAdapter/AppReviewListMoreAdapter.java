package com.dev.eatjeong.main.search.searchListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.searchListVO.AppReviewVO;
import com.dev.eatjeong.main.search.searchListVO.NaverReviewVO;

import java.util.ArrayList;

public class AppReviewListMoreAdapter extends BaseAdapter {
    private ArrayList<AppReviewVO> data;
    private Context context;
    public AppReviewListMoreAdapter(Context context, ArrayList<AppReviewVO> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size(); // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }
    @Override
    public AppReviewVO getItem(int position) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.search_app_item, null);


            TextView review_contents = v.findViewById(R.id.review_contents);

        review_contents.setText(data.get(position).getReview_contents());
        return v;
    }
}