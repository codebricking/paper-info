package com.paper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paper.model.entity.PaperInfo;
import com.paper.model.entity.TopConference;

import java.util.List;

/**
* @author 24102
* @description 针对表【top_conference(抓取的计算机视觉顶会文章信息)】的数据库操作Service
* @createDate 2023-06-03 21:19:49
*/
public interface TopConferenceService extends IService<TopConference> {

    boolean saveTopConference(TopConference topConference);

    boolean existsIgnoreCase(TopConference topConference);

    int saveTopConferenceDOBatch(List<TopConference> topConferenceList);

    List<PaperInfo> convertTopConferenceToPaperInfo(int batchSize);

    List<TopConference> getNoFullTextTopConference(int batchSize, int conferenceYear);
}
