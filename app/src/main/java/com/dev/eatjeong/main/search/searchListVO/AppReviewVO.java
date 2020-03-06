package com.dev.eatjeong.main.search.searchListVO;


import java.util.List;
import java.util.Map;

public class AppReviewVO {

    String index;
    String review_id;
    String review_user_id;
    String author;
    String profile_image_url;
    String rating_point;
    String review_contents;
    String like_count;
    String write_date;
    List<String> image_url;


    public AppReviewVO(
            String index,
            String review_id,
            String review_user_id,
            String author,
            String profile_image_url,
            String rating_point,
            String review_contents,
            String like_count,
            String write_date,
            List<String> image_url){

        this.index = index;
        this.review_id = review_id;
        this.review_user_id = review_user_id;
        this.author = author;
        this.profile_image_url = profile_image_url;
        this.rating_point = rating_point;
        this.review_contents = review_contents;
        this.like_count = like_count;
        this.write_date = write_date;
        this.image_url = image_url;
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

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }
}
