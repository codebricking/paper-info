package com.paper.service.impl;

import com.google.gson.Gson;
import com.paper.api.google.service.GoogleTranslateService;
import com.paper.api.google.service.domain.GoogleTranslateResp;
import com.paper.api.openai.domain.ChatCompletionResponse;
import com.paper.api.openai.service.impl.OpenApiService;
import com.paper.api.openai.utils.OpenAiUtil;
import com.paper.common.ErrorCode;
import com.paper.dao.PaperInfoDao;
import com.paper.esdao.PaperInfoEsDao;
import com.paper.exception.BusinessException;
import com.paper.model.dto.paper.PaperInfoEsDTO;
import com.paper.model.dto.paper.PaperRecommendRequest;
import com.paper.model.entity.PaperInfo;
import com.paper.model.vo.SearchVO;
import com.paper.service.PaperInfoService;
import com.paper.service.TopConferenceService;
import com.paper.service.TranslateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 论文信息实现类
 */

@Service
@Slf4j
public class PaperInfoServiceImpl implements PaperInfoService {

    @Resource
    private PaperInfoDao paperInfoDao;
    @Resource
    private PaperInfoEsDao paperInfoEsDao;
    @Resource
    private TopConferenceService topConferenceService;
    @Resource
    private OpenApiService openApiService;
    @Resource
    private TranslateService translateService;
    @Resource
    private GoogleTranslateService googleTranslateService;
    Gson gson = new Gson();

    @Override
    public List<PaperInfo> getLatestPaperInfo(int pageNo, int pageSize) {
        return paperInfoDao.getLatestPaperInfo(pageNo, pageSize);
    }

    @Override
    public List<PaperInfo> searchPaperInfo(String searchText, int pageNum, int pageSize) {
        if (StringUtils.isBlank(searchText)) {
            return paperInfoDao.getLatestPaperInfo(pageNum, pageSize);
        }
        return paperInfoDao.searchPaperInfo(searchText, pageNum, pageSize);
    }

    // todo search from es
    @Override
    public Page<PaperInfoEsDTO> searchPaperInfoEs(String searchText, int pageNum, int pageSize) {
        if (StringUtils.isBlank(searchText)) {
            //分页查询
            Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
            //在es中，当前页，第一页从 0 开始，1 表示第二页
            //设置查询分页
            PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort);
            Page<PaperInfoEsDTO> page = paperInfoEsDao.findAll(pageRequest);
            return page;

            // no valid search text , return newest data
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        // search from es
        Page<PaperInfoEsDTO> records = paperInfoEsDao.findByKeywordInFields(searchText,pageable);
        return records;

    }


    // add top conference to paper info
    @Override
    public int addPaperInfoFromTopConference(int batchSize) {
        List<PaperInfo> paperInfos = topConferenceService.convertTopConferenceToPaperInfo(batchSize);
        return paperInfoDao.savePaperInfo(paperInfos);
    }

