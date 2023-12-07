package com.paper.model.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 爬取的论文数据
 * @TableName arxiv_paper
 */
public class ArxivPaper implements Serializable {
    private final static Gson GSON = new Gson();
    /**
     * 爬取的数据的id
     */
    private Integer id;

    /**
     * An url of the form `http://arxiv.org/abs/{id}
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
    private List<String> authors;

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
    private List<String> categories;



    /**
     * 
     */
    private String pdfUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArxivId() {
        return arxivId;
    }

    public void setArxivId(String arxivId) {
        this.arxivId = arxivId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJournalRef() {
        return journalRef;
    }

    public void setJournalRef(String journalRef) {
        this.journalRef = journalRef;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public ArxivPaper(Integer id, String arxivId, Date updated, Date published, String title, String summary, List<String> authors, String comment, String journalRef, String doi, String primaryCategory, List<String> categories, String pdfUrl) {
        this.id = id;
        this.arxivId = arxivId;
        this.updated = updated;
        this.published = published;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.comment = comment;
        this.journalRef = journalRef;
        this.doi = doi;
        this.primaryCategory = primaryCategory;
        this.categories = categories;
        this.pdfUrl = pdfUrl;
    }

    public ArxivPaper() {
    }

    public  ArxivPaperDO convertToArxivPaperDO(){
        ArxivPaperDO arxivPaperDO = new ArxivPaperDO();
        arxivPaperDO.setArxivId(this.arxivId);
        arxivPaperDO.setUpdated(this.updated);
        arxivPaperDO.setPublished(this.published);
        arxivPaperDO.setTitle(this.title);
        arxivPaperDO.setSummary(this.summary);
        arxivPaperDO.setAuthors(GSON.toJson(this.authors));
        arxivPaperDO.setComment(this.comment);
        arxivPaperDO.setJournalRef(this.journalRef);
        arxivPaperDO.setDoi(this.getDoi());
        arxivPaperDO.setPrimaryCategory(this.primaryCategory);
        arxivPaperDO.setCategories(GSON.toJson(this.categories));
        arxivPaperDO.setPdfUrl(pdfUrl);
        return arxivPaperDO;
    }
}