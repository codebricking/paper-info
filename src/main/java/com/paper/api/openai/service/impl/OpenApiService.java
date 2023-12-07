package com.paper.api.openai.service.impl;

import com.paper.api.openai.domain.ChatCompletionResponse;
import com.paper.api.openai.domain.Message;

import java.util.List;

public interface OpenApiService {
    ChatCompletionResponse chat(List<Message> messages);
}
