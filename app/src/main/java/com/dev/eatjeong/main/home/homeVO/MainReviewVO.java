package com.dev.eatjeong.main.home.homeVO;


public class MainReviewVO {


    private String write_date;
    private String start_index;
    private String author;
    private String description;
    private String title;
    private String thumbnail_url;
    private String url;

    public MainReviewVO(String title) {

        this.title = title;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public String getStart_index() {
        return start_index;
    }

    public void setStart_index(String start_index) {
        this.start_index = start_index;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

