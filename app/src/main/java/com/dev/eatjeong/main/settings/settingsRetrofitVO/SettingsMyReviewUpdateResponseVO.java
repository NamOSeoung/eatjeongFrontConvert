package com.dev.eatjeong.main.settings.settingsRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingsMyReviewUpdateResponseVO {

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

    public List<DataList> mDatalist;


    public class DataList {


        @SerializedName("place_id")

        private String place_id = "";

        @SerializedName("place_name")

        private String place_name = "";

        @SerializedName("review_id")

        private String review_id = "";

        @SerializedName("review_user_id")

        private String review_user_id = "";

        @SerializedName("rating_avg")

        private String rating_avg = "";

        @SerializedName("rating")

        private String rating = "";

        @SerializedName("like_count")

        private String like_count = "";

        @SerializedName("like_flag")

        private String like_flag = "";

        @SerializedName("category")

        private String category = "";

        @SerializedName("category_name")

        private String category_name = "";

        @SerializedName("write_date")

        private String write_date = "";


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

        public String getReview_id() {
            return review_id;
        }

        public void setReview_id(String review_id) {
            this.review_id = review_id;
        }

        public String getReview_user_id() {
            return review_user_id;
        }

        public void setReview_user_id(String review_user_id) {
            this.review_user_id = review_user_id;
        }

        public String getRating_avg() {
            return rating_avg;
        }

        public void setRating_avg(String rating_avg) {
            this.rating_avg = rating_avg;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getLike_flag() {
            return like_flag;
        }

        public void setLike_flag(String like_flag) {
            this.like_flag = like_flag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getWrite_date() {
            return write_date;
        }

        public void setWrite_date(String write_date) {
            this.write_date = write_date;
        }
    }

}

