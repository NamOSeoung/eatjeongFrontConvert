package com.dev.eatjeong.main.search.searchListVO;


public class PlaceListVO {

    private String place_id;
    private String place_name;
    private String latitute;
    private String longitude;
    private String place_address;



    public PlaceListVO(String place_id, String place_name, String latitute, String longitude,String place_address){
        this.place_id = place_id;
        this.place_name = place_name;
        this.latitute = latitute;
        this.longitude = longitude;
        this.place_address = place_address;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getLatitute() {
        return latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlace_address() {
        return place_address;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }
}
