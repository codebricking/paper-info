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
import java.util.*;


@Service
@Slf4j
public class ECCVCrawler {
    private final static Gson GSON = new Gson();
    @Resource
    private TopConferenceService topConferenceService;
    public static Set<Integer> ECCVYear = new HashSet<>();

    static String ECCV_HOME_PAGE_URL = "https://www.ecva.net/papers.php";
    static String ECCV_PAPER_PREFIX = "https://www.ecva.net/";


    public List<TopConference> getECCVByYear(int year) {
        if (!ECCVYear.contains(year)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Document ECCVHomePage = null;
        try {
            ECCVHomePage = Jsoup.connect(ECCV_HOME_PAGE_URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LinkedList<TopConference> topConferences = new LinkedList<>();
        Elements subPageByYear = ECCVHomePage.select(".accordion-content");
        System.out.println(subPageByYear.size());
        for (Element subPage : subPageByYear) {//每一年的
            Elements titleElements = subPage.select(".ptitle");
            log.info("{} records to for year {}", titleElements.size(),year);
            for (Element titleElement : titleElements) {
                Element aElement = titleElement.select("a").first();
                String title = aElement.text().trim();
                String paperUrl = ECCV_PAPER_PREFIX +  aElement.attr("href");
                if (!paperUrl.contains(String.valueOf(year))){
                    log.info("{} not the year ,skip {}",title);
                    continue;
                }
                TopConference topConference = new TopConference();
                topConference.setConferenceYear(year);
                topConference.setTitle(title);
                topConference.setPaperUrl(paperUrl);
                if (topConferenceService.existsIgnoreCase(topConference)){
                    log.info("data is already in database, skip ! topConferenceDO = {}", topConference);
                    continue;
                }
                topConference.setConferenceName("ECCV");
                Document detailPage;
                try {
                    detailPage = Jsoup.connect(topConference.getPaperUrl()).get();
                } catch (IOException e) {
                    log.error("fail to get detail for topConferenceDO = {}", topConference);
                    continue;
                }
                String[] aus = detailPage.select("#authors").first().select("b").first().text().split(",");
                LinkedList<String> authors = new LinkedList<>();
                for (String au : aus) {
                    authors.add(au);
                }
                topConference.setAuthors(GSON.toJson(authors));
                String paperAbstract = detailPage.select("#abstract").first().text().trim();
                topConference.setPaperAbstract(paperAbstract);
                String pdfUrl =ECCV_PAPER_PREFIX +  detailPage.select("a:contains(pdf)").first().attr("href").replace("../../../../","");
                topConference.setPdfUrl(pdfUrl);
                topConference.setCreateTime(new Date());
                topConference.setUpdateTime(new Date());
                topConferences.add(topConference);
                topConferenceService.saveTopConference(topConference);
                CrawlerUtils.sleepRandomMil(1000,2000);

            }
        }
        return topConferences;
    }
    static {
        ECCVYear.add(2022);
        ECCVYear.add(2020);
        ECCVYear.add(2018);
    }



}
