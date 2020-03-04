package com.dev.eatjeong.main.search;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SearchRetrofitAPI {


    //검색 탭 인기검색어 조회
    @GET("/v1/popularsearches")
    Call<SearchResponseVO> getPopularSearches();

}