package com.paper.model.entity;

import com.google.gson.Gson;
import lombok.Data;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;


/**
 * 爬取的论文数据
 * @TableName arxiv_paper
 */
@Data
public class ArxivPaperDO implements Serializable {
    /**
     * 爬取的数据的id
     */
    private Integer id;

    /**
     * A url of the form `http://arxiv.org/abs/{id}
     */
    private String arxivId;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 发布时间
     */
    private Date published;

    /**
     * 
     */
    private String title;

    /**
     * abstract
     */
    private String summary;

    /**
     * 所有作者，使用JSON存储
     */
    private String authors;

    /**
     * The authors' comment if present.
     */
    private String comment;

    /**
     * A journal reference if present.
     */
    private String journalRef;

    /**
     * A URL for the resolved DOI to an external resource if present.
     */
    private String doi;

    /**
     * The result's primary arXiv category
     */
    private String primaryCategory;

    /**
     * All of the result's categories.
     */
    private String categories;



    /**
     * 
     */
    private String pdfUrl;

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}