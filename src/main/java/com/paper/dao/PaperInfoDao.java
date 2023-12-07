package com.paper.dao;


import com.paper.model.entity.PaperInfo;


import java.util.List;

public interface PaperInfoDao {

    List<PaperInfo> getLatestPaperInfo(int pageNo, int pageSize);

    List<PaperInfo> searchPaperInfo(String searchText, int pageNum, int pageSize);


    int savePaperInfo(List<PaperInfo> paperInfos);
    int savePaperInfo(PaperInfo paperInfo);

    List<PaperInfo> getUnSummarised(int batchSize);

    int updateByPrimaryKeySelective(PaperInfo paperInfo);

    List<PaperInfo> getValidPaperInfo();

    List<PaperInfo> getPaperInfoByTime(int seconds);

    List<PaperInfo> getUnSummarisedByAbstract(int batchSize);


    int updateSummaryCnByKey(PaperInfo paperInfo);


    int updateRemark(Integer id, String remark);

    List<PaperInfo> getUnTranslatedAbstract(int batchSize);

    int updateTitleCnByKey(PaperInfo paperInfo);

    List<PaperInfo> getUnTranslatedTitle(int batchSize);

    int updateAbstractCnByKey(PaperInfo paperInfo);

    boolean exist(PaperInfo paperInfo);
}
