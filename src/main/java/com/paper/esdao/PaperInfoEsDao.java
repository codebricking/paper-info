package com.paper.esdao;

import com.paper.model.dto.paper.PaperInfoEsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 文章 ES 操作
 * ElasticsearchRepository 提供了简单的查询，多用于变化不复杂的查询
 */
public interface PaperInfoEsDao extends ElasticsearchRepository<PaperInfoEsDTO,Integer> {
    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"title\": \"?0\"}}, " +
            "{\"match\": {\"titleCn\": \"?0\"}}, " +
            "{\"match\": {\"summary\": \"?0\"}}, " +
            "{\"match\": {\"summaryCn\": \"?0\"}}" +
            "]}}")
    Page<PaperInfoEsDTO> findByKeywordInFields(String keyword, Pageable pageable);
    List<PaperInfoEsDTO> findTopByUpdateTime();
    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"summary\": \"?1\"}}]}}")
    Page<PaperInfoEsDTO> findByTitleAndSummary(String title, String summary, Pageable pageable);
    PaperInfoEsDTO findFirstById(Integer id);
}
