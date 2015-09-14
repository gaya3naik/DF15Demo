# Understanding Complex Data Trends in Salesforce1 Objects Using Heroku

This repository demonstrates a sample application which integrates Salesforce with Heroku using H2DB as in memory database for complex data trend analysis.

Abstract : Have you ever faced a problem while computing complex trends from huge volumes of data?
Join us as we explore Time Series analysis of Salesforce1 data using Heroku.
As an example, we will demonstrate how to compare measures across two different time ranges.
This session will start with basics of time series analysis and demonstrate how to go beyond the limitations of Salesforce1 workbench by writing your own Heroku app.


Features :
This sample application helps in visualizing time series graphs in the following ways.

1) Measures across Single Time Dimension
2) % Growth of Resolution Time / Reopen Count( Measures of a CaseAnalytics custom object)
3) Surface Graph ( Time Series Graph across multiple dimensions)

Heroku App uses H2 database for in memory joins and complex calculations on the data returned by Salesforce REST APIs.
This data is drawn using Timeline graphs using HighCharts.js library.


Pre-requisites

Salesforce.com Developer Account
Heroku Account
Java 7 SE Development Kit (Preferably Oracle Java)
Maven 3+

NOTE : This is a sample app developed to present at Dreamforce this year! For More Details -[Click here!](https://success.salesforce.com/apex/Ev_Sessions?eventId=a1Q30000000DHQlEAO#/session/a2q300000019BFyAAM)


