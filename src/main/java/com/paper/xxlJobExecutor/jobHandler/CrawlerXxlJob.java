package com.paper.xxlJobExecutor.jobHandler;

import com.paper.service.ArxivPaperService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CrawlerXxlJob {
    @Resource
    private ArxivPaperService arxivPaperService;

    @XxlJob("crawlArxiv")
    public void crawlArxivJobHandler()  {
        log.info("XXL-JOB, is running.");
        XxlJobHelper.log("XXL-JOB is running");
        String batchSizeStr = XxlJobHelper.getJobParam();
        log.info("params from xxl-job : {}", batchSizeStr);
        //write log to xxl-job
        XxlJobHelper.log("params from xxl-job : {}", batchSizeStr);
        int pageSize = 0;
        try {
            pageSize = Integer.parseInt(batchSizeStr);
        }catch (Exception e){
            XxlJobHelper.log("fail to parse param :" + e.getMessage());
            boolean b = XxlJobHelper.handleFail("wrong parameters");
        }
        int size = arxivPaperService.getArxivPaperFromOfficialApi(pageSize).size();
        XxlJobHelper.handleSuccess(size + " papers are done !");
    }
}
