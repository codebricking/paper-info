package com.paper.utils;

import com.paper.model.entity.ArxivPaper;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Slf4j
public class ArxivParser {
    public static LinkedList<ArxivPaper> parse(String response){
        LinkedList<ArxivPaper> arxivPapers = new LinkedList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获取解析器
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //使用解析器生成Document实例
            Document document = documentBuilder.parse(new InputSource(new StringReader(response)));
            //entry
            NodeList entries = document.getElementsByTagName("entry");

            //遍历该节点列表
            for (int entryIndex=0; entryIndex< entries.getLength();entryIndex++){
                ArxivPaper arxivPaper = new ArxivPaper();
                //获取具体的元素
                org.w3c.dom.Element node = (org.w3c.dom.Element) entries.item(entryIndex);
                //输出值
                String id = getFirstContentOrDefault(node.getElementsByTagName("id"));

                String publishedStr = getFirstContentOrDefault(node.getElementsByTagName("published"));

                String updatedStr = getFirstContentOrDefault(node.getElementsByTagName("updated"));


                String title =getFirstContentOrDefault(node.getElementsByTagName("title"));


                String summary =getFirstContentOrDefault(node.getElementsByTagName("summary")) ;
                summary = summary.replaceAll("\n", " ");
//
                String primaryCategory = getAttributeOrDefault(node.getElementsByTagName("arxiv:primary_category"),"arxiv:primary_category") ;

                LinkedList<String> authors = new LinkedList<>();

                NodeList authorNodeList = node.getElementsByTagName("author");
                for (int authorIndex=0; authorIndex< authorNodeList.getLength();authorIndex++){
                    try {
                        String nameRemoveLineBreak = authorNodeList.item(authorIndex).getTextContent().replaceAll("\n ", "");
                        String name = nameRemoveLineBreak.trim().replaceAll("\\s{2,}", " ");
                        authors.add(name);
                    }catch (Exception e){
                    }
                }

                String comment = getFirstContentOrDefault(node.getElementsByTagName("arxiv:comment"));
                NodeList linkNodes = node.getElementsByTagName("link");

                String journalRef = null;
                String pdfUrl = null;
                for (int linkIndex = 0; linkIndex < linkNodes.getLength(); linkIndex++) {
                    try {
                        org.w3c.dom.Element  linkElement = (org.w3c.dom.Element) linkNodes.item(linkIndex);
                        String rel = linkElement.getAttribute("rel");
                        String href = linkElement.getAttribute("href");
                        if (rel.equals("alternate")) {
                            journalRef = href;
                        } else if (rel.equals("related") && linkElement.getAttribute("title").equals("pdf")) {
                            pdfUrl = href;
                        }
                    }catch (Exception e){

                    }
                }

                NodeList categoryNodes = node.getElementsByTagName("category");
                List<String> categories = new ArrayList<>();
                for (int catIndex = 0; catIndex < categoryNodes.getLength(); catIndex++) {
                    try {
                        org.w3c.dom.Element categoryElement = (org.w3c.dom.Element) categoryNodes.item(catIndex);
                        categories.add(categoryElement.getAttribute("term"));
                    }catch (Exception e){

                    }
                }



                Date publishedDate =null ;
                Date updated = null ;
                try {
                    publishedDate= Date.from(Instant.parse(publishedStr));
                    updated = Date.from(Instant.parse(updatedStr));
                }catch (Exception e){

                }

                arxivPaper.setArxivId(id);
                arxivPaper.setUpdated(updated);
                arxivPaper.setPublished(publishedDate);
                arxivPaper.setTitle(title);
                arxivPaper.setSummary(summary);
                arxivPaper.setAuthors(authors);
                arxivPaper.setComment(comment);
                arxivPaper.setJournalRef(journalRef);
                arxivPaper.setPrimaryCategory(primaryCategory);
                arxivPaper.setCategories(categories);
                arxivPaper.setPdfUrl(pdfUrl);
                arxivPapers.add(arxivPaper);
            }

        }catch (Exception e){
            log.info("fail to parse data = {}, error = [{}]",response, e);
        }
       return arxivPapers;
    }
    public static String getFirstContentOrDefault(NodeList nodeList, String defaultValue){
        try {
            return nodeList.item(0).getTextContent();
        }catch (Exception e){
            return defaultValue;
        }
    }
    public static String getFirstContentOrDefault(NodeList nodeList){
        return getFirstContentOrDefault(nodeList,"");
    }
    public static String getAttributeOrDefault(NodeList nodeList, String attribute){
        return getAttributeOrDefault(nodeList,attribute,"");
    }
    public static String getAttributeOrDefault(NodeList nodeList, String attribute, String defaultValue){
        try {
            Element item = (Element) nodeList.item(0);
            return item.getAttribute(attribute);
        }catch (Exception e){
            return defaultValue;
        }
    }
}
