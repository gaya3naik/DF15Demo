package com.salesforce.heroku.service;

import com.salesforce.heroku.db.H2DB;

import java.util.List;
import java.util.Map;

/**
 * Created by gayathri on 12/8/15.
 */
public class TrendAnalysisServiceImpl {

    public static List<Map<String, Object>> getAnalyzedTrendData(String analysisType, List<Map<String, Object>>  records) throws Exception {

        if("MA".equalsIgnoreCase(analysisType)) {
            return H2DB.getMovingAverages(records);
        }
        else if("AT".equalsIgnoreCase(analysisType)){
            return H2DB.getAdvancedTimeline(records);
        }
        else if("CA".equalsIgnoreCase(analysisType)){
            return H2DB.getMovingAveragesForCaseData(records);
        }
        return null;
    }
}
