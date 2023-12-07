package com.paper.mapper;

import com.paper.model.entity.ArxivCategory;

import java.util.List;

/**
* @author 24102
* @description 针对表【arxiv_category(arXiv Category Taxonomy)】的数据库操作Mapper
* @createDate 2023-04-08 22:09:38
* @Entity generator.domain.ArxivCategory
*/
public interface ArxivCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArxivCategory record);

    int insertSelective(ArxivCategory record);

    ArxivCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArxivCategory record);

    int updateByPrimaryKey(ArxivCategory record);

    List<ArxivCategory> listArxivCategory();

}
