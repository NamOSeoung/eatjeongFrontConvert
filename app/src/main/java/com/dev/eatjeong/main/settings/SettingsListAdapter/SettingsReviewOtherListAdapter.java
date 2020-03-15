package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewDetailListResponseVO;

import java.util.ArrayList;
import java.util.List;


public class SettingsReviewOtherListAdapter extends RecyclerView.Adapter<SettingsReviewOtherListAdapter.Holder> {

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
    private List<SettingsMyReviewDetailListResponseVO.DataList.otherReviewList> review_list = new ArrayList<SettingsMyReviewDetailListResponseVO.DataList.otherReviewList>();

    public SettingsReviewOtherListAdapter(Context context,
                                          ArrayList<SettingsMyReviewDetailListResponseVO.DataList.otherReviewList> review_list) {
        this.context = context;
        this.review_list = review_list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_review_list_detail_item, parent, false);
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
        holder.review_user_id.setText(review_list.get(itemposition).getReview_user_id());

        holder.review_delete.setVisibility(View.GONE);


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
    public int getItemCount()
    {
        return review_list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView review_user_id;
        public Button review_delete;

        public Holder(View view){
            super(view);
            review_user_id = (TextView) view.findViewById(R.id.review_user_id);
            review_delete = view.findViewById(R.id.review_delete);
        }
    }
}