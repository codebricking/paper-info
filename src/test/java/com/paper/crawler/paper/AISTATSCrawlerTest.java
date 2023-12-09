package com.paper.crawler.paper;


import com.paper.crawler.paper.AISTATSCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AISTATSCrawlerTest {
    @Resource
    private AISTATSCrawler aistatsCrawler;

    @Test
    void getPageLinks() {
        aistatsCrawler.getPageLinks();
    }
    @Test
    void saveAISTATSByYear() {
        aistatsCrawler.saveAISTATSByYear(2013,"AISTATS");
        aistatsCrawler.saveAISTATSByYear(2013,"ACML");
        aistatsCrawler.saveAISTATSByYear(2013,"COLT");
        aistatsCrawler.saveAISTATSByYear(2013,"ICML");
    }
}