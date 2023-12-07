package com.paper.xxlJobExecutor.utils;

import com.xxl.job.core.context.XxlJobHelper;

public class ParseUtils {
    public static int  parseInt(String origin){
        int res = 0;
        try {
            res = Integer.parseInt(origin);
        } catch (Exception e) {
            XxlJobHelper.log("fail to parse param :" + e.getMessage());
            boolean b = XxlJobHelper.handleFail("wrong parameters");
        }
        return res;
    }
}
