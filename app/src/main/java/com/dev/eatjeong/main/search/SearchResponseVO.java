package com.dev.eatjeong.main.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseVO {

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


        @SerializedName("index")

        private String index = "";


        @SerializedName("search_word")

        private String search_word = "";

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getSearch_word() {
            return search_word;
        }

        public void setSearch_word(String search_word) {
            this.search_word = search_word;
        }
    }


}

