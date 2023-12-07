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
public class AAAICrawler {
    private final static Gson GSON = new Gson();
    @Resource
    private TopConferenceService topConferenceService;
    static HashMap<Integer, String> AAAI_Artificial_Intelligence = new HashMap<>();
    public List<TopConference> saveAAAIByYear(int year) {
        if (!AAAI_Artificial_Intelligence.containsKey(year)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }


        String urls = AAAI_Artificial_Intelligence.get(year);
        LinkedList<TopConference> topConferences = new LinkedList<>();
        for (String url : urls.split(",")) {
            Document docProceedings = null;
            try {
                docProceedings = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
            }
            Element ulElement = docProceedings.select("ul").last();


            Elements liElements = ulElement.select("li");
            for (Element liElement : liElements) {
                // 提取<li>中的链接
                Element linkElement = liElement.select("a").first();
                String href = linkElement.attr("href");
                String text = linkElement.text();

                // 打印链接
                System.out.println("Link: " + href);
                System.out.println("Text: " + text);
                Document paperPage = null;
                try {
                    paperPage = Jsoup.connect(href).get();
                } catch (IOException e) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
                }
                // 使用Jsoup的选择器来选择所有class为"paper-wrap"的元素。可以使用.class选择器。
                Elements paperWraps = paperPage.select(".paper-wrap");
                for (Element paperElement : paperWraps) {
                    // 提取标题
                    Element titleElement = paperElement.select("h5 a").first();
                    String title = titleElement.text();

                    String paperUrl = titleElement.attr("href");

                    // 提取作者和页码
                    Element authorElement = paperElement.select(".papers-author-page p").first();
                    String author = authorElement.text();

                    // 提取PDF链接
                    Element pdfElement = paperElement.select("a[href$=.pdf]").first();
                    String pdfUrl = pdfElement.attr("href");
                    TopConference topConference = new TopConference();
                    topConference.setTitle(title);
                    topConference.setConferenceYear(year);
                    topConference.setConferenceName("AAAI");
                    topConference.setPaperUrl(paperUrl);
                    topConference.setPdfUrl(pdfUrl);
                    String[] split = author.split(",");
                    LinkedList<String> authorList = new LinkedList<>();
                    for (String s : split) {
                        authorList.add(s.trim());
                    }
                    topConference.setAuthors(GSON.toJson(authorList));
                    if (topConferenceService.existsIgnoreCase(topConference)){
                        log.info(" data {} is already in database, skip", topConference);
                        continue;
                    }
                    Document paperDerail = null;
                    try {
                        paperDerail = Jsoup.connect(topConference.getPaperUrl()).get();
                    } catch (IOException e) {
                        log.error("fail to get detail {}", topConference);
                        continue;
                    }
                    //使用Jsoup的选择器来选择class为"abstract-wrap"的元素。可以使用.class选择器。
                    Element abstractElement = paperDerail.select(".abstract-wrap").first();
                    Element pElement = abstractElement.select("div.abstract-output p").first();
                    String abstractText = pElement.text();
                    topConference.setPaperAbstract(abstractText);
                    topConference.setCreateTime(new Date());
                    topConference.setUpdateTime(new Date());
                    topConferences.add(topConference);
                    topConferenceService.saveTopConference(topConference);
                    CrawlerUtils.sleepRandomMil(100,200);
                }
            }

        }




