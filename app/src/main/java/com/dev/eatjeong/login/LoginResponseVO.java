package com.dev.eatjeong.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class LoginResponseVO {

    private String error_message;
    private String code;
    private String message;

    private Map<String,String> dataList;



    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }


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

    public Map<String, String> getDataList() {
        return dataList;
    }

    public void setDataList(Map<String, String> dataList) {
        this.dataList = dataList;
    }


}

