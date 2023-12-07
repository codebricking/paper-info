package com.paper.api.google.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GoogleTranslateParams {
    private String q;
    private String target;
    private String format;
    private String apiKey;
}
