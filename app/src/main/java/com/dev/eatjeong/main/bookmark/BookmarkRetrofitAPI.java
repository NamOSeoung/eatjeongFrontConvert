package com.dev.eatjeong.main.bookmark;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookmarkRetrofitAPI {


    //장소 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkPlaceResponseVO> getBookmarkPlace(@Query("gubun")String gubun,@Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //유튜브 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkYoutubeResponseVO> getBookmarkYoutube(@Query("gubun")String gubun,@Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //네이버 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkNaverResponseVO> getBookmarkNaver(@Query("gubun")String gubun,@Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //티스토리 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkTistoryResponseVO> getBookmarkTistory(@Query("gubun")String gubun,@Query("user_id")String user_id, @Query("sns_division")String sns_division);

}