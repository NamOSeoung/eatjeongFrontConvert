package com.dev.eatjeong.main.home.homeVO;


public class MainPlaceVO {


    private String place_id;
    private String place_name;
    private String open_hours;
    private String category_name;
    private String latitude;
    private String longitude;
    private String google_rating;
    private String appreview_rating;
    private String place_address;
    private String road_place_address;
    private String blog_thumbnail;
    private String app_thumbnail;

    public MainPlaceVO(String place_id, String place_name, String place_address,String latitude,String longitude, String blog_thumbnail, String app_thumbnail, String category_name, String google_rating, String appreview_rating){
        this.place_id = place_id;
        this.place_name = place_name;
        this.place_address = place_address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.app_thumbnail = app_thumbnail;
        this.category_name = category_name;
        this.blog_thumbnail = blog_thumbnail;
        this.google_rating = google_rating;
        this.appreview_rating = appreview_rating;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(String open_hours) {
        this.open_hours = open_hours;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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

    public String getAppreview_rating() {
        return appreview_rating;
    }

    public void setAppreview_rating(String appreview_rating) {
        this.appreview_rating = appreview_rating;
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

    public String getBlog_thumbnail() {
        return blog_thumbnail;
    }

    public void setBlog_thumbnail(String blog_thumbnail) {
        this.blog_thumbnail = blog_thumbnail;
    }

    public String getApp_thumbnail() {
        return app_thumbnail;
    }

    public void setApp_thumbnail(String app_thumbnail) {
        this.app_thumbnail = app_thumbnail;
    }
}
