package com.dev.eatjeong.main.settings.settingsRetrofitVO;

import java.util.List;
import java.util.Map;

public class SettingsBlackListResponseVO {

    private String code;
    private String message;

    private Map<String, List<Map<String,String>>> dataList;

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

    public Map<String, List<Map<String,String>>> getDataList() {
        return dataList;
    }

    public void setDataList(Map<String, List<Map<String,String>>> dataList) {
        this.dataList = dataList;
    }
}

