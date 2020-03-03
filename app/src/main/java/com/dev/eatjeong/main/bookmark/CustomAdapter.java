package com.dev.eatjeong.main.bookmark;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.model.BookmarkDataModel;

import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    private ArrayList<BookmarkDataModel> data;
    private Context context;
    public CustomAdapter(Context context, ArrayList<BookmarkDataModel> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return 3; // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }
    @Override
    public BookmarkDataModel getItem(int position) {
        position %= 4; // 0~3의 데이터의 포지션 값으로 지정해야함.
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        position %= 4; // 0~3의 데이터의 포지션 값으로 지정해야함.
        View v = LayoutInflater.from(context).inflate(R.layout.custom_adapter, null);
        TextView place_name = v.findViewById(R.id.place_name);

        Log.e("position : " , String.valueOf(position));
        Log.e("chack : " , data.get(0).getPlace_name());
//        place_name.setText(data.get(0).getPlace_name());
        return v;
    }
}