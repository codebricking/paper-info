package com.paper.pdf;

import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocumentExtractor;
import org.dromara.pdf.pdfbox.handler.XEasyPdfHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PdfReadTest {
    /**
     * this is the method without es and mysql running
     */
    @Test
    public void testRead(){
        String sourcePath = "D:\\temp\\arxiv\\pdf\\2202.10400v2.pdf";
        XEasyPdfDocument document = XEasyPdfHandler.Document.load(sourcePath);
        XEasyPdfDocumentExtractor extractor = document.extractor();
        // 提取文本
        List<String> textList = new ArrayList<>();
        extractor.extractText(textList);
        for (String text : textList) {
            System.out.println(text);
        }

    }
}
