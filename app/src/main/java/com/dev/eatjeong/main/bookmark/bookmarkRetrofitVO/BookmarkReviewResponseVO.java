package com.dev.eatjeong.main.bookmark.bookmarkRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookmarkReviewResponseVO {

    @SerializedName("dataList")

    public List<DataList> mDatalist;
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

    public class DataList {

        @SerializedName("place_name")
        private String place_name;

        @SerializedName("place_id")
        private String place_id = "";

        @SerializedName("review_id")
        private String review_id = "";

        @SerializedName("thumbnail_url")
        private String thumbnail_url = "";

        @SerializedName("url")
        private String url = "";

        @SerializedName("title")
        private String title = "";

        @SerializedName("description")
        private String description = "";

        @SerializedName("author")
        private String author = "";

        @SerializedName("write_date")
        private String write_date = "";

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

        public String getReview_id() {
            return review_id;
        }

        public void setReview_id(String review_id) {
            this.review_id = review_id;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getWrite_date() {
            return write_date;
        }

        public void setWrite_date(String write_date) {
            this.write_date = write_date;
        }
    }
}

