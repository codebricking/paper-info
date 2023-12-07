package com.paper.dao.impl;

import com.paper.dao.PaperInfoDao;
import com.paper.mapper.PaperInfoMapper;
import com.paper.model.entity.PaperInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class PaperDaoImpl implements PaperInfoDao {
    @Resource
    private PaperInfoMapper paperInfoMapper;

    public int insert(PaperInfo paperInfo) {
        return paperInfoMapper.insert(paperInfo);
    }


    @Override
    public List<PaperInfo> getLatestPaperInfo(int pageNo, int pageSize) {
        int start = (pageNo - 1) * pageSize;
        return paperInfoMapper.getLatestPaperInfo(start, pageSize);
    }

    @Override
    public List<PaperInfo> searchPaperInfo(String searchText, int pageNum, int pageSize) {
        int start = Math.max(0, (pageNum - 1) * pageSize);
        return paperInfoMapper.searchPaperInfo(searchText, start, pageSize);
    }

    @Override
    public int savePaperInfo(List<PaperInfo> paperInfos) {
        if (paperInfos == null || paperInfos.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (PaperInfo paperInfo : paperInfos) {
            Integer integer = paperInfoMapper.existsIgnoreCase(paperInfo.getTitle());
            if ( integer !=null && integer > 0) {
                continue;
            }
            count += paperInfoMapper.insert(paperInfo);
        }

        return count;
    }

    @Override
    public int savePaperInfo(PaperInfo paperInfo) {
        if (paperInfo == null) {
            return 0;
        }
        return paperInfoMapper.insert(paperInfo);
    }

    @Override
    public List<PaperInfo> getUnSummarised(int batchSize) {
        List<PaperInfo> unSummarised = paperInfoMapper.getUnSummarised(batchSize);
        if (unSummarised == null){
            return new LinkedList<>();
        }
        return unSummarised;
    }

    @Override
    public int updateByPrimaryKeySelective(PaperInfo paperInfo) {
        return paperInfoMapper.updateByPrimaryKeySelective(paperInfo);
    }

    @Override
    public List<PaperInfo> getValidPaperInfo() {
        return paperInfoMapper.getValidPaperInfo();
    }

    @Override
    public List<PaperInfo> getPaperInfoByTime(int seconds) {
        return paperInfoMapper.getByTimeSec(seconds);
    }

    @Override
    public List<PaperInfo> getUnSummarisedByAbstract(int batchSize) {
        return paperInfoMapper.getUnSummarisedByAbstract(batchSize);
    }

    @Override
    public int updateSummaryCnByKey(PaperInfo paperInfo) {
        if (paperInfo.getId() == null || paperInfo.getId() == 0 ||paperInfo.getSummaryCn() == null){
            return 0;
        }
        return paperInfoMapper.updateSummaryCnByKey(paperInfo);
    }

    @Override
    public int updateRemark(Integer id, String remark) {
        if (id == null ||id == 0 || StringUtils.isBlank(remark)){
            return 0;
        }
        return paperInfoMapper.updateRemark(id,remark);
    }

    @Override
    public List<PaperInfo> getUnTranslatedAbstract(int batchSize) {
        return paperInfoMapper.getUnTranslatedAbstract(batchSize);
    }

    @Override
    public int updateTitleCnByKey(PaperInfo paperInfo) {
        if (StringUtils.isBlank(paperInfo.getTitleCn() )|| paperInfo.getId() == null || paperInfo.getId() == 0 ){
            return 0;
        }
        return paperInfoMapper.updateTitleCnByKey(paperInfo);
    }

    @Override
    public List<PaperInfo> getUnTranslatedTitle(int batchSize) {
        return paperInfoMapper.getUnTranslatedTitle(batchSize);
    }

    @Override
    public int updateAbstractCnByKey(PaperInfo paperInfo) {
        if (StringUtils.isBlank(paperInfo.getSummaryCn() )|| paperInfo.getId() == null || paperInfo.getId() == 0 ){
            return 0;
        }
        return paperInfoMapper.updateAbstractCnByKey(paperInfo);
    }

    @Override
    public boolean exist(PaperInfo paperInfo) {
        Integer countTitle = paperInfoMapper.existsIgnoreCase(paperInfo.getTitle());
        Integer countId = paperInfoMapper.existId(paperInfo.getId());
        return countTitle > 0 || countId > 0;
    }
}
