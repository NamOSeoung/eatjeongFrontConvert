package com.dev.eatjeong.main.settings.settingsRetrofitVO;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SettingsTermsListResponseVO {

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

        @SerializedName("terms_code")
        public String terms_code;

        @SerializedName("terms_subject")
        public String terms_subject;

        public String getTerms_code() {
            return terms_code;
        }

        public void setTerms_code(String terms_code) {
            this.terms_code = terms_code;
        }

        public String getTerms_subject() {
            return terms_subject;
        }

        public void setTerms_subject(String terms_subject) {
            this.terms_subject = terms_subject;
        }
    }
}

