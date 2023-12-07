package com.paper.api.openai.domain;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class ChatCompletion implements Serializable {
    private @NonNull String model;
    private @NonNull List<Message> messages;
    private double temperature;
    @JsonProperty("top_p")
    private Double topP;
    private Integer n;
    private boolean stream;
    private List<String> stop;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("presence_penalty")
    private double presencePenalty;
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty;
    @JsonProperty("logit_bias")
    private Map logitBias;
    private String user;

    @Deprecated
    public int tokens() {
        // todo complete the method in the future if need
        if (!CollectionUtil.isEmpty(this.messages) && !StrUtil.isBlank(this.model)) {
            return 0;
        } else {
            log.warn("参数异常model：{}，prompt：{}", this.model, this.messages);
            return 0;
        }
    }

    public static enum Model {
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
        GPT_4("gpt-4"),
        GPT_4_0314("gpt-4-0314"),
        GPT_4_32K("gpt-4-32k"),
        GPT_4_32K_0314("gpt-4-32k-0314");

        private String name;

        public String getName() {
            return this.name;
        }

        private Model(String name) {
            this.name = name;
        }
    }
}
