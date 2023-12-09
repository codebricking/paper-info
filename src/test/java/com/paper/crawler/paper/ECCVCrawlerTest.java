package com.paper.crawler.paper;

import com.paper.crawler.paper.ECCVCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ECCVCrawlerTest {
    @Resource
    private ECCVCrawler eccvCrawler;

    @Test
    void getECCVByYear() {
        eccvCrawler.getECCVByYear(2018);//year must be latter than 2018
    }
}