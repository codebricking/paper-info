package com.paper.api.google.service;

import com.paper.api.google.service.domain.GoogleTranslateResp;

public interface GoogleTranslateService {
    GoogleTranslateResp googleTranslateByHttps(String q);

    GoogleTranslateResp googleTranslateByHttpsPost(String q);

}
