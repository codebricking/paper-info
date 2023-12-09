package com.paper.crawler.paper;

import com.paper.crawler.paper.AAAICrawler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AAAICrawlerTest {
    @Resource
    private AAAICrawler aaaiCrawler;

    @Test
    void saveAAAIByYear() {
        aaaiCrawler.saveAAAIByYear(2013);
    }
}