package com.paper.controller;

import com.paper.common.BaseResponse;
import com.paper.common.ResultUtils;
import com.paper.model.entity.PaperInfo;
import com.paper.service.TranslateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedList;

@RestController
@RequestMapping("/translate")
@Slf4j
public class TranslateController {

    @Resource
    private TranslateService translateService;
    @PostMapping("start")
    public BaseResponse<LinkedList<PaperInfo>> startTranslateJob(int maxNum){
        LinkedList<PaperInfo> translate = translateService.translate(maxNum);
        return ResultUtils.success(translate);

    }

}
