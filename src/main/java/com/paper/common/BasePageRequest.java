package com.paper.common;

import com.paper.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页请求
 *

 */
@Data
public class BasePageRequest {


    @ApiModelProperty(value = "当前页号,默认为1", example = "1")
    private int current = 1;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小,默认为10", example = "10")
    private int pageSize = 10;

}
