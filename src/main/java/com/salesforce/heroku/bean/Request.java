package com.salesforce.heroku.bean;

/**
 * Created by gayathri on 11/8/15.
 */
public class Request {

    private String sessionId;
    private String url;
    private String analysisType;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "sessionId='" + sessionId + '\'' +
                ", url='" + url + '\'' +
                ", analysisType='" + analysisType + '\'' +
                '}';
    }
}
