package com.paper.crawler.paper;

import com.paper.crawler.paper.JMLRCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;

@SpringBootTest
class JMLRCrawlerTest {
    @Resource
    private JMLRCrawler jmlrCrawler;

    @Test
    void getPageLinks() {
        HashMap<String, String> pageLinks = jmlrCrawler.getPageLinks();
    }
    @Test
    void saveByVolume() {

        jmlrCrawler.saveByVolume("Volume 14 (January 2013 - December 2013)",2013);
    }
}