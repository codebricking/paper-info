package com.paper.service.impl;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import com.paper.dao.ArxivPaperDao;
import com.paper.dao.PaperInfoDao;
import com.paper.model.entity.ArxivPaperDO;
import com.paper.model.entity.PaperInfo;
import com.paper.service.TranslateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class TranslateServiceImpl implements TranslateService {

    @Resource
    private ArxivPaperDao arxivPaperDao;
    @Resource
    private PaperInfoDao paperInfoDao;
    @Value("${translate.deepl.api.key:02xxxxxxxxxxx11:fx}")
    private String authKey;



    @Override
    public LinkedList<PaperInfo> translate(int maxNum) {
        List<ArxivPaperDO> arxivPaperDOs = arxivPaperDao.listArxivNotTranslatedPaper(maxNum);

        LinkedList<PaperInfo> res = new LinkedList<>();
        if (arxivPaperDOs.isEmpty()){
            return res;
        }
        for (ArxivPaperDO arxivPaperDO : arxivPaperDOs) {
            String title = arxivPaperDO.getTitle();
            String summary = arxivPaperDO.getSummary();
            String transTitle = null;
            String transSummary = null;
            try {
                if (StringUtils.isNotBlank(title)) {
                    transTitle = deepLTranslate(title.replaceAll("\n",""));
                }
                if (StringUtils.isNotBlank(summary)) {
                    transSummary = deepLTranslate(summary.replaceAll("\n",""));
                }
            } catch (Exception e) {
                continue;
            }
            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setPaperUrl(arxivPaperDO.getArxivId());
            paperInfo.setTitle(transTitle);
            paperInfo.setSummary(transSummary);
            paperInfo.setAuthors(arxivPaperDO.getAuthors());
            paperInfo.setPrimaryCategory(arxivPaperDO.getPrimaryCategory());
            paperInfo.setCategories(arxivPaperDO.getCategories());
            paperInfo.setPublished(arxivPaperDO.getPublished());
            res.add(paperInfo);
            paperInfoDao.savePaperInfo(paperInfo);
        }

        return res;
    }


    @Override
    public String deepLTranslate(String text) throws Exception {
        Translator translator = new Translator(authKey);
        TextResult result =
                translator.translateText(text, null, "ZH");// https://www.deepl.com/docs-api/translate-text/?utm_source=github&utm_medium=github-java-readme
        return result.getText();
    }

    // google translation api
    // refer to https://cloud.google.com/translate/docs/basic/translate-text-basic?hl=zh_cn#translate_translate_text-java
}
