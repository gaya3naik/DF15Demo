package com.salesforce.heroku.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.h2.tools.DeleteDbFiles;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gayathri on 12/8/15.
 */
public class H2DB {

   /* public static void main(String[] args) throws Exception {
        List records = Lists.newArrayList();


        Map<String, Object> record = Maps.newHashMap();
        record.put("Date__c", "2015-08-16");
        record.put("PageViews__c", 23);
        record.put("SessionCount__c", 45);
        records.add(record);

        record = Maps.newHashMap();
        record.put("Date__c", "2015-07-16");
        record.put("PageViews__c", 45);
        record.put("SessionCount__c", 90);
        records.add(record);

        record = Maps.newHashMap();
        record.put("Date__c", "2015-06-16");
        record.put("PageViews__c", 34);
        record.put("SessionCount__c", 58);
        records.add(record);

        getMovingAverages(records);
    }
*/

    public static List<Map<String, Object>> getMovingAverages(List<Map<String, Object>> records) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        Timestamp timestamp;
        DeleteDbFiles.execute("~", "AnalyticsData1", true); // delete and then do the below

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/AnalyticsData1");
        Statement stat = conn.createStatement();

        stat.execute("CREATE TABLE AnalyticsData1\n" +
                "( Date Date,\n" +
                "  PageViews Numeric(13,6),\n" +
                "  SessionCount Numeric(13,6) \n" +
                ")");

        for (Map<String, Object> record : records) {
            date = formatter.parse(String.valueOf(record.get("Date__c")));
            timestamp = new Timestamp(date.getTime());
            stat.execute("INSERT INTO AnalyticsData1 VALUES({ts '" + timestamp + "'} ,'" + record.get("PageViews__c") + "', '" + record.get("SessionCount__c") + "')");
        }

        System.out.print(" Data is inserted");
        ResultSet rs = stat.executeQuery("select data1.date, ((data1.pageviews-data2.pageviews)/data2.pageviews)*100 as growth_pageviews, ((data1.sessioncount-data2.sessioncount)/data2.sessioncount)*100 as growth_sessioncount  from analyticsdata1 data1 left outer join analyticsdata1 data2 on data2.date = DATEADD('MONTH', -1, data1.date)");
        System.out.println("result " + rs);
        List<Map<String, Object>> resultList = Lists.newArrayList();


        while (rs.next()) {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("Date__c", rs.getDate("date"));
            resultMap.put("PageViews__c", rs.getDouble("growth_pageviews"));
            resultMap.put("SessionCount__c", rs.getDouble("growth_sessioncount"));
            System.out.println(rs.getDouble("growth_pageviews"));
            System.out.println(rs.getDouble("growth_sessioncount"));
            resultList.add(resultMap);
        }
        stat.close();
        conn.close();
        return resultList;
    }


    public static List<Map<String, Object>> getAdvancedTimeline(List<Map<String, Object>> records) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        Timestamp timestamp;
        DeleteDbFiles.execute("~", "AnalyticsData2", true); // delete and then do the below

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/AnalyticsData2");
        Statement stat = conn.createStatement();

        stat.execute("CREATE TABLE AnalyticsData2\n" +
                "( Date Date,\n" +
                "  PageViews Numeric(13,6),\n" +
                "  SessionCount Numeric(13,6), \n" +
                "  Revenue Numeric(13,6), \n" +
                "  Name VARCHAR(255) \n" +
                ")");

        for (Map<String, Object> record : records) {
            date = formatter.parse(String.valueOf(record.get("Date__c")));
            timestamp = new Timestamp(date.getTime());
            stat.execute("INSERT INTO AnalyticsData2 VALUES({ts '" + timestamp + "'} ,'" + record.get("PageViews__c") + "', '" + record.get("SessionCount__c") + "','" + record.get("Revenue__c") + "','" + record.get("Name") + "')");
        }

        System.out.print(" Data is inserted");
        ResultSet rs = stat.executeQuery("select data1.date, data1.name, data1.revenue, ((data1.pageviews-data2.pageviews)/data2.pageviews)*100 as growth_pageviews, ((data1.sessioncount-data2.sessioncount)/data2.sessioncount)*100 as growth_sessioncount  from analyticsdata1 data1 left outer join analyticsdata1 data2 on data2.date = DATEADD('MONTH', -1, data1.date)");
        System.out.println("result " + rs);
        List<Map<String, Object>> resultList = Lists.newArrayList();


        while (rs.next()) {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("Date__c", rs.getDate("date"));
            resultMap.put("PageViews__c", rs.getDouble("growth_pageviews"));
            resultMap.put("SessionCount__c", rs.getDouble("growth_sessioncount"));
            resultMap.put("Revenue__c", rs.getDouble("name"));
            System.out.println(rs.getDouble("growth_pageviews"));
            System.out.println(rs.getDouble("growth_sessioncount"));
            resultList.add(resultMap);
        }
        stat.close();
        conn.close();
        return resultList;
    }

}
