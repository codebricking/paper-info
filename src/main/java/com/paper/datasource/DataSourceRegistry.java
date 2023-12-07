package com.paper.datasource;

import com.paper.model.enums.SearchTypeEnum;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegistry {
    @Resource
    private UserDataSource userDataSource;


    @Resource
    private PaperDataSource paperDataSource;

    private Map<String, DataSource> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        System.out.println("init data source registry");
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.PAPER.getValue(), paperDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
        }};
    }

    public DataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
