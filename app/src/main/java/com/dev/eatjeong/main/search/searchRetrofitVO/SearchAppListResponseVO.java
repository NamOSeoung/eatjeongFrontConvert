package com.dev.eatjeong.main.search.searchRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchAppListResponseVO {

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


        @SerializedName("index")

        private String index = "";

        @SerializedName("review_id")

        private String review_id = "";

        @SerializedName("review_user_id")

        private String review_user_id = "";

        @SerializedName("author")

        private String author = "";

        @SerializedName("profile_image_url")

        private String profile_image_url = "";

        @SerializedName("rating_point")

        private String rating_point = "";

        @SerializedName("review_contents")

        private String review_contents = "";

        @SerializedName("like_count")

        private String like_count = "";

        @SerializedName("write_date")

        private String write_date = "";

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }

        public String getRating_point() {
            return rating_point;
        }

        public void setRating_point(String rating_point) {
            this.rating_point = rating_point;
        }

        public String getReview_contents() {
            return review_contents;
        }

        public void setReview_contents(String review_contents) {
            this.review_contents = review_contents;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getWrite_date() {
            return write_date;
        }

        public void setWrite_date(String write_date) {
            this.write_date = write_date;
        }

        @SerializedName("image_url")

        public List<String> image_url;

        public List<String> getImage_url() {
            return image_url;
        }

        public void setImage_url(List<String> image_url) {
            this.image_url = image_url;
        }
    }

}

