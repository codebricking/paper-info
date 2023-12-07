package com.paper.model.dto.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "paper_info")// index: something in ES like table in database
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperInfoEsDTO {
    private static final String DATA_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * 自增主键
     */
    @Id
    private int id;
    /**
     * 论文的链接
     */
    private String paperUrl;

    /**
     * 论文的标题原文
     */
    private String title;

    /**
     * 论文的中文标题
     */
    private String titleCn;

    /**
     * 论文的摘要原文
     */
    private String summary;

    /**
     * 论文的摘要中文
     */
    private String summaryCn;

    /**
     * 论文的摘要简短总结
     */
    private String summaryBrief;

    /**
     * 关键字
     */
    private String keyWords;

    /**
     * 论文的关键词中文
     */
    private String keyWordsCn;

    /**
     * 论文的PDF下载链接
     */
    private String pdfUrl;

    /**
     * 会议名称，顶会则显示，非顶会默认others
     */
    private String conferenceName;

    /**
     * 论文的作者(Json String)
     */
    private String authors;

    /**
     * 主要分类
     */
    private String primaryCategory;

    /**
     * 所有分类标签
     */
    private String categories;

    /**
     *  发表时间
     */
    private Date published;

    /**
     * 创建时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format ={}, pattern = DATA_TIME_PATTERN)
    private Date createTime;
    /**
     *
     */
    @Field(index = false, store = true, type = FieldType.Date, format ={}, pattern = DATA_TIME_PATTERN)// 避免Java的时间和ES的时间格式不一致
    private Date updateTime;


}
