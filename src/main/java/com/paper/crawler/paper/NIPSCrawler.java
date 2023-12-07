package com.paper.crawler.paper;

import com.google.gson.Gson;
import com.paper.common.ErrorCode;
import com.paper.exception.BusinessException;
import com.paper.model.entity.TopConference;
import com.paper.service.TopConferenceService;
import com.paper.utils.CrawlerUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class NIPSCrawler {
    private final static Gson GSON = new Gson();
    @Resource
    private TopConferenceService topConferenceService;
    static String NIPS_BASE_URL = "https://proceedings.neurips.cc/paper/";
    static String NIPS_PAPER_PREFIX ="https://proceedings.neurips.cc";
    public List<TopConference> saveNIPSByYear(int year){
        LinkedList<TopConference> topConferences = new LinkedList<>();
        String pageUrl = NIPS_BASE_URL + year;
        Document paperPage = null;
        try {
            paperPage= Jsoup.connect(pageUrl).get();
        } catch (IOException e) {
            log.error("can not get page {}",pageUrl);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Element pageList = paperPage.select(".paper-list").first();// # 表示根据id查， . 表示根据class查
        Elements conferenceElements = pageList.select(".conference");
        if (conferenceElements.size() == 0){
            conferenceElements = pageList.select(".none");
        }
        for (Element conferenceElement : conferenceElements) {
            Element aElement = conferenceElement.select("a").first();
            String title = aElement.text().trim();
            TopConference topConference = new TopConference();
            topConference.setTitle(title);
            if (topConferenceService.existsIgnoreCase(topConference)){
                log.info(" data is already in database, skip {}", topConference);
                continue;
            }
            String paperUrl =NIPS_PAPER_PREFIX +  aElement.attr("href").trim();
            topConference.setPaperUrl(paperUrl);
            String[] aus = conferenceElement.select("i").first().text().split(",");
            LinkedList<String> authors = new LinkedList<>();
            for (String au : aus) {
                authors.add(au.trim());
            }
            topConference.setAuthors(GSON.toJson(authors));
            Document detailPage = null;
            try {
                detailPage = Jsoup.connect(topConference.getPaperUrl()).get();
            } catch (IOException e) {
                log.error("fail to get detail page {}", topConference);
                continue;
            }
            Element colElement = detailPage.select(".container-fluid").first().select("div.col").first();//从<div>标签中提取文本内容，并使用选择器选择包含"Abstract"的<h4>标签，
            Elements pEles = colElement.select("p");// hard to locate , find the longest as the abstract
            Element paperAbstractEle = null;
            int maxLen = 0;
            for (Element pEle : pEles) {
                if (pEle.text().trim().length()> maxLen){
                    paperAbstractEle = pEle;
                }
            }

            topConference.setPaperAbstract(paperAbstractEle.text().trim());
            String pdfUrl =NIPS_PAPER_PREFIX + colElement.select("a:contains(Paper)").first().attr("href");
            topConference.setConferenceYear(year);
            topConference.setConferenceName("NIPS");
            topConference.setCreateTime(new Date());
            topConference.setUpdateTime(new Date());
            topConference.setPdfUrl(pdfUrl);
            topConferences.add(topConference);
            topConferenceService.saveTopConference(topConference);
            CrawlerUtils.sleepRandomMil(10,200);

        }
        return topConferences;

    }
}
