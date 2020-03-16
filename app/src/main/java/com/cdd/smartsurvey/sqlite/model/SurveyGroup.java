package com.cdd.smartsurvey.sqlite.model;

public class SurveyGroup {

    public static final String TABLE_NAME = "tbl_survey_group_master";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GROUP_NO = "group_no";
    public static final String COLUMN_GROUP_NAME = "group_name";
    public static final String COLUMN_GROUP_DISPLAY = "group_display";
    public static final String COLUMN_GROUP_ORDERBY = "group_orderby";
    public static final String COLUMN_GROUP_IMAGE = "group_image";
    public static final String COLUMN_GROUP_METRIC = "group_metric";


    private int id;
    private String group_no;
    private String group_name;
    private String group_display;
    private String group_orderby;
    private String group_image;
    private String group_metric;

    public SurveyGroup() {
    }

    public SurveyGroup(int id, String group_no,String group_name,String group_display, String group_orderby, String group_image, String group_metric) {
        this.id = id;
        this.group_no = group_no;
        this.group_name = group_name;
        this.group_display = group_display;
        this.group_orderby = group_orderby;
        this.group_display = group_display;
        this.group_metric  = group_metric;
    }

    public int getId() {
        return id;
    }

    public String getGroupNo() {
        return group_no;
    }

    public String getGroupName() {
        return group_name;
    }

    public String getGroupDisplay() {
        return group_display;
    }

    public String getGroupOrderby() {
        return group_orderby;
    }

    public String getGroupImage() {
        return group_image;
    }

    public String getGroupMetric() {
        return group_metric;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroupNo(String group_no) {
        this.group_no = group_no;
    }

    public void setGroupName(String group_name) {
        this.group_name = group_name;
    }

    public void setGroupDisplay(String group_display) {
        this.group_display = group_display;
    }

    public void setGroupOrderby(String group_orderby) {
        this.group_orderby = group_orderby;
    }

    public void setGroupImage(String group_image) {
        this.group_image = group_image;
    }

    public void setGroupMetric(String group_metric) {
        this.group_metric = group_metric;
    }

}