package com.dev.eatjeong.login;

import com.dev.eatjeong.main.search.SearchResponseVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginRetrofitAPI {


    //일반사용자 로그인
    @GET("/v1/users/general/signin")
    Call<LoginResponseVO> getGeneralUserCheck(@Query("user_id") String user_id,@Query("password") String password);

}