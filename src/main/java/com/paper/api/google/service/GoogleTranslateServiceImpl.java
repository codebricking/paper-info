package com.paper.api.google.service;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.paper.api.google.service.domain.GoogleTranslateParams;
import com.paper.api.google.service.domain.GoogleTranslateResp;
import com.paper.api.google.service.domain.GoogleTranslationsData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleTranslateServiceImpl implements GoogleTranslateService {
    private Gson gson = new Gson();
    // todo add api key
    static String apiKey = "xxxxxx";
    String baeGetUrl = "https://translation.googleapis.com/language/translate/v2?target=zh-CN";
//    String basePostUrl = "https://translation.googleapis.com/language/translate/v2?";
    String basePostUrl = "http://xxxxxx/language/translate/v2";// Proxy
//    String basePostUrl = "http://127.0.0.1/language/translate/v2";//local test
    public static Map<String,String> supportedLanguages = new HashMap<>();


    @Override
    public GoogleTranslateResp googleTranslateByHttps(String q){
        String url = baeGetUrl
                +"&key="+apiKey
                +"&q=" + q;
        String jsonStr = HttpUtil.get(url);
        GoogleTranslateResp googleTranslateResp = gson.fromJson(jsonStr, GoogleTranslateResp.class);
        GoogleTranslationsData data = googleTranslateResp.getData();
        System.out.println(data.getTranslations().get(0).getTranslatedText());
        return googleTranslateResp;

    }
    @Override
    public GoogleTranslateResp googleTranslateByHttpsPost(String q){
        GoogleTranslateParams params = new GoogleTranslateParams();
        params.setQ(q);
        params.setFormat("text");
        params.setApiKey(apiKey);
        params.setTarget("zh-CN");
        String jsonStr = HttpUtil.post(basePostUrl , gson.toJson(params));
        System.out.println(jsonStr);
        GoogleTranslateResp googleTranslateResp = gson.fromJson(jsonStr, GoogleTranslateResp.class);
        return googleTranslateResp;
    }


}
