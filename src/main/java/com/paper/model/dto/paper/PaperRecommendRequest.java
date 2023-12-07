package com.paper.model.dto.paper;

import com.paper.common.BasePageRequest;
import com.paper.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *

 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "论文推荐请求")
public class PaperRecommendRequest extends BasePageRequest implements Serializable {

    @ApiModelProperty(value = "论文的ID", example = "1")
    private int id;
    @ApiModelProperty(value = "推荐类型，0: 根据当前论文推荐, 1: 根据用户推荐", example = "0")
    private int type;

    private static final long serialVersionUID = 928764610594020595L;
}