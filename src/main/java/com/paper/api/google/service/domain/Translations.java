package com.paper.api.google.service.domain;

public class Translations {

    private String translatedText;
    private String detectedSourceLanguage;

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setDetectedSourceLanguage(String detectedSourceLanguage) {
        this.detectedSourceLanguage = detectedSourceLanguage;
    }

    public String getDetectedSourceLanguage() {
        return detectedSourceLanguage;
    }

}
