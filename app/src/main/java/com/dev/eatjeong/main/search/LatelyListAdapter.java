package com.dev.eatjeong.main.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.User;

import java.util.ArrayList;

public class LatelyListAdapter extends BaseAdapter {
    private ArrayList<User> data;
    private Context context;
    public LatelyListAdapter(Context context, ArrayList<User> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 4; // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }
    @Override
    public User getItem(int position) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_user, null);


            TextView name = v.findViewById(R.id.name_tv);
            TextView email = v.findViewById(R.id.email_tv);
            TextView content = v.findViewById(R.id.content_tv);

            name.setText(data.get(position).getName());
            email.setText(data.get(position).getEmail());
            content.setText(data.get(position).getContent());
        //TextView place_name = v.findViewById(R.id.place_name);

//        place_name.setText(data.get(0).getPlace_name());
        return v;
    }
}