package com.paper.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 *

 */
@Data
public class SearchVO implements Serializable {

    private List<?> dataList;
    private long totalElements;

    private static final long serialVersionUID = 3610475324953066585L;

}
