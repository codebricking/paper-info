package com.paper.mapper;


import com.paper.model.entity.PaperInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 24102
* @description 针对表【paper_info_cn(论文的中文信息)】的数据库操作Mapper
* @createDate 2023-04-13 17:21:00
* @Entity generator.domain.PaperInfoCn
*/
public interface PaperInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PaperInfo record);
    Integer existsIgnoreCase(@Param("title") String title);

    int insertSelective(PaperInfo record);

    PaperInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaperInfo record);

    int updateByPrimaryKey(PaperInfo record);

    List<PaperInfo> getLatestPaperInfo(@Param("start") int start, @Param("pageSize") int pageSize);

    List<PaperInfo> searchPaperInfo(@Param("searchText") String searchText,@Param("start") int start,@Param("pageSize") int pageSize);

    List<PaperInfo> getUnSummarised(@Param("batchSize")int batchSize);

    List<PaperInfo> getValidPaperInfo();

    List<PaperInfo> getByTimeSec(@Param("seconds") int seconds);

    List<PaperInfo> getUnSummarisedByAbstract(@Param("batchSize")int batchSize);

    int updateSummaryCnByKey(PaperInfo paperInfo);

    int updateRemark(@Param("id") Integer id, @Param("remark") String remark);

    List<PaperInfo> getUnTranslatedAbstract(@Param("batchSize") int batchSize);

    int updateTitleCnByKey(PaperInfo paperInfo);

    List<PaperInfo> getUnTranslatedTitle(@Param("batchSize")int batchSize);

    int updateAbstractCnByKey(PaperInfo paperInfo);

    Integer existId(@Param("id") Integer id);
}
