package com.paper.crawler.sg;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.paper.common.ErrorCode;
import com.paper.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SgPrAppCrawler {
    static String baseUrl = "http://sgprapp.com/listPage?&page=";
    int maxPage = 5;
    public void getAllPrInfo(){
        List<Map<String,String>> res = new LinkedList<>();
        for (int pageNum = 0; pageNum < maxPage; pageNum++) {
            String url = baseUrl + pageNum;
            Document pageDoc = null;
            try {
                pageDoc = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
            }
            Element tbody = pageDoc.select("tbody").first();
//            System.out.println(tbody);
            Elements trElements = tbody.select("tr");
            for (Element trElement : trElements) {
                Elements tds = trElement.select("td");
                System.out.print(tds.get(1).text()+"\t");
                System.out.print(tds.get(2).text()+"\t");
                System.out.print(tds.get(3).text()+"\t");
                System.out.print(tds.get(4).text()+"\t");
                System.out.println(tds.get(5).text()+"\t");
                HashMap<String, String> map = new HashMap<>();
                map.put("用户名",tds.get(1).text());
                map.put("条件",tds.get(2).text());
                map.put("结果",tds.get(3).text());
                map.put("申请日期",tds.get(4).text());
                map.put("结束日期",tds.get(5).text());
                map.put("更新时间",tds.get(6).text());
                res.add(map);
            }

        }
        exportToExcel(res,"data-2023-7-24");


    }

    private static void exportToExcel(List<Map<String, String>> dataList, String fileName) {
        // 创建ExcelWriter对象
        ExcelWriter writer = ExcelUtil.getWriter(FileUtil.file(fileName));

        // 写入数据
        writer.write(dataList, true);

        // 关闭ExcelWriter对象，保存文件
        writer.close();
    }
}
