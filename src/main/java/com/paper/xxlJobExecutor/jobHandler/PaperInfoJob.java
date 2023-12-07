package com.paper.xxlJobExecutor.jobHandler;

import com.paper.service.PaperInfoService;
import com.paper.service.TopConferenceService;
import com.paper.xxlJobExecutor.utils.ParseUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PaperInfoJob {

    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private TopConferenceService topConferenceService;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("summaryByGpt")
    public void summaryJobHandler() {
        log.info("XXL-JOB, is running.");
        XxlJobHelper.log("XXL-JOB is running");
        String batchSizeStr = XxlJobHelper.getJobParam();
        log.info("params from xxl-job : {}", batchSizeStr);
        //write log to xxl-job
        XxlJobHelper.log("params from xxl-job : {}", batchSizeStr);
        int batchSize = 0;
        try {
            batchSize = Integer.parseInt(batchSizeStr);
        } catch (Exception e) {
            XxlJobHelper.log("fail to parse param :" + e.getMessage());
            boolean b = XxlJobHelper.handleFail("wrong parameters");
        }
        int summaryCount = paperInfoService.briefSummaryPaper(batchSize);
        XxlJobHelper.handleSuccess(summaryCount + " papers are done !");
    }

    @XxlJob("translateTitle")
    public void translateTitleJobHandler() {
        log.info("XXL-JOB, is running.");
        XxlJobHelper.log("XXL-JOB is running");
        String batchSizeStr = XxlJobHelper.getJobParam();
        log.info("params from xxl-job : {}", batchSizeStr);
        //write log to xxl-job
        XxlJobHelper.log("params from xxl-job : {}", batchSizeStr);
        int batchSize = 0;
        try {
            batchSize = Integer.parseInt(batchSizeStr);
        } catch (Exception e) {
            XxlJobHelper.log("fail to parse param :" + e.getMessage());
            boolean b = XxlJobHelper.handleFail("wrong parameters");
        }
        int summaryCount = paperInfoService.translateTitle(batchSize);
        XxlJobHelper.handleSuccess(summaryCount + " papers are done !");
    }

    @XxlJob("translateAbstract")
    public void translateAbstractJobHandler() {
        XxlJobHelper.log("XXL-JOB is running");
        String batchSizeStr = XxlJobHelper.getJobParam();
        log.info("params from xxl-job : {}", batchSizeStr);
        //write log to xxl-job
        XxlJobHelper.log("params from xxl-job : {}", batchSizeStr);
        int batchSize = 0;
        try {
            batchSize = Integer.parseInt(batchSizeStr);
        } catch (Exception e) {
            XxlJobHelper.log("fail to parse param :" + e.getMessage());
            boolean b = XxlJobHelper.handleFail("wrong parameters");
        }
        int summaryCount = paperInfoService.translateAbstract(batchSize);
        XxlJobHelper.handleSuccess(summaryCount + " papers are done !");
    }

    @XxlJob("topConferenceToPaperInfo")
    public void convertTopConferenceToPaperInfoJobHandler() {
        XxlJobHelper.log("XXL-JOB is running");
        String batchSizeStr = XxlJobHelper.getJobParam();
        log.info("params from xxl-job : {}", batchSizeStr);
        //write log to xxl-job
        XxlJobHelper.log("params from xxl-job : {}", batchSizeStr);
        int batchSize = ParseUtils.parseInt(batchSizeStr);
        int summaryCount = paperInfoService.addPaperInfoFromTopConference(batchSize);
        XxlJobHelper.handleSuccess(summaryCount + " papers are done !");
    }

    @XxlJob("syncFromMysqlToEs")
    public void syncFromMysqlToEs(){
        int i = paperInfoService.syncFromMysqlToEs();
        XxlJobHelper.handleSuccess(i + " papers are handled");

    }
}
