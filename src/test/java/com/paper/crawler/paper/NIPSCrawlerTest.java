package com.paper.crawler.paper;

import com.paper.crawler.paper.NIPSCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NIPSCrawlerTest {

    @Resource
    private NIPSCrawler nipsCrawler;
    @Test
    void saveNIPSByYear() {
        nipsCrawler.saveNIPSByYear(2013);
    }
}