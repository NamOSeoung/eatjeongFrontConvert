package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewListResponseVO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SettingsReviewListAdapter extends RecyclerView.Adapter<SettingsReviewListAdapter.Holder> {

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
    private String category_code;
    private int item_count;
    private List<SettingsMyReviewListResponseVO.DataList> review_list = new ArrayList<SettingsMyReviewListResponseVO.DataList>();

    public SettingsReviewListAdapter(Context context,
                                     ArrayList<SettingsMyReviewListResponseVO.DataList> review_list,String category_code,int item_count) {
        this.category_code = category_code;
        this.context = context;
        this.review_list = review_list;
        this.item_count = item_count;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_my_review_list_item, parent, false);
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
        holder.place_name.setText(review_list.get(itemposition).getPlace_name());




//        //중략 ...................
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(itemClick != null){
//                    itemClick.onClick(v, Position);
//                }
//            }
//        });

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return item_count; // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView place_name;

        public Holder(View view){
            super(view);
            place_name = (TextView) view.findViewById(R.id.place_name);
        }
    }
}