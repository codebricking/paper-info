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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class JMLRCrawler {
    private final static Gson GSON = new Gson();
    @Resource
    private TopConferenceService topConferenceService;
    public static String JMLR_HOME_PAGE = "https://www.jmlr.org/papers/";
    public static String PagePrefix = "https://www.jmlr.org";
    public static HashMap<String, String> JMLRLinkMap = new HashMap<>();
    public List<TopConference> saveByVolume(String volume, int year){
        if (!JMLRLinkMap.containsKey(volume) || !volume.contains(String.valueOf(year))){
            return null;
        }
        String pageUrl = JMLRLinkMap.get(volume);
        Document paperPage = null;
        try {
            paperPage = Jsoup.connect(pageUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements paperEles = paperPage.select("dl");
        List<TopConference> topConferenceList = new LinkedList<>();
        for (Element paperEle : paperEles) {
            Element titleEle = paperEle.select("dt").first();
            String title = titleEle.text().trim();
            TopConference topConference = new TopConference();
            topConference.setTitle(title);
            if (topConferenceService.existsIgnoreCase(topConference)){
                log.info("data {} is already in database, skip !");
                continue;
            }

            Element dd = paperEle.select("dd").first();
            String[] aus = dd.select("i").first().text().split(",");
            LinkedList<String> authors = new LinkedList<>();
            for (String au : aus) {
                authors.add(au.trim());
            }
            topConference.setAuthors(GSON.toJson(authors));
            Elements aEles = dd.select("a");
            String pdfUrl = null;
            String paperUrl = null;
            for (Element aEle : aEles) {
                if (aEle.text().contains("pdf")){
                    pdfUrl =PagePrefix + aEle.attr("href");
                } else if (aEle.text().contains("abs")) {
                    paperUrl = PagePrefix + aEle.attr("href");
                }
            }
            topConference.setPaperUrl(paperUrl);
            topConference.setPdfUrl(pdfUrl);
            topConference.setConferenceYear(year);
            topConference.setConferenceName("JMLR");
            Document paperDetailPage = null;
            try {
                paperDetailPage = Jsoup.connect(topConference.getPaperUrl()).get();
            } catch (IOException e) {
                log.error("can not get detial for {}", topConference);
                continue;
            }
            String abstractText = paperDetailPage.select(".abstract").first().text();
            topConference.setPaperAbstract(abstractText);
            topConference.setCreateTime(new Date());
            topConference.setUpdateTime(new Date());

            CrawlerUtils.sleepRandomMil(1000,2000);
            topConferenceList.add(topConference);
            topConferenceService.saveTopConference(topConference);
        }
        return topConferenceList;
    }



    public HashMap<String, String> getPageLinks() {
        HashMap<String, String> linksMap = new HashMap<>();

        Document document;
        try {
            document = Jsoup.connect(JMLR_HOME_PAGE).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        使用Jsoup的选择器来选择id为"content"的元素。可以使用#id选择器。
        Elements volumeEles = document.select("#content").first().select("p");
        for (Element volumeEle : volumeEles) {
            if (volumeEle.text().isEmpty()){
                continue;
            }
            if (volumeEle.select("a").first() == null){
                continue;
            }
            String key = volumeEle.text().trim();
            String linkSub = volumeEle.select("a").first().attr("href");
            String link = JMLR_HOME_PAGE + linkSub;
            linksMap.put(key,link);
            System.out.println("JMLRLinkMap.put(\"" + key+"\",\"" + link +"/\");");
        }

        return linksMap;

    }
    static {
        // links of each year
        JMLRLinkMap.put("Volume 23 (January 2022 - Present)","https://www.jmlr.org/papers/v23/");
        JMLRLinkMap.put("Volume 22 (January 2021 - December 2021)","https://www.jmlr.org/papers/v22/");
        JMLRLinkMap.put("Volume 21 (January 2020 - December 2020)","https://www.jmlr.org/papers/v21/");
        JMLRLinkMap.put("Volume 20 (January 2019 - December 2019)","https://www.jmlr.org/papers/v20/");
        JMLRLinkMap.put("Volume 19 (August 2018 - December 2018)","https://www.jmlr.org/papers/v19/");
        JMLRLinkMap.put("Volume 18 (February 2017 - August 2018)","https://www.jmlr.org/papers/v18/");
        JMLRLinkMap.put("Volume 17 (January 2016 - January 2017)","https://www.jmlr.org/papers/v17/");
        JMLRLinkMap.put("Volume 16 (January 2015 - December 2015)","https://www.jmlr.org/papers/v16/");
        JMLRLinkMap.put("Volume 15 (January 2014 - December 2014)","https://www.jmlr.org/papers/v15/");
        JMLRLinkMap.put("Volume 14 (January 2013 - December 2013)","https://www.jmlr.org/papers/v14/");
        JMLRLinkMap.put("Volume 13 (January 2012 - December 2012)","https://www.jmlr.org/papers/v13/");
        JMLRLinkMap.put("Volume 12 (January 2011 - December 2011)","https://www.jmlr.org/papers/v12/");
        JMLRLinkMap.put("Volume 11 (January 2010 - December 2010)","https://www.jmlr.org/papers/v11/");
        JMLRLinkMap.put("Volume 10 (January 2009 - December 2009)","https://www.jmlr.org/papers/v10/");
        JMLRLinkMap.put("Volume 9 (January 2008 - December 2008)","https://www.jmlr.org/papers/v9/");
        JMLRLinkMap.put("Volume 8 (January 2007 - December 2007)","https://www.jmlr.org/papers/v8/");
        JMLRLinkMap.put("Volume 7 (January 2006 - December 2006)","https://www.jmlr.org/papers/v7/");
        JMLRLinkMap.put("Volume 6 (January 2005 - December 2005)","https://www.jmlr.org/papers/v6/");
        JMLRLinkMap.put("Volume 5 (December 2003 - December 2004)","https://www.jmlr.org/papers/v5/");
        JMLRLinkMap.put("Volume 4 (Apr 2003 - December 2003)","https://www.jmlr.org/papers/v4/");
        JMLRLinkMap.put("Volume 3 (Jul 2002 - Mar 2003)","https://www.jmlr.org/papers/v3/");
        JMLRLinkMap.put("Volume 2 (Oct 2001 - Mar 2002)","https://www.jmlr.org/papers/v2/");
        JMLRLinkMap.put("Volume 1 (Oct 2000 - Sep 2001)","https://www.jmlr.org/papers/v1/");



    }
}
