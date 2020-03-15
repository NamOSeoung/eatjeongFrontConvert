package com.dev.eatjeong.main.settings;

import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsBlackListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewDetailListResponseVO;
import com.dev.eatjeong.main.settings.settingsRetrofitVO.SettingsMyReviewListResponseVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    //내가 작성한 리뷰에 대한 리뷰 상세 조회
    @GET("/v1/myreviews/{place_id}/{review_id}/")
    Call<SettingsMyReviewDetailListResponseVO> getMyReviewsDetail(@Path("place_id")String place_id, @Path("review_id")String review_id, @Query("user_id") String user_id, @Query("sns_division") String sns_division);

    //리뷰 좋아요 추가
    @POST("/v1/reviews/{review_id}")
    Call<CommonMapResponseVO> setLikeFlag(@Path("review_id")String review_id, @Query("user_id") String user_id, @Query("sns_division") String sns_division);

    //리뷰 좋아요 삭제
    @DELETE("/v1/reviews/{review_id}")
    Call<CommonMapResponseVO> deleteLikeFlag(@Path("review_id")String review_id, @Query("user_id") String user_id, @Query("sns_division") String sns_division);

    //내 리뷰 삭제
    @DELETE("/v1/places/{place_id}/reviews/{review_id}")
    Call<CommonMapResponseVO> deleteReview(@Path("place_id")String place_id, @Path("review_id")String review_id, @Query("review_user_id") String review_user_id, @Query("sns_division") String sns_division);


    //내 리뷰 업데이트 (파일 있을 경우 )
    @PUT("/v1/places/{place_id}/reviews/{review_id}")
    @Multipart
    Call<Map<String,String>> updateReview(@Path("place_id")String place_id, @Path("review_id")String review_id, @Query("review_user_id") String review_user_id, @Query("sns_division") String sns_division, @Query("rating_point")String rating_point, @Query("review_contents")String review_contents, @Query("image_url") ArrayList<String> image_url,@Part List<MultipartBody.Part> fileList);

    //내 리뷰 업데이트 (파일 없을 경우 )
    @Headers("Content-Type: application/json")
    @PUT("/v1/places/{place_id}/reviews/{review_id}")
    Call<Map<String,String>> updateReview(@Path("place_id")String place_id, @Path("review_id")String review_id, @Query("review_user_id") String review_user_id, @Query("sns_division") String sns_division, @Query("rating_point")String rating_point, @Query("review_contents")String review_contents, @Query("image_url") ArrayList<String> image_url);


}