package com.paper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paper.dao.TopConferenceDao;
import com.paper.mapper.TopConferenceMapper;
import com.paper.model.entity.PaperInfo;
import com.paper.model.entity.TopConference;
import com.paper.service.TopConferenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 24102
* @description 针对表【top_conference(抓取的计算机视觉顶会文章信息)】的数据库操作Service实现
* @createDate 2023-06-03 21:19:49
*/
@Service
public class TopConferenceServiceImpl extends ServiceImpl<TopConferenceMapper, TopConference>
    implements TopConferenceService {
    @Resource
    private TopConferenceDao topConferenceDao;
    @Override
    public boolean saveTopConference(TopConference topConference){
        Integer existsIgnoreCase = this.getBaseMapper().existsIgnoreCase(topConference.getTitle());
        if (existsIgnoreCase == null || existsIgnoreCase == 0){
            return save(topConference);
        }
        return false;
    }
    @Override
    public boolean existsIgnoreCase(TopConference topConference){
        Integer exists = this.getBaseMapper().existsIgnoreCase(topConference.getTitle());
        return exists!=null && exists>0;
    }
    @Override
    public int saveTopConferenceDOBatch(List<TopConference> topConferenceList){
        int count = 0;
        for (TopConference topConference : topConferenceList) {
            boolean b = saveTopConference(topConference);
            if (b){
                count ++;
            }
        }
        return count;
    }
    @Override
    public List<PaperInfo> convertTopConferenceToPaperInfo(int batchSize){
        List<TopConference> unConvTopConference = topConferenceDao.getUnConvTopConference(batchSize);
        if (unConvTopConference.isEmpty()){
            return new LinkedList<>();
        }
        List<PaperInfo> paperInfos = unConvTopConference.stream().map(this::paperInfoConvert).collect(Collectors.toList());
        return paperInfos;
    }
    @Override
    public List<TopConference> getNoFullTextTopConference(){
        QueryWrapper<TopConference> qw = new QueryWrapper<>();
        qw.isNull("full_text").last("limit 1");
        List<TopConference> list = list(qw);
        return list;
    }

    PaperInfo paperInfoConvert(TopConference topConference){
        PaperInfo paperInfo = new PaperInfo();
        paperInfo.setPaperUrl(topConference.getPaperUrl());
        paperInfo.setTitle(topConference.getTitle());
        paperInfo.setSummary(topConference.getPaperAbstract());
        paperInfo.setConferenceName(topConference.getConferenceName());
        paperInfo.setPdfUrl(topConference.getPdfUrl());
        paperInfo.setAuthors(topConference.getAuthors());
        paperInfo.setCreateTime(new Date());
        paperInfo.setUpdateTime(new Date());
        return paperInfo;
    }


}




