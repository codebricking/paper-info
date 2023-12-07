package com.paper.crawler.paper;

import com.google.gson.Gson;
import com.paper.common.ErrorCode;
import com.paper.exception.BusinessException;
import com.paper.model.entity.TopConference;
import com.paper.service.TopConferenceService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;


@Service
@Slf4j
public class CVFCrawler {
    private final static Gson GSON = new Gson();
    static String baseUrl = "https://openaccess.thecvf.com/";
    @Resource
    private TopConferenceService topConferenceService;

    @Value("#{'${cvf.conference:CVPR,ICCV,WACV,ACCV}'.split(',')}")
    public Set<String> CVF_CONFERENCE ;

    public List<TopConference> getCvfByYear(int year, String conference) {
        if (!CVF_CONFERENCE.contains(conference)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<String> paperUrls = new LinkedList<>();
        String url = null;
        if (conference.equals("ICCV") && year == 2021){
            url = "https://openaccess.thecvf.com/ICCV2021?day=all";
            paperUrls.add(url);
        }else if (conference.equals("CVPR") && year >= 2022){
            url = "https://openaccess.thecvf.com/CVPR" + year + "?day=all";
        }
        if (urlMapSep.containsKey(conference + year) ){
            paperUrls = getUrlsByDay(conference + year);
        }
        if (urlMap.containsKey(conference+year)){
            url = urlMap.get(conference + year);
            paperUrls.add(url);
        }
        if (paperUrls.isEmpty()){
            paperUrls.add("https://openaccess.thecvf.com/" + conference + year);
        }

        List<TopConference> topConferences = new LinkedList<>();

        for (String paperUrl : paperUrls) {
            Document doc = null;
            try {
                doc = Jsoup.connect(paperUrl).get();
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常, check in https://openaccess.thecvf.com/menu");
            }
            Elements dt = doc.select("dt");

            log.info("total paper {} to be crawled", dt.size());
            for (Element element : dt) {
                TopConference topConference = new TopConference();
                try {
                    Element aTag = element.select("a").first(); // 获取第一个匹配的<a>标签
                    String href = aTag.attr("href"); // 获取href的链接
                    String title = aTag.text(); // 获取<a>标签的内容
                    topConference.setConferenceName(conference);
                    topConference.setPaperUrl("https://openaccess.thecvf.com"+addSlashIfNeed(href.trim()));
                    topConference.setTitle(title.trim());
                    topConference.setConferenceYear(year);
                    if (topConferenceService.existsIgnoreCase(topConference)){
                        log.info(" data {} is already in database, skip", topConference);
                        continue;
                    }
                    fillDetail(topConference);
                }catch (Exception e){
                    log.error("fail to fill detail for {}", topConference);
                    continue;
                }
                topConferences.add(topConference);
                topConferenceService.saveTopConference(topConference);
                try {
                    Thread.sleep(1000);
                }catch (Exception ignored){

                }
            }

        }



        return topConferences;
    }


    public TopConference fillDetail(TopConference c) throws IOException {
        Document doc = null;
        doc = Jsoup.connect(c.getPaperUrl()).get();
        Elements authorElements = doc.select("meta[name=citation_author]");
        LinkedList<String> authors = new LinkedList<>();
        for (Element authorElement : authorElements) {
            String authorName = authorElement.attr("content");
            authors.add(authorName);
        }

        String paperPdfUrl =null;

        Element abstractElement = doc.selectFirst("div#abstract");
        String paperAbstract = abstractElement.text();

        Element ddElement = doc.select("dd").last(); // 获取最后一个 <dd> 标签
        Elements links = ddElement.select("a"); // 选择 <a> 元素
        String arXivLink = null;

        for (Element link : links) {
            String href = link.attr("href");
            String text = link.text();
            if (text.equals("arXiv")) {
                arXivLink = href;
            }else if (text.equals("pdf")){
                paperPdfUrl = href;
            }
        }
        c.setAuthors(GSON.toJson(authors));
        c.setArxivId(arXivLink);
        c.setPdfUrl("https://openaccess.thecvf.com" +  paperPdfUrl);
        c.setPaperAbstract(paperAbstract);
        c.setCreateTime(new Date());
        c.setUpdateTime(new Date());
        return c;
    }
    List<String> getUrlsByDay(String key){
        String url = "https://openaccess.thecvf.com/" + key;
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            return new ArrayList<>();
        }
        LinkedList<String> res = new LinkedList<>();
        Elements dd = document.select("dd");
        for (Element element : dd) {
            Element a = element.select("a").first();
            String href = a.attr("href");
            String replace = href.replace(".py", "");
            boolean add = res.add(baseUrl + replace );
        }
        return res;
    }
    static Map<String,String> urlMap = new HashMap<>();
    static Map<String,String> urlMapSep = new HashMap<>();
    static {
        urlMap.put("CVPR2021","https://openaccess.thecvf.com/CVPR2021?day=all");
        urlMap.put("CVPR2017","https://openaccess.thecvf.com/CVPR2017");
        urlMap.put("ICCV2017","https://openaccess.thecvf.com/ICCV2017");
        urlMap.put("WACV2021","https://openaccess.thecvf.com/CVPR2021?day=all");
        urlMap.put("WACV2020","https://openaccess.thecvf.com/WACV2020");
    }
    static {
      urlMapSep.put("CVPR2020","https://openaccess.thecvf.com/CVPR2020");
    }
    String addSlashIfNeed(String s){
        if (s.startsWith("/")){
            return s;
        }
        return "/" + s;
    }



}
