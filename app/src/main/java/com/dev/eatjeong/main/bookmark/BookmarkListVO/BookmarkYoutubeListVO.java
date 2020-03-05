package com.dev.eatjeong.main.bookmark.BookmarkListVO;

public class BookmarkYoutubeListVO {
    private String place_name = "";
    private String place_id = "";
    private String category_name = "";
    private String place_address = "";
    private String road_place_address = "";
    private String latitude = "";
    private String longitude = "";
    private String google_rating = "";
    private String app_rating = "";
    private String thumbnail = "";
    private String naver_blog_count = "";
    private String daum_blog_count = "";
    private String google_review_count = "";
    private String youtube_review_count = "";
    private String app_review_count = "";

    public BookmarkYoutubeListVO(String place_name){
        this.place_name = place_name;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPlace_address() {
        return place_address;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }

    public String getRoad_place_address() {
        return road_place_address;
    }

    public void setRoad_place_address(String road_place_address) {
        this.road_place_address = road_place_address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGoogle_rating() {
        return google_rating;
    }

    public void setGoogle_rating(String google_rating) {
        this.google_rating = google_rating;
    }

    public String getApp_rating() {
        return app_rating;
    }

    public void setApp_rating(String app_rating) {
        this.app_rating = app_rating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNaver_blog_count() {
        return naver_blog_count;
    }

    public void setNaver_blog_count(String naver_blog_count) {
        this.naver_blog_count = naver_blog_count;
    }

    public String getDaum_blog_count() {
        return daum_blog_count;
    }

    public void setDaum_blog_count(String daum_blog_count) {
        this.daum_blog_count = daum_blog_count;
    }

    public String getGoogle_review_count() {
        return google_review_count;
    }

    public void setGoogle_review_count(String google_review_count) {
        this.google_review_count = google_review_count;
    }

    public String getYoutube_review_count() {
        return youtube_review_count;
    }

    public void setYoutube_review_count(String youtube_review_count) {
        this.youtube_review_count = youtube_review_count;
    }

    public String getApp_review_count() {
        return app_review_count;
    }

    public void setApp_review_count(String app_review_count) {
        this.app_review_count = app_review_count;
    }
}
