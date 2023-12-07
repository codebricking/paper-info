package com.paper.crawler.paper;

import com.google.gson.Gson;
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
public class AISTATSCrawler {
    private final static Gson GSON = new Gson();
    public  static Set<String> PMLRSet = new HashSet<>();
    static {
        PMLRSet.add("AISTATS");
        PMLRSet.add("ACML");
        PMLRSet.add("COLT");
        PMLRSet.add("ICML");
        PMLRSet.add("IJCAI ");
    }
    @Resource
    private TopConferenceService topConferenceService;
    static String AISTATS_HOME_PAGE = "http://proceedings.mlr.press/";
    static String PREFIX = "http://proceedings.mlr.press/";

    public List<TopConference> saveAISTATSByYear(int year, String conferenceName){

        HashMap<String, String> pageLinks = getPageLinks();
        LinkedList<String> linksByYear = new LinkedList<>();
        for (String key : pageLinks.keySet()) {
            if (key.contains(" " + year + "") && key.toLowerCase().contains(conferenceName.toLowerCase())){
                linksByYear.add(pageLinks.get(key));
            }
        }
        if (linksByYear.isEmpty()){
            return null;
        }
        LinkedList<TopConference> topConferences = new LinkedList<>();

        for (String link : linksByYear) {
            Document paperPage;
            try {
                paperPage = Jsoup.connect(link).get();
            } catch (IOException e) {
                log.error("fail to ge page via link {}", link);
                continue;
            }
            Elements papers = paperPage.select(".paper");
            log.info(" paper for this link {}", papers.size());
            for (Element paperEle : papers) {
                Element titleEle = paperEle.select(".title").first();
                String title = titleEle.text().trim();
                TopConference topConference = new TopConference();
                topConference.setTitle(title);
                if (topConferenceService.existsIgnoreCase(topConference)){
                    continue;
                }
                String[] split = paperEle.select(".authors").first().text().split(",");
                LinkedList<String> authors = new LinkedList<>();
                for (String au : split) {
                    authors.add(au.trim());
                }
                Elements linksEles = paperEle.select(".links a");
                String paperUrl = null;
                String pdfUrl = null;
                for (Element linkEle : linksEles) {
                    if (linkEle.text().contains("abs")){
                        paperUrl = linkEle.attr("href");
                    }
                    if (linkEle.text().contains("Download PDF")){
                        pdfUrl = linkEle.attr("href");
                    }
                }
                topConference.setPaperUrl(paperUrl);
                topConference.setAuthors(GSON.toJson(authors));
                topConference.setConferenceYear(year);
                topConference.setConferenceName(conferenceName);
                topConference.setPdfUrl(pdfUrl);
                Document paperDetail = null;
                try {
                    paperDetail = Jsoup.connect(topConference.getPaperUrl()).get();
                } catch (IOException e) {
                    log.error("fail to get detail for {}", topConference);
                    continue;
                }
                Element paperAbstractEle = paperDetail.select(".abstract").first();
                if (paperAbstractEle == null || paperAbstractEle.text().isEmpty()){
                    continue;
                }
                topConference.setPaperAbstract(paperAbstractEle.text());
                topConference.setConferenceYear(year);
                topConference.setCreateTime(new Date());
                topConference.setUpdateTime(new Date());
                topConferences.add(topConference);
                topConferenceService.saveTopConference(topConference);
                CrawlerUtils.sleepRandomMil(1000,2000);
            }

        }
        return topConferences;
    }
    public HashMap<String, String> getPageLinks() {
        HashMap<String, String> linksMap = new HashMap<>();

        Document document;
        try {
            document = Jsoup.connect(AISTATS_HOME_PAGE).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements proceedingList = document.select(".proceedings-list");
        for (Element pl : proceedingList) {
            Elements lis = pl.select("li");
            for (Element li : lis) {
                if (li.text().isEmpty()) {
                    continue; // 跳过空标签
                }
                Elements a = li.select("a");
                String link =PREFIX +  a.attr("href");
                String text = li.text();
                System.out.println(link);
                System.out.println(text);
                linksMap.put(text, link);
            }
        }
        return linksMap;

    }

}
