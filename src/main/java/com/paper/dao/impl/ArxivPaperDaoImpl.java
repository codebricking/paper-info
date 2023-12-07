package com.paper.dao.impl;

import com.paper.dao.ArxivPaperDao;
import com.paper.mapper.ArxivCategoryMapper;
import com.paper.mapper.ArxivPaperMapper;
import com.paper.model.entity.ArxivCategory;
import com.paper.model.entity.ArxivPaper;
import com.paper.model.entity.ArxivPaperDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArxivPaperDaoImpl implements ArxivPaperDao {

    @Resource
    private ArxivCategoryMapper arxivCategoryMapper;
    @Resource
    private ArxivPaperMapper arxivPaperMapper;

    @Override
    public List<ArxivCategory> listArxivCategory(){
        return arxivCategoryMapper.listArxivCategory();
    }

    @Override
    public int saveRecord(List<ArxivPaper> arxivPapers) {
        List<ArxivPaperDO> records = arxivPapers.stream().map(a -> a.convertToArxivPaperDO()).collect(Collectors.toList());
        System.out.println(records.size());
        return arxivPaperMapper.insertArxivPapers(records);

    }

    @Override
    public List<ArxivPaperDO> listArxivPaper() {
        return arxivPaperMapper.listAll();

    }

    @Override
    public List<ArxivPaperDO> listArxivPaper(int pageNum, int pageSize) {
        int start = Math.max(0,(pageNum-1)*pageSize);
        return arxivPaperMapper.listPage(start, pageSize);

    }

    @Override
    public List<ArxivPaperDO> listArxivNotTranslatedPaper(int num) {
       return arxivPaperMapper.listArxivNotTranslatedPaper(num);

    }
}
