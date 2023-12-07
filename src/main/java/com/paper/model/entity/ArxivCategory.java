package com.paper.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * arXiv Category Taxonomy
 * @TableName arxiv_category
 */
public class ArxivCategory implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 分类-缩写
     */
    private String category;

    /**
     * 分类，全称
     */
    private String fullName;

    /**
     * 上一级分类
     */
    private String previousCat;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 分类-缩写
     */
    public String getCategory() {
        return category;
    }

    /**
     * 分类-缩写
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 分类，全称
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 分类，全称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 上一级分类
     */
    public String getPreviousCat() {
        return previousCat;
    }

    /**
     * 上一级分类
     */
    public void setPreviousCat(String previousCat) {
        this.previousCat = previousCat;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}