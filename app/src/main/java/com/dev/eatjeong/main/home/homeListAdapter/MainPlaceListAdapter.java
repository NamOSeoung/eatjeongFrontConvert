package com.dev.eatjeong.main.home.homeListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.eatjeong.R;
import com.dev.eatjeong.main.home.homeVO.MainPlaceVO;
import com.dev.eatjeong.main.home.homeVO.MainReviewVO;
import com.dev.eatjeong.main.search.searchListVO.PlaceListVO;

import java.util.ArrayList;
import java.util.List;


public class MainPlaceListAdapter extends RecyclerView.Adapter<MainPlaceListAdapter.Holder> {

    private Context context;
    private List<MainPlaceVO> list = new ArrayList<>();

    public MainPlaceListAdapter(Context context, List<MainPlaceVO> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_place_list_item, parent, false);
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
        holder.place_name.setText(list.get(itemposition).getPlace_name());

    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
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