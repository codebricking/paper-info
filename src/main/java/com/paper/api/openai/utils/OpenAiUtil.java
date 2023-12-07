package com.paper.api.openai.utils;

import com.paper.api.openai.domain.Message;

import java.util.LinkedList;
import java.util.List;

public class OpenAiUtil {
    public static List<Message> wrapUserMessage(String content){
        Message message = new Message();
        message.setContent(content);
        message.setRole(Message.Role.USER.getName());
        LinkedList<Message> messages = new LinkedList<>();
        messages.add(message);
        return messages;
    }
}
