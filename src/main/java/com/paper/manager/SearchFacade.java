package com.paper.manager;

import com.paper.common.ErrorCode;
import com.paper.datasource.DataSourceRegistry;
import com.paper.exception.ThrowUtils;
import com.paper.model.dto.paper.PaperInfoEsDTO;
import com.paper.model.dto.search.SearchRequest;
import com.paper.model.enums.SearchTypeEnum;
import com.paper.model.vo.SearchVO;
import com.paper.service.PaperInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 搜索门面
 */
@Component
@Slf4j
public class SearchFacade {


    @Resource
    private PaperInfoService paperInfoService;


    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);// 保留字段，暂时不用
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        // 搜索出所有数据
        Page<PaperInfoEsDTO> page = paperInfoService.searchPaperInfoEs(searchText, current, pageSize);
        SearchVO searchVO = new SearchVO();
        searchVO.setDataList(page.getContent());
        searchVO.setTotalElements(page.getTotalElements());
        return searchVO;

    }

    public SearchVO searchPaper(SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        // 搜索出所有数据
        Page<PaperInfoEsDTO> page = paperInfoService.searchPaperInfoEs(searchText, current, pageSize);
        SearchVO searchVO = new SearchVO();
        searchVO.setDataList(page.getContent());
        searchVO.setTotalElements(page.getTotalElements());
        return searchVO;
    }
}
