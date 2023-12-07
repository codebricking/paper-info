package com.paper.job.cycle;


import com.paper.service.ArxivPaperService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


//@Component
//@Slf4j
//public class CrawlArxivPaper {
//    @Resource
//    private ArxivPaperService paperService;
//
//    @Scheduled(fixedRate = 3 * 60 * 60 * 1000, initialDelay = 60 * 60 * 1000)//每3小时一次
//    public void run() {
//        paperService.getArxivPaperFromOfficialApi(10);//每个方向检索20条
//    }
//}
