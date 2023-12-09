package com.paper.api.openai.service.impl;

import com.paper.api.openai.domain.ChatChoice;
import com.paper.api.openai.domain.ChatCompletionResponse;
import com.paper.api.openai.domain.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.LinkedList;

@SpringBootTest
class OpenApiServiceTest {
    @Resource
    private OpenApiService openApiService;

    @Test
    void chat() {
        Message message = new Message();
        message.setRole("user");
        message.setContent("say hello");
        LinkedList<Message> messages = new LinkedList<>();
        messages.add(message);
        ChatCompletionResponse chat = openApiService.chat(messages);
        ChatChoice chatChoice = chat.getChoices().get(0);
    }
}