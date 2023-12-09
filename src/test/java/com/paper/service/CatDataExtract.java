//package com.paper.service;

//
//import com.paper.common.ErrorCode;
//import com.paper.exception.BusinessException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.junit.jupiter.api.Test;
//import org.jsoup.nodes.Document;
//
//import java.io.IOException;
//
//public class CatDataExtract {
//
//    @Test
//    public void extractAllCat(){
//        String url = "https://arxiv.org/category_taxonomy";
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(url).get();
//        } catch (IOException e) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
//        }
//        Elements select = doc.select("h2, h4");
//        int cnt = 0;
//        for (Element element : select) {
//            cnt++;
//            if (cnt  < 87){
//                continue;
//            }
////
////            System.out.println(element.tagName());
////            System.out.println(element.text());
//            String text = element.text();
//            int i = text.indexOf(' ');
//            if (i == -1){
//                System.out.println(text);
//            }else {
//                String swd = text.substring(0,i);
//                String lwd = text.substring(i);
//                System.out.println(swd +"\t\t\t\t" + lwd);
//
//
//            }
//
//
//
//
//        }
//
//
//    }
//}
