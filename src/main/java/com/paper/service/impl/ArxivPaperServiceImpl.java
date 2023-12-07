package com.paper.service.impl;

import cn.hutool.http.HttpUtil;
import com.paper.dao.ArxivPaperDao;
import com.paper.dao.PaperInfoDao;
import com.paper.model.entity.ArxivPaper;
import com.paper.model.entity.ArxivPaperDO;
import com.paper.service.ArxivPaperService;
import com.paper.utils.ArxivParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 论文服务实现类
 */

@Service
@Slf4j
public class ArxivPaperServiceImpl implements ArxivPaperService {
    static int MAX_RESULTS = 2;//set to 50 when online
    static int START = 0;

    @Resource
    private ArxivPaperDao arxivPaperDao;
    @Resource
    private PaperInfoDao paperInfoDao;

    @Override
    public List<ArxivPaperDO> getArxivPaperFromOfficialApi(int pageSize) {
        if (pageSize == 0) {
            pageSize = MAX_RESULTS;
        }
        int paperCount = 0;

        List<String> cats = arxivPaperDao.listArxivCategory().stream().map(a -> a.getCategory()).collect(Collectors.toList());
        //for test ,use one
//        List<String> cats = new LinkedList<>();
//        cats.add("cs.AI");

        for (String cat : cats) {
            String url = getUrl(cat, pageSize);
            String response = HttpUtil.get(url);
//            log.info("get data from url = {} ,response = ",url,response);
            List<ArxivPaper> arxivPapers = new LinkedList<>();
            try {
                arxivPapers = ArxivParser.parse(response);
            } catch (Exception e) {

            }

            try {
                if (arxivPapers.isEmpty()) {
                    continue;
                }
                int recordInsertNum = arxivPaperDao.saveRecord(arxivPapers);
                paperCount += recordInsertNum;
                log.info("cat [{}] has insert [{}] data", cat, recordInsertNum);

            } catch (Exception e) {
                log.info("fail to insert into mysql {}", arxivPapers);
            }
        }
        return arxivPaperDao.listArxivPaper(0, paperCount);
    }

    @Override
    public List<ArxivPaperDO> listArxivPaper(int pageNum, int pageSize) {
        return arxivPaperDao.listArxivPaper(pageNum, pageSize);
    }


    private String getUrl(String cat, int pageSize) {
        return "http://export.arxiv.org/api/query?search_query=cat:" +
                cat +
                "&start=" +
                START +
                "&max_results=" +
                pageSize +
                "&sortBy=lastUpdatedDate&sortOrder=descending";
    }



}
