package com.dev.eatjeong.signUp;

import com.dev.eatjeong.common.CommonMapResponseVO;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SignUpRetrofitAPI {

    //일반유저 회원가입
    @POST("/v1/users/general/signup")
    Call<CommonMapResponseVO> signUser(@Query("email") String email, @Query("password") String password,@Query("gender") String gender,@Query("nickname") String nickname,@Query("birthday") String birthday,@Query("phone_number") String phone_number);

}