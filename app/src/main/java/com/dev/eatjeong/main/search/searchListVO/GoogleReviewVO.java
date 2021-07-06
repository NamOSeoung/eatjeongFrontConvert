package com.dev.eatjeong.main.search.searchListVO;


public class GoogleReviewVO {

    private String index;
    private String review_id;
    private String write_date;
    private String author;
    private String url;
    private String g_content;
    private String g_rating;

    public GoogleReviewVO(
            String index,
            String review_id,
            String author,
            String g_content,
            String g_rating,
            String write_date){

        this.index = index;
        this.review_id = review_id;
        this.author = author;
        this.url = g_content;
        this.g_content = g_content;
        this.g_rating = g_rating;
        this.write_date = write_date;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
