package com.paper.dao;

import com.paper.model.entity.ArxivCategory;
import com.paper.model.entity.ArxivPaper;
import com.paper.model.entity.ArxivPaperDO;

import java.util.List;

public interface ArxivPaperDao {

    List<ArxivCategory> listArxivCategory();

    int saveRecord(List<ArxivPaper> arxivPapers);

    List<ArxivPaperDO> listArxivPaper();

    List<ArxivPaperDO> listArxivPaper(int pageNum, int pageSize);

    List<ArxivPaperDO> listArxivNotTranslatedPaper(int num);
}
