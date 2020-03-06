package com.dev.eatjeong.main.search;

import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;

import retrofit2.Call;
import retrofit2.http.GET;
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


}