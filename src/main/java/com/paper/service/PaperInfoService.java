package com.paper.service;

import com.paper.model.dto.paper.PaperRecommendRequest;
import com.paper.model.entity.PaperInfo;
import com.paper.model.dto.paper.PaperInfoEsDTO;
import com.paper.model.vo.SearchVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaperInfoService {
    List<PaperInfo> getLatestPaperInfo(int pageNo, int pageSize);
    List<PaperInfo> searchPaperInfo(String searchText, int pageNum, int pageSize);

    // todo search from es
    Page<PaperInfoEsDTO> searchPaperInfoEs(String searchText, int pageNum, int pageSize);

    int addPaperInfoFromTopConference(int batchSize);

    int briefSummaryPaper(int batchSize);

    int summaryAbstract(int batchSize);

    int translateAbstract(int batchSize);

    int translateTitle(int batchSize);

    int syncFromMysqlToEs();

    int syncFromMysqlToEsByTimeSec(int seconds);

    int insertIntoPaperInfo(PaperInfo paperInfo);

    SearchVO recommendPaper(PaperRecommendRequest request);
}
