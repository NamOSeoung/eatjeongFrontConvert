package com.dev.eatjeong.main.search;

import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAppListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchGoogleListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchNaverListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceInfoMapResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchTistoryListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchYoutubeListResponseVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchRetrofitAPI {

    //인기검색어 조회
    @GET("/v1/popularsearches")
    Call<SearchResponseVO> getPopularSearches();

    //장소 리스트 조회 -- 로그인 했을 경우
    @GET("/v1/places")
    Call<SearchPlaceListResponseVO> getPlaceList(@Query("q") String q, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //장소 리스트 조회 -- 둘러보기 사용자일 경우
    @GET("/v1/places")
    Call<SearchPlaceListResponseVO> getPlaceList(@Query("q") String q);

    //대분류 지역 검섹
    @GET("/v1/area/first")
    Call<SearchAreaListResponseVO> getMainAreaList();

    //중분류 지역 검섹
    @GET("/v1/area/second")
    Call<SearchAreaListResponseVO> getSubAreaList(@Query("area") String area);

    //매장 상세 정보 검색
    @GET("/v1/places/{place_id}")
    Call<SearchPlaceInfoMapResponseVO> getPlaceInfo(@Path("place_id")String place_id, @Query("latitude") String latitude,@Query("longitude")String longitude);

    //매장상세 유튜브 리뷰 검색
    @GET("/v1/places/{place_id}/reviews/youtube")
    Call<SearchYoutubeListResponseVO> getYoutubeReview(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query, @Query("size") String size);

    //매장상세 네이버 블로그 리뷰 검색
    @GET("/v1/places/{place_id}/blogs/naver")
    Call<SearchNaverListResponseVO> getNaverReview(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query, @Query("size") String size);

    //매장상세 다음 블로그 리뷰 검색
    @GET("/v1/places/{place_id}/blogs/daum")
    Call<SearchTistoryListResponseVO> getDaumReview(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query, @Query("size") String size);


    //매장상세 구글 리뷰 검색
    @GET("/v1/places/{place_id}/reviews/google")
    Call<SearchGoogleListResponseVO> getGoogleReview(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query);

    //매장상세 잇정 리뷰 검색
    @GET("/v1/places/{place_id}/reviews/eatzeong")
    Call<SearchAppListResponseVO> getAppReview(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("size")String size);


    //매장상세 유튜브 리뷰 더보기 검색
    @GET("/v1/places/{place_id}/reviews/youtube")
    Call<SearchYoutubeListResponseVO> getYoutubeReviewMore(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query);

    //매장상세 네이버 블로그 더보기 리뷰 검색
    @GET("/v1/places/{place_id}/blogs/naver")
    Call<SearchNaverListResponseVO> getNaverReviewMore(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query);

    //매장상세 다음 블로그 리뷰 더보기 검색
    @GET("/v1/places/{place_id}/blogs/daum")
    Call<SearchTistoryListResponseVO> getDaumReviewMore(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("query")String query );

    //매장상세 잇정 리뷰 더보기 검색
    @GET("/v1/places/{place_id}/reviews/eatzeong")
    Call<SearchAppListResponseVO> getAppReviewMore(@Path("place_id")String place_id, @Query("user_id") String user_id, @Query("sns_division")String sns_division);


}