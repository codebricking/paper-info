package com.paper.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 抓取的计算机视觉顶会文章信息
 * @TableName top_conference
 */
@TableName(value ="top_conference")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopConference implements Serializable {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 论文标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 论文连接
     */
    @TableField(value = "paper_url")
    private String paperUrl;

    /**
     * 论文摘要
     */
    @TableField(value = "paper_abstract")
    private String paperAbstract;

    /**
     * 所有作者，使用JSON存储
     */
    @TableField(value = "authors")
    private String authors;

    /**
     * arxivId,An url of the form `http://arxiv.org/abs/{id}
     */
    @TableField(value = "arxiv_id")
    private String arxivId;

    /**
     * 论文PDF文件链接
     */
    @TableField(value = "pdf_url")
    private String pdfUrl;

    /**
     * 会议名称
     */
    @TableField(value = "conference_name")
    private String conferenceName;

    /**
     * 会议所在的年
     */
    @TableField(value = "conference_year")
    private Integer conferenceYear;

    /**
     * 论文发表时间
     */
    @TableField(value = "published_time")
    private Date publishedTime;

    /**
     * 论文全文
     */
    @TableField(value = "full_text")
    private String fullText;

    /**
     * 记录创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 记录修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = -4078828375323398361L;


}