package com.dev.eatjeong.main.search.searchListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.search.searchListVO.PlaceListVO;
import com.dev.eatjeong.main.search.searchListVO.PopularVO;

import java.util.ArrayList;

public class PlaceListAdapter extends BaseAdapter {
    private ArrayList<PlaceListVO> data;
    private Context context;
    public PlaceListAdapter(Context context, ArrayList<PlaceListVO> data){
        this.context = context;
        this.data = data;
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


        TextView place_name = v.findViewById(R.id.place_name);

        place_name.setText(data.get(position).getPlace_name());
        //TextView place_name = v.findViewById(R.id.place_name);

//        place_name.setText(data.get(0).getPlace_name());
        return v;
    }
}