package com.paper.controller;

import com.paper.annotation.AuthCheck;
import com.paper.common.BaseResponse;
import com.paper.common.PageRequest;
import com.paper.common.ResultUtils;
import com.paper.constant.UserConstant;
import com.paper.model.dto.paper.PaperRecommendRequest;
import com.paper.model.entity.ArxivPaperDO;
import com.paper.model.entity.PaperInfo;
import com.paper.model.vo.SearchVO;
import com.paper.service.ArxivPaperService;
import com.paper.service.PaperInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/paper")
@Slf4j
public class PaperController {
    @Resource
    private ArxivPaperService arxivPaperService;
    @Resource
    private PaperInfoService paperInfoService;
    /**
     * 手动开启爬取任务
     */
    @PostMapping("/crawl/arxiv")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<ArxivPaperDO>> startCrawl(int pageSize, HttpServletRequest request) {
        List<ArxivPaperDO> arxivPaperFromOfficialApi = arxivPaperService.getArxivPaperFromOfficialApi(pageSize);
        return ResultUtils.success(arxivPaperFromOfficialApi);
    }

    @PostMapping("/list/arxiv")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<ArxivPaperDO>> listRecentArxivPaper(@RequestBody PageRequest pageRequest, HttpServletRequest request) {
        List<ArxivPaperDO> arxivPaperFromOfficialApi = arxivPaperService.listArxivPaper(pageRequest.getCurrent(), pageRequest.getPageSize());
        return ResultUtils.success(arxivPaperFromOfficialApi);
    }
    @PostMapping("/info/arxiv")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<PaperInfo>> latestPaperInfo(@RequestBody PageRequest pageRequest, HttpServletRequest request) {
        List<PaperInfo> latestPaperInfo = paperInfoService.getLatestPaperInfo(pageRequest.getCurrent(), pageRequest.getPageSize());
        return ResultUtils.success(latestPaperInfo);
    }
    @PostMapping("/add/paper-info")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> addPaperInfoFromTopConference(@RequestParam("batchSize") int  batchSize){
        int addedCount = paperInfoService.addPaperInfoFromTopConference(batchSize);
        return ResultUtils.success(addedCount);
    }
    @PostMapping("/summary-and-translate")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> summaryAndTranslate(@RequestParam("batchSize") int  batchSize){
        int translatedCount = paperInfoService.briefSummaryPaper(batchSize);
        return ResultUtils.success(translatedCount);
    }
    @PostMapping("/summary-abstract")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> summaryAbstract(@RequestParam("batchSize") int  batchSize){
        int translatedCount = paperInfoService.summaryAbstract(batchSize);
        return ResultUtils.success(translatedCount);
    }
    @PostMapping("/translate-abstract")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> translateAbstract(@RequestParam("batchSize") int  batchSize){
        int translatedCount = paperInfoService.translateAbstract(batchSize);
        return ResultUtils.success(translatedCount);
    }
    @PostMapping("/translate-title")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> translateTitle(@RequestParam("batchSize") int  batchSize){
        int translatedCount = paperInfoService.translateTitle(batchSize);
        return ResultUtils.success(translatedCount);
    }
    @PostMapping("/sync-Mysql-ES-all")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> syncFromMysqlToEs(){
        int total = paperInfoService.syncFromMysqlToEs();
        return ResultUtils.success(total);
    }
    @PostMapping("/insert/paper-info")
    public BaseResponse<Integer> insertPaperInfo(@RequestBody PaperInfo paperInfo){
        paperInfoService.insertIntoPaperInfo(paperInfo);
        return ResultUtils.success(0);
    }

    @PostMapping("recommend")
    public BaseResponse<SearchVO> recPaper(@RequestBody  PaperRecommendRequest request){
        SearchVO searchVO = paperInfoService.recommendPaper(request);
        return ResultUtils.success(searchVO);
    }

}
