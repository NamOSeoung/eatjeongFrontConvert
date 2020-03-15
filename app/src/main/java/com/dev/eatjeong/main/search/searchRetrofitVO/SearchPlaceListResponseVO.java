package com.dev.eatjeong.main.search.searchRetrofitVO;

import com.dev.eatjeong.main.home.homeRetrofitVO.MainPlaceListResponseVO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchPlaceListResponseVO {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("dataList")

    public List<MainPlaceListResponseVO.DataList> mDatalist;


    public class DataList {


        @SerializedName("place_id")

        private String place_id = "";

        @SerializedName("place_name")

        private String place_name = "";

        @SerializedName("open_hours")

        private String open_hours = "";

        @SerializedName("category_name")

        private String category_name = "";

        @SerializedName("latitude")

        private String latitude = "";

        @SerializedName("longitude")

        private String longitude = "";

        @SerializedName("appreview_rating")

        private String appreview_rating = "";

        @SerializedName("place_address")

        private String place_address = "";

        @SerializedName("read_place_address")

        private String read_place_address = "";

        @SerializedName("blog_thumbnail")

        private String blog_thumbnail = "";

        @SerializedName("app_thumbnail")

        private String app_thumbnail = "";

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

        public String getRead_place_address() {
            return read_place_address;
        }

        public void setRead_place_address(String read_place_address) {
            this.read_place_address = read_place_address;
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


}

