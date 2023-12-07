package com.paper.dao;


import com.paper.model.entity.TopConference;

import java.util.List;

public interface TopConferenceDao {

    List<TopConference> getUnConvTopConference(int batchSize);
}
