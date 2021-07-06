package com.dev.eatjeong.main.search;

import com.dev.eatjeong.common.CommonMapListResponseVO;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAppListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchAreaListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchGoogleListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchNaverListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchPlaceListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchTistoryListResponseVO;
import com.dev.eatjeong.main.search.searchRetrofitVO.SearchYoutubeListResponseVO;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    //지역 자동 완성을 위한 검색어 db검색
    @GET("/v1/area/suggest")
    Call<CommonMapListResponseVO> getAllArea();

    //매장 상세 정보 검색
    @GET("/v1/places/{place_id}")
    Call<CommonMapResponseVO> getPlaceInfo(@Path("place_id")String place_id, @Query("user_id")String user_id, @Query("sns_division")String sns_division, @Query("latitude") String latitude, @Query("longitude")String longitude);

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


    /* 북마크 삭제부분 */

    //유튜브 북마크 삭제
    @DELETE("/v1/bookmarks")
    Call<CommonMapResponseVO> deleteBookmarkYoutube(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //네이버 북마크 삭제
    @DELETE("/v1/bookmarks")
    Call<CommonMapResponseVO> deleteBookmarkNaver(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //티스토리 북마크 삭제
    @DELETE("/v1/bookmarks")
    Call<CommonMapResponseVO> deleteBookmarkTistory(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);


    /* 북마크 삽입부분 */

    //유튜브 북마크 추가
    @POST("/v1/bookmarks")
    Call<CommonMapResponseVO> setBookmarkYoutube(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //네이버 북마크 추가
    @POST("/v1/bookmarks")
    Call<CommonMapResponseVO> setBookmarkNaver(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);

    //티스토리 북마크 추가
    @POST("/v1/bookmarks")
    Call<CommonMapResponseVO> setBookmarkTistory(@Query("gubun")String gubun, @Query("place_id")String place_id, @Query("id")String id, @Query("user_id")String user_id, @Query("sns_division")String sns_division);


    /* 블랙리스트 추가/삭제 부분 */

    //유튜브/네이버/티스토리 (게시자/게시물) 공용 블랙리스트 추가
    @POST("/v1/reviews/{review_id}/blacklist")
    Call<CommonMapResponseVO> setBlackList(@Path("review_id") String review_id,@Query("place_id")String place_id, @Query("user_id")String user_id, @Query("sns_division")String sns_division, @Query("portal")String portal, @Query("author")String author,@Query("blacklist_division")String blacklist_division);

    //유튜브/네이버/티스토리 (게시자/게시물) 공용 블랙리스트 삭제
    @DELETE("/v1/reviews/blacklist")
    Call<CommonMapResponseVO> deleteBlackList(@Query("place_id")String place_id,@Query("user_id") String user_id, @Query("sns_division")String sns_division, @Query("portal")String portal, @Query("author")String author, @Query("blacklist_division")String blacklist_division,@Query("review_id")String review_id);


    /* 잇정 리뷰 부분 */
    //잇정 리뷰 추가
    @Multipart
    @POST("/v1/places/{place_id}/reviews")

    Call<CommonMapResponseVO> addAppReview(@Path("place_id")String place_id,@Query("review_user_id")String review_user_id, @Query("sns_division")String sns_division, @Query("review_contents")String review_contents, @Query("rating_point")String rating_point, @Part List<MultipartBody.Part> fileList);

//    Call<CommonMapResponseVO> addAppReview(@Path("place_id") String place_id,@Query("review_user_id")String review_user_id, @Query("sns_division")String sns_division, @Query("review_contents")String review_contents, @Query("rating_point")String rating_point, @Query("file")String file);

}