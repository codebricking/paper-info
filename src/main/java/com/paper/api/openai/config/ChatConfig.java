package com.paper.api.openai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChatConfig {
    @Value("${openai.api.model:gpt-3.5-turbo-16k}")
    private String openaiApiModel;
    @Value("${openai.max.tokens:4000}")
    private int maxTokens;
    @Value("${openai.api.authorization:sk-xxxxx}")
    private String authorization;
    @Value("${openai.api.base.url:http://xxxxx/v1/chat/completions}")
    private String chatOpenAiApiBaseUrl;
    @Value("${openai.api.time.out.ms:100000}")
    private int timeOutMs;
}
