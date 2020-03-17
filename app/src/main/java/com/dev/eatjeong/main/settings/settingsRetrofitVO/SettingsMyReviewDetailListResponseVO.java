package com.dev.eatjeong.main.settings.settingsRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SettingsMyReviewDetailListResponseVO {

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

    public DataList mDatalist;


    public class DataList {

        @SerializedName("myReviewList")
        public List<myReviewList> myReviewList;

        @SerializedName("otherReviewList")
        public List<otherReviewList> otherReviewList;


        public class myReviewList {


            @SerializedName("place_id")

            private String place_id = "";

            @SerializedName("review_id")

            private String review_id = "";

            @SerializedName("review_user_id")

            private String review_user_id = "";

            @SerializedName("sns_division")

            private String sns_division = "";

            @SerializedName("review_user_nickname")

            private String review_user_nickname = "";

            @SerializedName("profile_image_url")

            private String profile_image_url = "";

            @SerializedName("like_count")

            private String like_count = "";

            @SerializedName("like_flag")

            private String like_flag = "";

            @SerializedName("review_contents")

            private String review_contents = "";

            @SerializedName("rating_point")

            private String rating_point = "";

            @SerializedName("write_date")

            private String write_date = "";

            @SerializedName("image_url")

            private ArrayList<String> image_url;


            public String getPlace_id() {
                return place_id;
            }

            public void setPlace_id(String place_id) {
                this.place_id = place_id;
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

            public String getSns_division() {
                return sns_division;
            }

            public void setSns_division(String sns_division) {
                this.sns_division = sns_division;
            }

            public String getReview_user_nickname() {
                return review_user_nickname;
            }

            public void setReview_user_nickname(String review_user_nickname) {
                this.review_user_nickname = review_user_nickname;
            }

            public String getProfile_image_url() {
                return profile_image_url;
            }

            public void setProfile_image_url(String profile_image_url) {
                this.profile_image_url = profile_image_url;
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

            public String getReview_contents() {
                return review_contents;
            }

            public void setReview_contents(String review_contents) {
                this.review_contents = review_contents;
            }

            public String getRating_point() {
                return rating_point;
            }

            public void setRating_point(String rating_point) {
                this.rating_point = rating_point;
            }

            public String getWrite_date() {
                return write_date;
            }

            public void setWrite_date(String write_date) {
                this.write_date = write_date;
            }

            public ArrayList<String> getImage_url() {
                return image_url;
            }

            public void setImage_url(ArrayList<String> image_url) {
                this.image_url = image_url;
            }
        }

        public class otherReviewList {


            @SerializedName("place_id")

            private String place_id = "";

            @SerializedName("review_id")

            private String review_id = "";

            @SerializedName("review_user_id")

            private String review_user_id = "";

            @SerializedName("sns_division")

            private String sns_division = "";

            @SerializedName("review_user_nickname")

            private String review_user_nickname = "";

            @SerializedName("profile_image_url")

            private String profile_image_url = "";

            @SerializedName("like_count")

            private String like_count = "";

            @SerializedName("like_flag")

            private String like_flag = "";

            @SerializedName("review_contents")

            private String review_contents = "";

            @SerializedName("rating_point")

            private String rating_point = "";

            @SerializedName("write_date")

            private String write_date = "";

            @SerializedName("image_url")

            private ArrayList<String> image_url;


            public String getPlace_id() {
                return place_id;
            }

            public void setPlace_id(String place_id) {
                this.place_id = place_id;
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

            public String getSns_division() {
                return sns_division;
            }

            public void setSns_division(String sns_division) {
                this.sns_division = sns_division;
            }

            public String getReview_user_nickname() {
                return review_user_nickname;
            }

            public void setReview_user_nickname(String review_user_nickname) {
                this.review_user_nickname = review_user_nickname;
            }

            public String getProfile_image_url() {
                return profile_image_url;
            }

            public void setProfile_image_url(String profile_image_url) {
                this.profile_image_url = profile_image_url;
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

            public String getReview_contents() {
                return review_contents;
            }

            public void setReview_contents(String review_contents) {
                this.review_contents = review_contents;
            }

            public String getRating_point() {
                return rating_point;
            }

            public void setRating_point(String review_point) {
                this.rating_point = rating_point;
            }

            public String getWrite_date() {
                return write_date;
            }

            public void setWrite_date(String write_date) {
                this.write_date = write_date;
            }

            public ArrayList<String> getImage_url() {
                return image_url;
            }

            public void setImage_url(ArrayList<String> image_url) {
                this.image_url = image_url;
            }




        }


    }
}

