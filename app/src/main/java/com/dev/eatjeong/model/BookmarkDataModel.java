package com.dev.eatjeong.model;

public class BookmarkDataModel {

    private String place_name, place_id, place_address;

    public BookmarkDataModel(String place_name){
        this.place_name = place_name;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_address() {
        return place_address;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
}
