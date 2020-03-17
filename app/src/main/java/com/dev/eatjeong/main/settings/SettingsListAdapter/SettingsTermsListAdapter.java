package com.dev.eatjeong.main.settings.SettingsListAdapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.eatjeong.R;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsNoticeListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsTermsListResponseVO;

import java.util.ArrayList;
import java.util.List;


public class SettingsTermsListAdapter extends RecyclerView.Adapter<SettingsTermsListAdapter.Holder> {
    public AppCompatTextView write_date,review_user_id, review_user_nickname, review_contents, rating_point, like_count, like_flag, image_url,review_update, review_delete, like_delete, like_add;
    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    private RequestManager requestManager;

    public interface ItemClick {
        public void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void responseItemStatus(int controll_flag, int view_id) {

    }

    private Context context;
    private List<SettingsTermsListResponseVO.DataList> list = new ArrayList<SettingsTermsListResponseVO.DataList>();

    public SettingsTermsListAdapter(Context context,
                                    List<SettingsTermsListResponseVO.DataList> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.terms_list_item, parent, false);
        Holder holder = new Holder(view);

        requestManager = Glide.with(context);
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
        Handler handler = new Handler();  // 외부쓰레드 에서 메인 UI화면을 그릴때 사용

        final int Position = position;
        holder.terms_subject.setText(list.get(itemposition).getTerms_subject());
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder {


        public AppCompatTextView terms_subject;


        public Holder(View view) {
            super(view);

            terms_subject = view.findViewById(R.id.terms_subject);
        }
    }
}