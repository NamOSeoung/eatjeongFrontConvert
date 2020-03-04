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
//    @SerializedName("dataList")
//
//    public List<DataList> mDatalist;
//
//
//    public class DataList {
//
//
//        @SerializedName("result")
//
//        private String result = "";
//
//
//        @SerializedName("login90_flag")
//
//        private String login90_flag = "";
//
//        @SerializedName("result_message")
//
//        private String result_message = "";
//
//
//        public String getResult() {
//            return result;
//        }
//
//        public void setResult(String result) {
//            this.result = result;
//        }
//
//        public String getLogin90_flag() {
//            return login90_flag;
//        }
//
//        public void setLogin90_flag(String login90_flag) {
//            this.login90_flag = login90_flag;
//        }
//
//        public String getResult_message() {
//            return result_message;
//        }
//
//        public void setResult_message(String result_message) {
//            this.result_message = result_message;
//        }
//    }


}

