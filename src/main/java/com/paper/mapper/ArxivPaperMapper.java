package com.paper.mapper;

import com.paper.model.entity.ArxivPaperDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 24102
* @description 针对表【arxiv_paper(爬取的论文数据)】的数据库操作Mapper
* @createDate 2023-04-09 01:32:38
* @Entity com.paper.model.entity.ArxivPaper
*/
public interface ArxivPaperMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArxivPaperDO record);

    int insertSelective(ArxivPaperDO record);

    ArxivPaperDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArxivPaperDO record);

    int updateByPrimaryKey(ArxivPaperDO record);

    int insertArxivPapers(List<ArxivPaperDO> records);

    List<ArxivPaperDO> listAll();

    List<ArxivPaperDO> listPage(@Param("start") int start, @Param("pageSize") int pageSize);

    List<ArxivPaperDO> listArxivNotTranslatedPaper(@Param("num") int num);
}
