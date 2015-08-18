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
        try {
            String analysisType = request.getAnalysisType();
            Map<String, Object> sfResult;

            if("MD".equalsIgnoreCase(analysisType)){
                String query = "SELECT Category__c, Rainfall__c, Temperature__c FROM WeatherInfo__c ORDER BY CreatedDate ASC NULLS FIRST";

                sfResult = SalesforceQueryRunner.query(query, request.getUrl(), request.getSessionId());
                records = (List<Map<String, Object>>) sfResult.get("records");
            }
            else if("AT".equalsIgnoreCase(analysisType)){
                String query = "SELECT Id,Name,PageViews__c,Revenue__c,SessionCount__c FROM EmployeeInfo__c";

                sfResult = SalesforceQueryRunner.query(query, request.getUrl(), request.getSessionId());
                records = (List<Map<String, Object>>) sfResult.get("records");
            }
            else {
                String query = "SELECT Id,PageViews__c,SessionCount__c, Date__c FROM AnalyticsData__c";

                sfResult = SalesforceQueryRunner.query(query, request.getUrl(), request.getSessionId());
                records = (List<Map<String, Object>>) sfResult.get("records");
                if (analysisType == null || "MM".equalsIgnoreCase(analysisType)) {
                    return records;
                } else {
                    return TrendAnalysisServiceImpl.getAnalyzedTrendData(analysisType, records);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

}
