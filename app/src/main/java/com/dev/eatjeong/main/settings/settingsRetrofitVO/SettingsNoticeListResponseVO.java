package com.dev.eatjeong.main.settings.settingsRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingsNoticeListResponseVO {

    private String code;
    private String message;

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

    @SerializedName("dataList")

    public List<DataList> mDatalist;


    public class DataList {

        @SerializedName("notice_id")
        public String notice_id;

        @SerializedName("add_date")
        public String add_date;

        @SerializedName("add_time")
        public String add_time;

        @SerializedName("user_id")
        public String user_id;

        @SerializedName("subject")
        public String subject;

        @SerializedName("items")
        public List<Map<String,String>> items;

        public String getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(String notice_id) {
            this.notice_id = notice_id;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public List<Map<String, String>> getItems() {
            return items;
        }

        public void setItems(List<Map<String, String>> items) {
            this.items = items;
        }
    }
}

