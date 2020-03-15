package com.dev.eatjeong.common;

import java.util.List;
import java.util.Map;

public class CommonMapListResponseVO {

    private String code;
    private String message;

    private List<String> dataList;

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

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }
}

