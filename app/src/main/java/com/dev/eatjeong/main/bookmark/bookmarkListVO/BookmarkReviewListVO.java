package com.dev.eatjeong.main.bookmark.bookmarkListVO;

public class BookmarkReviewListVO {
    private String place_name;
    private String place_id = "";
    private String review_id = "";
    private String thumbnail_url = "";
    private String url = "";
    private String title = "";
    private String description = "";
    private String author = "";
    private String write_date = "";

    public BookmarkReviewListVO(String place_name, String place_id, String review_id, String thumbnail_url, String url, String title, String description, String author, String write_date) {
        this.place_name = place_name;
        this.place_id = place_id;
        this.review_id = review_id;
        this.thumbnail_url = thumbnail_url;
        this.url = url;
        this.title = title;
        this.description = description;
        this.author = author;
        this.write_date = write_date;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
