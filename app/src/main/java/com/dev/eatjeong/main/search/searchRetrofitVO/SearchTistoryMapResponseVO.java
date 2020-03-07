package com.dev.eatjeong.main.search.searchRetrofitVO;

import java.util.Map;

public class SearchTistoryMapResponseVO {

    private String code;
    private String message;

    private Map<String,String> dataList;

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

