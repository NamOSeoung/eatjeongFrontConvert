package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SettingsCategoryListAdapter extends RecyclerView.Adapter<SettingsCategoryListAdapter.Holder> {
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    private int selectedPosition = -1;
    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    private Context context;
    private List<String> name_list = new ArrayList<String>();
    private List<String> code_list = new ArrayList<String>();

    public SettingsCategoryListAdapter(Context context,
                                       List<String> code_list,List<String> name_list) {
        this.context = context;
        this.name_list = name_list;
        this.code_list = code_list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_category_list_item, parent, false);
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
        holder.category_name.setText(name_list.get(itemposition));
        if(selectedPosition == position){
            holder.category_name.setBackground(ContextCompat.getDrawable(context,R.drawable.common_bottom_border));
        }else {
            holder.category_name.setBackground(ContextCompat.getDrawable(context,R.drawable.common_white_background));
        }

        holder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return name_list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public AppCompatTextView category_name;
        public RecyclerView cv_item_movie_parent;

        public Holder(View view){
            super(view);
            category_name = view.findViewById(R.id.category_name);
        }
    }
}