    @Override
    public int briefSummaryPaper(int batchSize) {
        int count = 0;
        List<PaperInfo> paperInfos = paperInfoDao.getUnSummarised(batchSize);
        for (PaperInfo paperInfo : paperInfos) {
            try {
                ChatCompletionResponse chatCompletionResponse = openApiService.chat(OpenAiUtil.wrapUserMessage(createContent(paperInfo.getSummary())));
                String briefSummary = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
                paperInfo.setSummaryBrief(briefSummary);
                count += paperInfoDao.updateByPrimaryKeySelective(paperInfo);
            } catch (Exception e) {
                log.info("fail to summary for {}, exception = {}", paperInfo, e);

            }
            try {
                GoogleTranslateResp googleTranslateResp = googleTranslateService.googleTranslateByHttpsPost(paperInfo.getSummary());
                String translatedText = googleTranslateResp.getData().getTranslations().get(0).getTranslatedText();
                paperInfo.setSummaryCn(translatedText);
                paperInfoDao.updateByPrimaryKeySelective(paperInfo);
            }catch (Exception e){
                log.info("fail to translate summary for  {} ",paperInfo);
            }
            try {
                if (!StringUtils.isBlank(paperInfo.getTitleCn())) {
                    continue;
                }
                String titleCn = translateService.deepLTranslate(paperInfo.getTitle());
                paperInfo.setTitleCn(titleCn);
                paperInfo.setUpdateTime(new Date());
                paperInfoDao.updateByPrimaryKeySelective(paperInfo);
            } catch (Exception e) {
                log.info("fail to translate title for data in paper_info : id ={}", paperInfo.getId());
            }
        }
        return count;
    }
    @Override
    public int summaryAbstract(int batchSize){
        int count = 0;
        List<PaperInfo> paperInfos = paperInfoDao.getUnSummarisedByAbstract(batchSize);
        for (PaperInfo paperInfo : paperInfos) {
            try {
                ChatCompletionResponse chatCompletionResponse = openApiService.chat(OpenAiUtil.wrapUserMessage(createContent(paperInfo.getSummary())));
                String briefSummary = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
                paperInfo.setSummaryBrief(briefSummary);
                count += paperInfoDao.updateSummaryCnByKey(paperInfo);
            } catch (Exception e) {
                log.info("fail to summary for {}, exception = {}", paperInfo, e);
                String remark = "fail to summary by gpt" + e.getMessage();
                paperInfoDao.updateRemark(paperInfo.getId(),remark );
            }
        }
        return count;
    }
    @Override
    public int translateAbstract(int batchSize){
        int count = 0;
        List<PaperInfo> paperInfos = paperInfoDao.getUnTranslatedAbstract(batchSize);
        for (PaperInfo paperInfo : paperInfos) {
            try {
                GoogleTranslateResp googleTranslateResp = googleTranslateService.googleTranslateByHttpsPost(paperInfo.getSummary());
                String translatedText = googleTranslateResp.getData().getTranslations().get(0).getTranslatedText();
                paperInfo.setSummaryCn(translatedText);
                count +=paperInfoDao.updateAbstractCnByKey(paperInfo);
            }catch (Exception e){
                log.info("fail to translate summary for  {} ",paperInfo);
                String remark = "fail to translate " +e.getMessage();
                paperInfoDao.updateRemark(paperInfo.getId(),remark);
            }
        }
        return count;
    }
    @Override
    public int translateTitle(int batchSize){
        int count = 0;
        List<PaperInfo> paperInfos = paperInfoDao.getUnTranslatedTitle(batchSize);
        for (PaperInfo paperInfo : paperInfos) {
            try {
//                String titleCn = translateService.deepLTranslate(paperInfo.getTitle());
                GoogleTranslateResp googleTranslateResp = googleTranslateService.googleTranslateByHttpsPost(paperInfo.getTitle());
                String titleCn = googleTranslateResp.getData().getTranslations().get(0).getTranslatedText();
                paperInfo.setTitleCn(titleCn);
                paperInfo.setUpdateTime(new Date());
                count +=paperInfoDao.updateTitleCnByKey(paperInfo);
            }catch (Exception e){
                log.info("fail to translate summary for  {} ",paperInfo);
                String remark = "fail to translate " +e.getMessage();
                paperInfoDao.updateRemark(paperInfo.getId(),remark);
            }

        }
        return count;

    }


    public String createContent(String origin) {
        return "Please summarize the abstract of this paper in one sentence so that the reader can quickly grasp the general idea. Use Chinese to respond. Do not describe the question, just answer the results in Chinese.The original abstract is below:\n [" + origin + "]";
    }


    @Override
    public int syncFromMysqlToEs() {
        List<PaperInfo> paperInfos = paperInfoDao.getValidPaperInfo();
        for (PaperInfo source : paperInfos) {
            PaperInfoEsDTO target = new PaperInfoEsDTO();
            BeanUtils.copyProperties(source, target);
            PaperInfoEsDTO save = paperInfoEsDao.save(target);
        }
        return paperInfos.size();
    }

    @Override
    public int syncFromMysqlToEsByTimeSec(int seconds) {
        List<PaperInfo> paperInfos = paperInfoDao.getPaperInfoByTime(seconds);
        for (PaperInfo source : paperInfos) {
            PaperInfoEsDTO target = new PaperInfoEsDTO();
            BeanUtils.copyProperties(source, target);
            PaperInfoEsDTO save = paperInfoEsDao.save(target);
        }
        return paperInfos.size();
    }

    @Override
    public int insertIntoPaperInfo(PaperInfo paperInfo){
        if (isEmpty(paperInfo)){
            return 0;
        }

        boolean exist = paperInfoDao.exist(paperInfo);
        if (exist){
            return 0;
        }
        paperInfoDao.savePaperInfo(paperInfo);
        return 0;
    }

    @Override
    public SearchVO recommendPaper(PaperRecommendRequest request) {
        // get paper from es by id
        if (request.getId() <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        PaperInfoEsDTO firstById = paperInfoEsDao.findFirstById(request.getId());
        if (firstById == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Pageable pageable = PageRequest.of(request.getCurrent() - 1, request.getPageSize());
        Page<PaperInfoEsDTO> page = paperInfoEsDao.findByTitleAndSummary(firstById.getTitle(), firstById.getSummary(), pageable);
        SearchVO searchVO = new SearchVO();
        searchVO.setDataList(page.getContent());
        searchVO.setTotalElements(page.getTotalElements());
        return searchVO;
    }

    boolean isEmpty(PaperInfo paperInfo){
        if (paperInfo == null ){
            return false;
        }
        if (paperInfo.getId() == null || paperInfo.getId() == 0){
            return true;
        }
        if (StringUtils.isAnyBlank(paperInfo.getSummary(),paperInfo.getSummaryCn(),paperInfo.getTitle(),paperInfo.getTitleCn(),paperInfo.getSummaryBrief())){
            return true;
        }
        return false;
    }




}
