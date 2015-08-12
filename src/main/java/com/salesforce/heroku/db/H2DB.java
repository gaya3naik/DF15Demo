package com.salesforce.heroku.db;

import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by gayathri on 12/8/15.
 */
public class H2DB {
    public static void main(String... args) throws Exception {

        DeleteDbFiles.execute("~", "analysisdata", true); // delete and then do the below

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/analysisdata");
        Statement stat = conn.createStatement();

        // this line would initialize the database
        // from the SQL script file 'init.sql'
        // stat.execute("runscript from 'init.sql'");

        stat.execute("create table analysisdata(id int primary key, pageviews_c double, sessioncount_c double, date_c date)");
        stat.execute("insert into analysisdata values(1, 120, 121, {ts '2012-09-17 18:47:52.69'})");
     //   stat.execute("insert into analysis-data values(2, 'Gayathri')");
        ResultSet rs;
        rs = stat.executeQuery("select * from analysisdata");
        while (rs.next()) {
            System.out.println(rs.getDouble("pageviews_c"));
        }
        stat.close();
        conn.close();
    }
}
