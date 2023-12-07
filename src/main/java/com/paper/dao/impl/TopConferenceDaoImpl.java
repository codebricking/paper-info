package com.paper.dao.impl;

import com.paper.dao.TopConferenceDao;
import com.paper.mapper.TopConferenceMapper;
import com.paper.model.entity.TopConference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class TopConferenceDaoImpl implements TopConferenceDao {
    @Resource
    private TopConferenceMapper topConferenceMapper;
    @Override
    public List<TopConference> getUnConvTopConference(int batchSize){
        List<TopConference> unConvTopConference = topConferenceMapper.getUnConvTopConference(batchSize);
        if (unConvTopConference == null){
            return new LinkedList<>();
        }
        return unConvTopConference;
    }
}
