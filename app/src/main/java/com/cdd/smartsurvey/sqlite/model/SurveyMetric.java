package com.cdd.smartsurvey.sqlite.model;

public class SurveyMetric {
    public static final String TABLE_NAME = "tbl_survey_metric_master";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TBL_SURVEY_GROUP_MASTERID = "tbl_survey_group_masterid";
    public static final String COLUMN_METRIC_NO = "metric_no";
    public static final String COLUMN_METRIC_NAME = "metric_name";
    public static final String COLUMN_METRIC_DISPLAY = "metric_display";
    public static final String COLUMN_METRIC_DESCRIPTION = "metric_description";
    public static final String COLUMN_METRIC_ORDERBY = "metric_orderby";


    private int id;
    private String tbl_survey_group_masterid;
    private String metric_no;
    private String metric_name;
    private String metric_display;
    private String metric_description;
    private String metric_orderby;

    public SurveyMetric() {
    }

    public SurveyMetric(int id, String tbl_survey_group_masterid, String metric_no, String metric_name, String metric_display,String metric_description,String metric_orderby) {
        this.id = id;
        this.tbl_survey_group_masterid = tbl_survey_group_masterid;
        this.metric_no = metric_no;
        this.metric_name = metric_name;
        this.metric_display = metric_display;
        this.metric_description = metric_description;
        this.metric_orderby = metric_orderby;
    }

    public int getId() {
        return id;
    }

    public String getTbl_survey_group_masterid() {
        return tbl_survey_group_masterid;
    }

    public String getMetric_no() {
        return metric_no;
    }

    public String getMetric_name() {
        return metric_name;
    }

    public String getMetric_display() {
        return metric_display;
    }

    public String getMetric_description() {
        return metric_description;
    }

    public String getMetric_orderby() {
        return metric_orderby;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTbl_survey_group_masterid(String tbl_survey_groupid) {
        this.tbl_survey_group_masterid = tbl_survey_groupid;
    }

    public void setMetric_no(String metric_no) {
        this.metric_no = metric_no;
    }

    public void setMetric_name(String metric_name) {
        this.metric_name = metric_name;
    }

    public void setMetric_display(String metric_display) {
        this.metric_display = metric_display;
    }

    public void setMetric_description(String metric_description) {
        this.metric_description = metric_description;
    }

    public void setMetric_orderby(String metric_orderby) { this.metric_orderby = metric_orderby;
    }

}