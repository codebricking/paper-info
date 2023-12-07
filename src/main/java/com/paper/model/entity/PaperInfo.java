package com.paper.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 论文的中文信息
 * @TableName paper_info
 */
@TableName(value ="paper_info")
public class PaperInfo implements Serializable {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 论文的链接
     */
    @TableField(value = "paper_url")
    private String paperUrl;

    /**
     * 论文的标题原文
     */
    @TableField(value = "title")
    private String title;

    /**
     * 论文的中文标题
     */
    @TableField(value = "title_cn")
    private String titleCn;

    /**
     * 论文的摘要原文
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 论文的摘要中文
     */
    @TableField(value = "summary_cn")
    private String summaryCn;

    /**
     * 论文的摘要简短总结
     */
    @TableField(value = "summary_brief")
    private String summaryBrief;

    /**
     * 关键字
     */
    @TableField(value = "key_words")
    private String keyWords;

    /**
     * 论文的关键词中文
     */
    @TableField(value = "key_words_cn")
    private String keyWordsCn;

    /**
     * 论文的PDF下载链接
     */
    @TableField(value = "pdf_url")
    private String pdfUrl;

    /**
     * 会议名称，顶会则显示，非顶会默认others
     */
    @TableField(value = "conference_name")
    private String conferenceName;

    /**
     * 论文的作者(Json String)
     */
    @TableField(value = "authors")
    private String authors;

    /**
     * 主要分类
     */
    @TableField(value = "primary_category")
    private String primaryCategory;

    /**
     * 所有分类标签
     */
    @TableField(value = "categories")
    private String categories;

    /**
     *
     */
    @TableField(value = "published")
    private Date published;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 论文的链接
     */
    public String getPaperUrl() {
        return paperUrl;
    }

    /**
     * 论文的链接
     */
    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }

    /**
     * 论文的标题原文
     */
    public String getTitle() {
        return title;
    }

    /**
     * 论文的标题原文
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 论文的中文标题
     */
    public String getTitleCn() {
        return titleCn;
    }

    /**
     * 论文的中文标题
     */
    public void setTitleCn(String titleCn) {
        this.titleCn = titleCn;
    }

    /**
     * 论文的摘要原文
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 论文的摘要原文
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 论文的摘要中文
     */
    public String getSummaryCn() {
        return summaryCn;
    }

    /**
     * 论文的摘要中文
     */
    public void setSummaryCn(String summaryCn) {
        this.summaryCn = summaryCn;
    }

    /**
     * 论文的摘要简短总结
     */
    public String getSummaryBrief() {
        return summaryBrief;
    }

    /**
     * 论文的摘要简短总结
     */
    public void setSummaryBrief(String summaryBrief) {
        this.summaryBrief = summaryBrief;
    }

    /**
     * 关键字
     */
    public String getKeyWords() {
        return keyWords;
    }

    /**
     * 关键字
     */
    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    /**
     * 论文的关键词中文
     */
    public String getKeyWordsCn() {
        return keyWordsCn;
    }

    /**
     * 论文的关键词中文
     */
    public void setKeyWordsCn(String keyWordsCn) {
        this.keyWordsCn = keyWordsCn;
    }

    /**
     * 论文的PDF下载链接
     */
    public String getPdfUrl() {
        return pdfUrl;
    }

    /**
     * 论文的PDF下载链接
     */
    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    /**
     * 会议名称，顶会则显示，非顶会默认others
     */
    public String getConferenceName() {
        return conferenceName;
    }

    /**
     * 会议名称，顶会则显示，非顶会默认others
     */
    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    /**
     * 论文的作者(Json String)
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * 论文的作者(Json String)
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * 主要分类
     */
    public String getPrimaryCategory() {
        return primaryCategory;
    }

    /**
     * 主要分类
     */
    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    /**
     * 所有分类标签
     */
    public String getCategories() {
        return categories;
    }

    /**
     * 所有分类标签
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     *
     */
    public Date getPublished() {
        return published;
    }

    /**
     *
     */
    public void setPublished(Date published) {
        this.published = published;
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
     *
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}