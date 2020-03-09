package com.dev.eatjeong.main.home;

import com.dev.eatjeong.main.home.homeRetrofitVO.MainPlaceListResponseVO;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainReviewListResponseVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeRetrofitAPI {

    //메인화면 장소 검색
    @GET("/v1/main/places")
    Call<MainPlaceListResponseVO> getMainPlace(@Query("query")String query, @Query("size")String size);

    //메인화면 리뷰 검색 -- 유튜브, 네이버, 티스토리 공통사용
    @GET("/v1/main/reviews")
    Call<MainReviewListResponseVO> getMainReviews(@Query("query")String query, @Query("portal")String portal, @Query("size")String size);

    //메인화면 장소 더보기 검색 (no Size)
    @GET("/v1/main/places")
    Call<MainPlaceListResponseVO> getMainPlaceMore(@Query("query")String query);

    //메인화면 더보기 검색 -- 유튜브, 네이버, 티스토리 공통사용 (no Size)
    @GET("/v1/main/reviews")
    Call<MainReviewListResponseVO> getMainReviewsMore(@Query("query")String query, @Query("portal")String portal);


}