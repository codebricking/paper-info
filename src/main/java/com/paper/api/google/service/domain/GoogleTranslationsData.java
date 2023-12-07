package com.paper.api.google.service.domain;

import java.util.List;

public class GoogleTranslationsData {
    private List<Translations> translations;
    public void setTranslations(List<Translations> translations) {
        this.translations = translations;
    }
    public List<Translations> getTranslations() {
        return translations;
    }

    public GoogleTranslationsData(List<Translations> translations) {
        this.translations = translations;
    }

    public GoogleTranslationsData() {
    }
}
