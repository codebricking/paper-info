package com.paper.common;

import com.paper.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
 *

 */
@Data
public class PageRequest extends BasePageRequest{


    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
