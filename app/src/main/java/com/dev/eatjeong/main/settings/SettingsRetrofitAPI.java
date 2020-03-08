package com.dev.eatjeong.main.settings;

import com.dev.eatjeong.main.home.homeRetrofitVO.MainPlaceListResponseVO;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainReviewListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsUserInfoMapResponseVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SettingsRetrofitAPI {

    //유저 정보 조회
    @GET("/v1/users/user")
    Call<SettingsUserInfoMapResponseVO> getUserInfo(@Query("user_id") String user_id, @Query("sns_division") String sns_division);

    //유저 닉네임 변경
    @PUT("/v1/users/general/nickname")
    Call<SettingsUserInfoMapResponseVO> updateNickName(@Query("user_id") String user_id, @Query("nickname") String nickname);

    //유저 휴대폰 번호 변경
    @PUT("/v1/users/general/phone")
    Call<SettingsUserInfoMapResponseVO> updatePhoneNumber(@Query("user_id") String user_id, @Query("phone_number") String phone_number);

    //유저 비밀번호 변경
    @PUT("/v1/users/general/password")
    Call<SettingsUserInfoMapResponseVO> updatePassword(@Query("email") String email, @Query("sns_division") String sns_division, @Query("password") String password);

    //유저 회원 탈퇴
    @PUT("/v1/users/general/accountclose")
    Call<SettingsUserInfoMapResponseVO> setAccountClose(@Query("user_id") String user_id, @Query("password") String password);



}