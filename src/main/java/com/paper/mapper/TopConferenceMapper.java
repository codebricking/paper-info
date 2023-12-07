package com.paper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paper.model.entity.TopConference;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 24102
* @description 针对表【top_conference(抓取的计算机视觉顶会文章信息)】的数据库操作Mapper
* @createDate 2023-06-03 21:19:49
* @Entity generator.domain.TopConference
*/
public interface TopConferenceMapper extends BaseMapper<TopConference> {
    @Select("SELECT 1 FROM top_conference WHERE LOWER(title) = LOWER(#{title}) LIMIT 1")
    Integer existsIgnoreCase(@Param("title") String title);

    List<TopConference> getUnConvTopConference(@Param("batchSize") int batchSize);

}




