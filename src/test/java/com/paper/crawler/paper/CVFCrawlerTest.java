package com.paper.crawler.paper;


import com.paper.model.entity.TopConference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class CVFCrawlerTest {
    @Resource
    private CVFCrawler cvfCrawler;
    @Test
    public void saveTest() {
//        List<TopConferenceDO> cvpr = cvfCrawler.getCvfByYear(2013, "CVPR");// larger or equal than 2013
        List<TopConference> iccv = cvfCrawler.getCvfByYear(2013, "ICCV");
//        List<TopConferenceDO> wacv = cvfCrawler.getCvfByYear(2014, "WACV");//2020-2023
//        int i = topConferenceService.saveTopConferenceDOBatch(cvpr);
//        System.out.printf("save %d papers" , i);
    }
}