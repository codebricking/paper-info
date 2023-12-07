package com.paper.api.google.service.domain;

public class GoogleTranslateResp {
    private GoogleTranslationsData data;
    public void setData(GoogleTranslationsData data) {
        this.data = data;
    }
    public GoogleTranslationsData getData() {
        return data;
    }

    public GoogleTranslateResp(GoogleTranslationsData data) {
        this.data = data;
    }

    public GoogleTranslateResp() {
    }
}
