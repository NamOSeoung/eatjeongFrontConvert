package com.dev.eatjeong.main.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.search.searchFragment.LatelyFragment;
import com.dev.eatjeong.main.search.searchFragment.PopularFragment;
import com.dev.eatjeong.main.settings.settingsActivity.BlackListActivity;
import com.dev.eatjeong.main.settings.settingsActivity.MyAppReviewListActivity;
import com.dev.eatjeong.main.settings.settingsActivity.UserInfoManagementActivity;
import com.dev.eatjeong.mainWrap.MainWrapActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsTab extends Fragment {

    String user_id;
    String sns_division;
    Button settings_logout;
    Button settings_login;

    private Retrofit mRetrofit;

    private SettingsRetrofitAPI mSettingsRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    TextView nick_name;

    private PopularFragment popularFragment = new PopularFragment();
    private LatelyFragment latelyFragment = new LatelyFragment();

    private Button my_info,my_black_list,my_review;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v;

        user_id = ((MainWrapActivity)getActivity()).getUserInfo().get("user_id");
        sns_division = ((MainWrapActivity)getActivity()).getUserInfo().get("sns_division");


        if(user_id != null){
            v = inflater.inflate(R.layout.settings_tab, container, false);

            settings_logout = v.findViewById(R.id.settings_logout);

            nick_name = (TextView)v.findViewById(R.id.nick_name) ;
            my_info = (Button)v.findViewById(R.id.my_info);
            my_black_list = (Button)v.findViewById(R.id.my_black_list);
            my_review = (Button)v.findViewById(R.id.my_review);

            //로그인 됬을 경우만 회원 정보 가지고옴
            setRetrofitInit();

            callPlaceInfoResponse();

            settings_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainWrapActivity)getActivity()).appLogout();
                }
            });

            my_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent userInfoManagement = new Intent(getContext(), UserInfoManagementActivity.class);
                    userInfoManagement.putExtra("user_id",user_id);
                    userInfoManagement.putExtra("sns_division",sns_division);

                    startActivityForResult(userInfoManagement,0);//액티비티 띄우기
                    getActivity().overridePendingTransition(R.anim.fadein,0);
                }
            });

            my_black_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent blackList = new Intent(getContext(), BlackListActivity.class);
                    blackList.putExtra("user_id",user_id);
                    blackList.putExtra("sns_division",sns_division);

                    startActivityForResult(blackList,0);//액티비티 띄우기
                    getActivity().overridePendingTransition(R.anim.fadein,0);
                }
            });

            my_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myAppList = new Intent(getContext(), MyAppReviewListActivity.class);
                    myAppList.putExtra("user_id",user_id);
                    myAppList.putExtra("sns_division",sns_division);

                    startActivityForResult(myAppList,0);//액티비티 띄우기
                    getActivity().overridePendingTransition(R.anim.fadein,0);
                }
            });
        }else {
            v = inflater.inflate(R.layout.settings_logout_tab, container, false);

            settings_login = v.findViewById(R.id.settings_login);

            settings_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainWrapActivity)getActivity()).backLoginPage();
                }
            });

        }




        return v;
    }

    private void setRetrofitInit() {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

    }

    private void callPlaceInfoResponse() {
        mCallCommonMapResponseVO = mSettingsRetrofitAPI.getUserInfo(user_id,sns_division);
        mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
        @Override
        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());
            Log.e("dd", response.body().getDataList().get("nickname"));

            if(response.body().getCode().equals("200")){
                nick_name.setText(response.body().getDataList().get("nickname"));
            }

        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {

                setRetrofitInit();

                callPlaceInfoResponse();
                Log.e("LOG", "결과 받기 성공");
            }

        }
    }
}