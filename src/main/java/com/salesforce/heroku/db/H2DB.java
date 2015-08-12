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
//    public static void main(String... args) throws Exception {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//        String dateInString = "7-Jun-2013";
//        Date date = formatter.parse(dateInString);
//        Timestamp timestamp = new Timestamp(date.getTime());//instead of date put your converted date
//        Timestamp myTimeStamp= timestamp;
//        DeleteDbFiles.execute("~", "#TestDW", true); // delete and then do the below
//
//        Class.forName("org.h2.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:h2:~/#TestDW");
//        Statement stat = conn.createStatement();
//
//        // this line would initialize the database
//        // from the SQL script file 'init.sql'
//        // stat.execute("runscript from 'init.sql'");
//
//        stat.execute("CREATE TABLE #TestDW\n" +
//                "( Date1 Date,\n" +
//                "  LoadValue Numeric(13,6)\n" +
//                ")");
//
//
//        stat.execute("INSERT INTO #TestDW VALUES({ts '2012-06-09 18:47:52.69'} , '3.540' )");
//        stat.execute("INSERT INTO #TestDW VALUES({ts '2012-06-08 18:47:52.69'} , '2.260' )");
//        stat.execute("INSERT INTO #TestDW VALUES({ts '2012-06-07 18:47:52.69'} , '1.330' )");
//        stat.execute("INSERT INTO #TestDW VALUES({ts '2012-06-06 18:47:52.69'} , '5.520' )");
//        System.out.print(" Data is inserted");
//        ResultSet rs = stat.executeQuery("SELECT S1.date1,  AVG(S2.LoadValue) AS avg_prev_3_days\n" +
//                        "FROM #TestDW AS S1, #TestDW AS S2\n" +
//                        "WHERE S2.date1\n" +
//                        "    BETWEEN DATEADD('DAY', -2, S1.date1 )\n" +
//                        "    AND S1.date1\n" +
//                        "GROUP BY S1.date1\n" +
//                        "order by 1");
//        System.out.print("result " + rs);
//        while (rs.next()) {
//            System.out.println(rs.getDouble("avg_prev_3_days"));
//        }
//        while (rs.next()) {
//            System.out.println(rs.getInt("avg_prev_3_days"));
//        }
//        stat.close();
//        conn.close();
//    }

    public static List<Map<String, Object>> getMovingAverages(List<Map<String, Object>> records) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-DD");
        Date date;
        Timestamp timestamp;
        DeleteDbFiles.execute("~", "#AnalyticsData1", true); // delete and then do the below

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/#AnalyticsData1");
        Statement stat = conn.createStatement();

        stat.execute("CREATE TABLE #AnalyticsData1\n" +
                "( Date Date,\n" +
                "  PageViews Numeric(13,6),\n" +
                "  SessionCount Numeric(13,6) \n" +
                ")");

        for(Map<String, Object> record : records){
            date = formatter.parse(String.valueOf(record.get("Date__c")));
            timestamp = new Timestamp(date.getTime());
             stat.execute("INSERT INTO #AnalyticsData VALUES({ts '"+ timestamp+ "'} ,'" +record.get("PageViews__c")+"')" );
        }

        System.out.print(" Data is inserted");
        ResultSet rs = stat.executeQuery("SELECT S1.date as date,  AVG(S2.PageViews) AS avg_pv\n" +
                "FROM #AnalyticsData1 AS S1, #AnalyticsData1 AS S2\n" +
                "WHERE S2.date\n" +
                "    BETWEEN DATEADD('DAY', -2, S1.date )\n" +
                "    AND S1.date\n" +
                "GROUP BY S1.date\n" +
                "order by 1");
        System.out.print("result " + rs);
        List<Map<String, Object>> resultList= Lists.newArrayList();

        while (rs.next()) {
            System.out.println(rs.getDouble("avg_prev_3_days"));
        }
        while (rs.next()) {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("Date__c", rs.getDate("date"));
            resultMap.put("PageViews__c", rs.getDouble("avg_pv"));
            System.out.println(rs.getInt("avg_prev_3_days"));
            resultList.add(resultMap);
        }
        stat.close();
        conn.close();
        return resultList;
    }
}
