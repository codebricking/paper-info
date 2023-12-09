package com.paper.service;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.file.LineSeparator;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.paper.model.entity.TopConference;
import com.paper.utils.DownloadUtil;
import com.paper.utils.PdfUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class TopConferenceServiceTest {
    static String baseSaveDir = "E:\\temp\\paper\\topconference\\";
    @Autowired
    private TopConferenceService topConferenceService;

    /**
     *         int startYear = 2014;
     *         int endYear = 2022;
     */
    @Test
    public void downloadAndParseFullTextV1(){
        downloadAndParseFullText(2022);
    }

    @Test
    public void downloadAndParseFullTextV2(){
        downloadAndParseFullText(2021);
    }

    @Test
    public void downloadAndParseFullTextV3(){
        downloadAndParseFullText(2020);
    }

    @Test
    public void downloadAndParseFullTextV4(){
        downloadAndParseFullText(2019);
    }

    @Test
    public void downloadAndParseFullTextV5(){
        downloadAndParseFullText(2018);
    }
    @Test
    public void downloadAndParseFullTextV6(){
        downloadAndParseFullText(2017);
    }



    public void downloadAndParseFullText(int year){
        int count = 100;
        int batchSize = 10;

        List<String> conferenceNameList = Arrays.asList("CVPR", "AAAI", "AISTATS","JMLR","NIPS","ECCV","ACML","COLT","ICML","WACV","ICCV");

        while (count > 0){
            count--;
            int confIndex = count % conferenceNameList.size();
            String randomConference = conferenceNameList.get(confIndex);
            List<TopConference> noFullTextTopConference = topConferenceService.getNoFullTextTopConference(batchSize,year,randomConference);
            if (noFullTextTopConference.isEmpty()){
                continue;
            }
            for (TopConference topConference : noFullTextTopConference) {
                Integer conferenceYear = topConference.getConferenceYear();
                String conferenceName = topConference.getConferenceName();
                String newPath  =  baseSaveDir + conferenceYear + "\\" + conferenceName + "\\" ;
                boolean created = createFolderIfNotExits(newPath);

                //下载
                String pdfUrl = topConference.getPdfUrl();
                String title = topConference.getTitle();
                Integer id = topConference.getId();
                String fileName = id + "-" + modifyTitle(title) +".pdf";
                try {
                    DownloadUtil.downLoadByUrl(pdfUrl,fileName, newPath);
                }catch (Exception e){
                    System.out.println("fail to download" + fileName);
                    topConference.setFullText("fail to download");
                    topConferenceService.updateById(topConference);
                }
                try {
                    //解析
                    List<String> extractText = PdfUtil.extractText(newPath + fileName);
                    String fullText = String.join(" ", extractText);
                    // 回写
                    topConference.setFullText(fullText);
                    topConferenceService.updateById(topConference);
                }catch (Exception e){
                    topConference.setFullText("fail to parse");
                    topConferenceService.updateById(topConference);
                }
            }

        }
    }
    private String modifyTitle(String origin){
       return origin.replaceAll(":","-").replace("?","-").replace("\"","");
    }

    boolean createFolderIfNotExits(String folderPath){

        try {
            // 将路径字符串转换为Path对象
            Path path = Paths.get(folderPath);
            if (Files.exists(path)){
                return false;
            }

            // 使用Files.createDirectories()创建目录（包括所有不存在的父目录）
            Files.createDirectories(path);

            System.out.println("目录已成功创建: " + path);
            return true;
        } catch (IOException e) {
            // 处理创建目录时的异常
            System.err.println("创建目录时发生错误: " + e.getMessage());
            return false;
        }
    }
    //产生目录
    @Test
    public void generateList(){
        QueryWrapper<TopConference> qw = new QueryWrapper<>();
        List<TopConference> list = topConferenceService.list(qw);
        Map<Integer, Map<String, List<TopConference>>> result = list.stream()
                .collect(Collectors.groupingBy(TopConference::getConferenceYear,
                        Collectors.groupingBy(TopConference::getConferenceName)));

        for (Map.Entry<Integer, Map<String, List<TopConference>>> et : result.entrySet()) {
            Integer year = et.getKey();
            LinkedList<String> res = new LinkedList<>();

            for (Map.Entry<String, List<TopConference>> subEt : et.getValue().entrySet()) {
                String name = subEt.getKey();
                res.add("# " + name);
                int id = 1;
                for (TopConference topConference : subEt.getValue()) {
                    String line = id +  ". " + topConference.getTitle();
                    id ++;
                    if (topConference.getPdfUrl()!= null){
                        line += " [pdf](" + topConference.getPdfUrl() +")";
                    }
                    if (topConference.getArxivId()!=null){
                        line += " [arxiv](" + topConference.getArxivId() +")";
                    }
                    res.add(line);
                }
            }
            FileWriter writer = new FileWriter("D:\\temp\\paper\\top_conference_"+year+".md");
            writer.writeLines(res, LineSeparator.WINDOWS,true);
        }
    }

    @Test
    public void genDownloadJson(){
        QueryWrapper<TopConference> qw = new QueryWrapper<>();
        qw.isNull("full_text");
        qw.last("limit 1000");
        List<TopConference> list = topConferenceService.list(qw);
        FileWriter writer = new FileWriter("D:\\temp\\paper\\test.txt");

        for (TopConference topConference : list) {
            JSONObject obj = JSONUtil.createObj();
            obj.putIfAbsent("uri", topConference.getPdfUrl());
            obj.putIfAbsent("file_name",topConference.getId() + ".pdf");
            writer.write(obj.toString() + "\n", true);
        }
    }




}