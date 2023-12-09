package com.paper.utils;

import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocumentExtractor;
import org.dromara.pdf.pdfbox.handler.XEasyPdfHandler;

import java.util.ArrayList;
import java.util.List;

public class PdfUtil {

    public static List<String> extractText(String sourcePath){
        XEasyPdfDocument document = XEasyPdfHandler.Document.load(sourcePath);
        XEasyPdfDocumentExtractor extractor = document.extractor();
        // 提取文本
        List<String> textList = new ArrayList<>();
        extractor.extractText(textList);
        document.close();
        return textList;
    }
}
