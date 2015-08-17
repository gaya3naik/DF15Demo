package com.salesforce.heroku.service;

import com.salesforce.heroku.db.H2DB;

import java.util.List;
import java.util.Map;

/**
 * Created by gayathri on 12/8/15.
 */
public class TrendAnalysisServiceImpl {

    public static List<Map<String, Object>> getAnalyzedTrendData(String analysisType, List<Map<String, Object>>  records) throws Exception {

        if("MA".equalsIgnoreCase(analysisType)){
            return H2DB.getMovingAverages(records);
        }
        return null;
    }
}
