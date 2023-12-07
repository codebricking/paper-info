package com.paper.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paper.model.entity.PaperInfo;
import com.paper.service.PaperInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 最新的文章/帖子服务实现
 *

 */
@Service
@Slf4j
public class PaperDataSource implements DataSource<PaperInfo> {

    @Resource
    private PaperInfoService paperInfoService;

    @Override
    public Page<PaperInfo> doSearch(String searchText, int pageNum, int pageSize) {
        List<PaperInfo> latestPaperInfo = paperInfoService.searchPaperInfo(searchText, pageNum, pageSize);
        Page<PaperInfo> paperInfoPage = new Page<>();
        return paperInfoPage.setRecords(latestPaperInfo);

    }
}




