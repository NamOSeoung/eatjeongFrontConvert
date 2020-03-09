package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SettingsListAdapter extends RecyclerView.Adapter<SettingsListAdapter.Holder> {

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    private Context context;
    private List<Map<String,String>> list = new ArrayList<Map<String,String>>();

    private String division;
    public SettingsListAdapter(Context context,
                               ArrayList<Map<String,String>> list,
                               String division) {
        this.context = context;
        this.list = list;
        this.division = division;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_black_list_item, parent, false);
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

        final int Position = position;

        if(division.equals("author")){
            holder.author.setVisibility(View.VISIBLE);
            holder.post.setVisibility(View.GONE);
            holder.author.setText(list.get(itemposition).get("author"));
        }else {
            holder.author.setVisibility(View.GONE);
            holder.post.setVisibility(View.VISIBLE);
            holder.post.setText(list.get(itemposition).get("title"));
        }

        //중략 ...................
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, Position);
                }
            }
        });

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView author,post;
        public Button delete;

        public Holder(View view){
            super(view);
            author = (TextView) view.findViewById(R.id.author);
            post = (TextView)view.findViewById(R.id.post);
            delete =(Button) view.findViewById(R.id.delete);
        }
    }
}