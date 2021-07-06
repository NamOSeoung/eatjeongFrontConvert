package com.dev.eatjeong.main.search.searchRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchGoogleListResponseVO {

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

        @SerializedName("author")

        private String author = "";

        @SerializedName("g_content")

        private String g_content = "";

        @SerializedName("g_rating")

        private String g_rating = "";

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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getG_content() {
            return g_content;
        }

        public void setG_content(String g_content) {
            this.g_content = g_content;
        }

        public String getG_rating() {
            return g_rating;
        }

        public void setG_rating(String g_rating) {
            this.g_rating = g_rating;
        }

        public String getWrite_date() {
            return write_date;
        }

        public void setWrite_date(String write_date) {
            this.write_date = write_date;
        }
    }

}

