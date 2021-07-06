package com.dev.eatjeong.main.search.searchRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchTistoryListResponseVO {

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

        @SerializedName("title")

        private String title = "";

        @SerializedName("write_date")

        private String write_date = "";

        @SerializedName("author")

        private String author = "";

        @SerializedName("description")

        private String description = "";

        @SerializedName("url")

        private String url = "";

        @SerializedName("thumbnail_url")

        private String thumbnail_url = "";

        @SerializedName("bookmark_flag")

        private String bookmark_flag = "";


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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWrite_date() {
            return write_date;
        }

        public void setWrite_date(String write_date) {
            this.write_date = write_date;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getBookmark_flag() {
            return bookmark_flag;
        }

        public void setBookmark_flag(String bookmark_flag) {
            this.bookmark_flag = bookmark_flag;
        }
    }

}

