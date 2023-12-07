package com.paper.service;

import com.paper.model.entity.ArxivPaperDO;

import java.util.List;

public interface ArxivPaperService {
    List<ArxivPaperDO> getArxivPaperFromOfficialApi(int pageSize);

    List<ArxivPaperDO> listArxivPaper(int pageNum, int pageSize);


}