        return topConferences;


    }
    // urls for AAAI Conference on Artificial Intelligence
    static {

        AAAI_Artificial_Intelligence.put(2022,"https://aaai.org/proceeding/aaai-36-2022");
        AAAI_Artificial_Intelligence.put(2021,"https://aaai.org/proceeding/aaai-35-2021");
        AAAI_Artificial_Intelligence.put(2020,"https://aaai.org/proceeding/aaai-34-2020");
        AAAI_Artificial_Intelligence.put(2019,"https://aaai.org/proceeding/aaai-33-2019");
        AAAI_Artificial_Intelligence.put(2018,"https://aaai.org/proceeding/aaai-32-2018");
        AAAI_Artificial_Intelligence.put(2017,"https://aaai.org/proceeding/01-thirty-first-aaai-conference-on-artificial-intelligence-31/,https://aaai.org/proceeding/02-2017-the-twenty-ninth-innovative-applications-of-artificial-intelligence-conference-31/");
        AAAI_Artificial_Intelligence.put(2016,"https://aaai.org/proceeding/aaai-30-2016");
        AAAI_Artificial_Intelligence.put(2015,"https://aaai.org/proceeding/aaai-29-2015");
        AAAI_Artificial_Intelligence.put(2014,"https://aaai.org/proceeding/aaai-28-2014");
        AAAI_Artificial_Intelligence.put(2013,"https://aaai.org/proceeding/aaai-27-2013");
        AAAI_Artificial_Intelligence.put(2012,"https://aaai.org/proceeding/aaai-26-2012");
        AAAI_Artificial_Intelligence.put(2011,"https://aaai.org/proceeding/aaai-25-2011");
        AAAI_Artificial_Intelligence.put(2010,"https://aaai.org/proceeding/aaai-24-2010");
        AAAI_Artificial_Intelligence.put(2008,"https://aaai.org/proceeding/aaai-23-2008");
        AAAI_Artificial_Intelligence.put(2007,"https://aaai.org/proceeding/aaai-22-2007");
        AAAI_Artificial_Intelligence.put(2006,"https://aaai.org/proceeding/aaai-21-2006");
        AAAI_Artificial_Intelligence.put(2005,"https://aaai.org/proceeding/aaai-20-2005");
        AAAI_Artificial_Intelligence.put(2004,"https://aaai.org/proceeding/aaai-19-2004");
        AAAI_Artificial_Intelligence.put(2002,"https://aaai.org/proceeding/aaai-18-2002");
        AAAI_Artificial_Intelligence.put(2000,"https://aaai.org/proceeding/aaai-17-2000");
        AAAI_Artificial_Intelligence.put(1999,"https://aaai.org/proceeding/aaai-16-1999");
        AAAI_Artificial_Intelligence.put(1998,"https://aaai.org/proceeding/aaai-15-1998");
        AAAI_Artificial_Intelligence.put(1997,"https://aaai.org/proceeding/aaai-14-1997");
        AAAI_Artificial_Intelligence.put(1996,"https://aaai.org/proceeding/aaai-13-1996");
        AAAI_Artificial_Intelligence.put(1994,"https://aaai.org/proceeding/aaai-12-1994");
        AAAI_Artificial_Intelligence.put(1993,"https://aaai.org/proceeding/aaai-11-1993");
        AAAI_Artificial_Intelligence.put(1992,"https://aaai.org/proceeding/aaai-10-1992");
        AAAI_Artificial_Intelligence.put(1991,"https://aaai.org/proceeding/aaai-09-1991");
        AAAI_Artificial_Intelligence.put(1990,"https://aaai.org/proceeding/aaai-08-1990");
        AAAI_Artificial_Intelligence.put(1988,"https://aaai.org/proceeding/aaai-07-1988");
        AAAI_Artificial_Intelligence.put(1987,"https://aaai.org/proceeding/aaai-06-1987");
        AAAI_Artificial_Intelligence.put(1986,"https://aaai.org/proceeding/aaai-05-1986");
        AAAI_Artificial_Intelligence.put(1984,"https://aaai.org/proceeding/aaai-04-1984");
        AAAI_Artificial_Intelligence.put(1983,"https://aaai.org/proceeding/aaai-03-1983");
        AAAI_Artificial_Intelligence.put(1982,"https://aaai.org/proceeding/aaai-02-1982");
        AAAI_Artificial_Intelligence.put(1980,"https://aaai.org/proceeding/aaai-01-1980");

    }

}
