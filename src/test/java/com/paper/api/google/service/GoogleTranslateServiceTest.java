package com.paper.api.google.service;

import com.paper.api.google.service.domain.GoogleTranslateResp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GoogleTranslateServiceTest {
    @Resource
    private GoogleTranslateService googleTranslateService;

    @Test
    void googleTranslateByHttps() {
        GoogleTranslateResp googleTranslateResp = googleTranslateService.googleTranslateByHttps("How are you ?");
    }
    @Test
    void googleTranslateByHttpsPost() {
        GoogleTranslateResp googleTranslateResp = googleTranslateService.googleTranslateByHttpsPost("How are you ?");
        System.out.println(googleTranslateResp.getData().getTranslations().get(0).getTranslatedText());
    }
}