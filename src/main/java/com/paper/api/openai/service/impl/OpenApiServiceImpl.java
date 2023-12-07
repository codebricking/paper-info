package com.paper.api.openai.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paper.api.openai.config.ChatConfig;
import com.paper.api.openai.domain.ChatCompletion;
import com.paper.api.openai.domain.ChatCompletionResponse;
import com.paper.api.openai.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class OpenApiServiceImpl implements OpenApiService {
    @Resource
    private ChatConfig chatConfig;

    // todo add api key
    static String AUTHOR_PREFIX = "Bearer ";

    @Override
    public ChatCompletionResponse chat(List<Message> messages){
        ChatCompletion chatCompletion = ChatCompletion.builder()
                // 最大的 tokens = 模型的最大上线 - 本次 prompt 消耗的 tokens
                .maxTokens(chatConfig.getMaxTokens())
                .model(chatConfig.getOpenaiApiModel())
                // [0, 2] 越低越精准
                .temperature(0.8)
                .topP(1.0)
                // 每次生成一条
                .n(1)
                .presencePenalty(1)
                .messages(messages)
                .stream(false)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = mapper.writeValueAsString(chatCompletion);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpResponse response = HttpRequest.post(chatConfig.getChatOpenAiApiBaseUrl())
                .header("Authorization",AUTHOR_PREFIX +  chatConfig.getAuthorization())// token鉴权
                .header("Content-Type", "application/json")
//                .auth(authorization)
                .timeout(chatConfig.getTimeOutMs())
                .setReadTimeout(chatConfig.getTimeOutMs())
                .setConnectionTimeout(chatConfig.getTimeOutMs())
                .body(requestBody)
                .execute();
        String body = response.body();
        ChatCompletionResponse chatCompletionResponse = null;
        try {
            chatCompletionResponse= mapper.readValue(body, ChatCompletionResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("question = {} \n answers = {}",messages,chatCompletionResponse.getChoices());
        return chatCompletionResponse;
    }

}
