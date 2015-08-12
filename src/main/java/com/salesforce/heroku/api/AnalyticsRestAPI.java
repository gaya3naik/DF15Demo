package com.salesforce.heroku.api;

import com.dreamforceDemo.utils.SalesforceQueryRunner;
import com.google.common.collect.Lists;
import com.salesforce.heroku.bean.Request;
import com.salesforce.heroku.service.TrendAnalysisServiceImpl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by gayathri on 11/8/15.
 */

@Path("trends")
public class AnalyticsRestAPI {

    @POST
    @Path("/analysis")
    public List<Map<String, Object>> fetchData(Request request){
        List records = Lists.newArrayList();
                String query = "SELECT Id,PageViews__c,SessionCount__c, Date__c FROM AnalyticsData__c";
        try {

            String analysisType = request.getAnalysisType();
            Map<String, Object> sfResult = SalesforceQueryRunner.query(query, request.getUrl(), request.getSessionId());
            records = (List<Map<String, Object>>) sfResult.get("records");
            if(analysisType == null || "MM".equalsIgnoreCase(analysisType)) {
                return records;
            }
            else{
                 return TrendAnalysisServiceImpl.getAnalyzedTrendData(analysisType);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

}
