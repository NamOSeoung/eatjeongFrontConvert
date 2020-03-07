package com.dev.eatjeong.main.home;

import com.dev.eatjeong.main.home.homeRetrofitVO.MainPlaceListResponseVO;
import com.dev.eatjeong.main.home.homeRetrofitVO.MainReviewListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAppListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchGoogleListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchNaverListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchNaverMapResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceInfoMapResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchTistoryListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchTistoryMapResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchYoutubeListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchYoutubeMapResponseVO;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeRetrofitAPI {

    //메인화면 장소 검색
    @GET("/v1/main/places")
    Call<MainPlaceListResponseVO> getMainPlace(@Query("query")String query, @Query("size")String size);

    //메인화면 리뷰 검색 -- 유튜브, 네이버, 티스토리 공통사용
    @GET("/v1/main/reviews")
    Call<MainReviewListResponseVO> getMainReviews(@Query("query")String query, @Query("portal")String portal, @Query("size")String size);


}