package com.dev.eatjeong.main.settings;

import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsBlackListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewListResponseVO;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SettingsRetrofitAPI {

    //유저 정보 조회
    @GET("/v1/users/user")
    Call<CommonMapResponseVO> getUserInfo(@Query("user_id") String user_id, @Query("sns_division") String sns_division);

    //유저 닉네임 변경
    @PUT("/v1/users/general/nickname")
    Call<CommonMapResponseVO> updateNickName(@Query("user_id") String user_id, @Query("nickname") String nickname);

    //유저 휴대폰 번호 변경
    @PUT("/v1/users/general/phone")
    Call<CommonMapResponseVO> updatePhoneNumber(@Query("user_id") String user_id, @Query("phone_number") String phone_number);

    //유저 비밀번호 변경
    @PUT("/v1/users/general/password")
    Call<CommonMapResponseVO> updatePassword(@Query("email") String email, @Query("sns_division") String sns_division, @Query("password") String password);

    //유저 회원 탈퇴
    @PUT("/v1/users/general/accountclose")
    Call<CommonMapResponseVO> setAccountClose(@Query("user_id") String user_id, @Query("password") String password);

    //리뷰별 블랙리스트 조회
    @GET("/v1/blacklist")
    Call<SettingsBlackListResponseVO> getBlackList(@Query("user_id") String user_id, @Query("sns_division") String sns_division);

    //내 블랙리스트 관리 블랙리스트 삭제
    @DELETE("/v1/reviews/blacklist")
    Call<SettingsBlackListResponseVO> deleteBlackList(
            @Query("user_id") String user_id,
            @Query("sns_division") String sns_division,
            @Query("portal") String portal,
            @Query("author")String author,
            @Query("blacklist_division")String blacklist_division,
            @Query("review_id")String review_id);

    //내가 작성한 리뷰 조회
    @GET("/v1/myreviews")
    Call<SettingsMyReviewListResponseVO> getMyReviews(@Query("user_id") String user_id, @Query("sns_division") String sns_division);

}