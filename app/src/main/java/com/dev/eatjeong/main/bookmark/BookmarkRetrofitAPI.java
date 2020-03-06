package com.dev.eatjeong.main.bookmark;

import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkNaverMapResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkNaverResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkPlaceResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkTistoryMapResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkTistoryResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkYoutubeMapResponseVO;
import com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO.BookmarkYoutubeResponseVO;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookmarkRetrofitAPI {


    //북마크 조회부분

    //장소 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkPlaceResponseVO> getBookmarkPlace(@Query("gubun")String gubun, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //유튜브 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkYoutubeResponseVO> getBookmarkYoutube(@Query("gubun")String gubun, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //네이버 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkNaverResponseVO> getBookmarkNaver(@Query("gubun")String gubun, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //티스토리 북마크 조회
    @GET("/v1/bookmarks")
    Call<BookmarkTistoryResponseVO> getBookmarkTistory(@Query("gubun")String gubun, @Query("user_id")String user_id, @Query("sns_division")String sns_division);


    //북마크 삭제부분

    //유튜브 북마크 삭제
    @DELETE("/v1/bookmarks")
    Call<BookmarkYoutubeMapResponseVO> deleteBookmarkYoutube(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //네이버 북마크 삭제
    @DELETE("/v1/bookmarks")
    Call<BookmarkNaverMapResponseVO> deleteBookmarkNaver(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //티스토리 북마크 삭제
    @DELETE("/v1/bookmarks")
    Call<BookmarkTistoryMapResponseVO> deleteBookmarkTistory(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);



    //북마크 삽입부분

    //유튜브 북마크 추가
    @POST("/v1/bookmarks")
    Call<BookmarkYoutubeMapResponseVO> setBookmarkYoutube(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);


    //네아버 북마크 추가
    @POST("/v1/bookmarks")
    Call<BookmarkNaverMapResponseVO> setBookmarkNaver(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //티스토리 북마크 추가
    @POST("/v1/bookmarks")
    Call<BookmarkTistoryMapResponseVO> setBookmarkTistory(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

}