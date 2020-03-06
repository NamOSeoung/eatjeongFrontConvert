package com.dev.eatjeong.main.search.searchListVO;


public class TistoryReviewVO {

    private String index;
    private String review_id;
    private String title;
    private String write_date;
    private String author;
    private String description;
    private String url;
    private String thumbnail_url;
    private String bookmark_flag;

    public TistoryReviewVO(
            String index,
            String review_id,
            String title,
            String write_date,
            String author,
            String description,
            String url,
            String thumbnail_url,
            String bookmark_flag){

        this.index = index;
        this.review_id = review_id;
        this.title = title;
        this.write_date = write_date;
        this.author = author;
        this.description = description;
        this.url = url;
        this.thumbnail_url = thumbnail_url;
        this.bookmark_flag = bookmark_flag;
    }

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
