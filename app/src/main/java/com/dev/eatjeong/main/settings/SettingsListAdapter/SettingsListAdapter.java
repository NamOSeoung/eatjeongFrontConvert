package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dev.eatjeong.R;

import java.util.ArrayList;
import java.util.Map;

public class SettingsListAdapter extends BaseAdapter {
    private ArrayList<Map<String,String>> data;
    private Context context;
    private String division;
    public SettingsListAdapter(Context context, ArrayList<Map<String,String>> data,String division){
        this.context = context;
        this.data = data;
        this.division = division;
    }

    @Override
    public int getCount() {
        return data.size(); // 실제 데이터는 4개지만, 리스트 뷰가 100개인 것처럼 보임.
    }
    @Override
    public Map<String,String> getItem(int position) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.settings_black_list_item, null);

            TextView author = v.findViewById(R.id.author);
            TextView post = v.findViewById(R.id.post);
            Button delete = v.findViewById(R.id.delete);

            if(division.equals("author")){
                author.setVisibility(View.VISIBLE);
                post.setVisibility(View.GONE);
                author.setText(data.get(position).get("author"));
            }else {
                author.setVisibility(View.GONE);
                post.setVisibility(View.VISIBLE);
                post.setText(data.get(position).get("title"));
            }

        return v;
    }
}