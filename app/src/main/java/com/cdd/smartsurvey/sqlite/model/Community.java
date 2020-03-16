package com.cdd.smartsurvey.sqlite.model;

public class Community {

    public static final String TABLE_NAME = "tbl_community_master";

    public static final String COLUMN_ID          = "id";
    public static final String COLUMN_CODE        = "community_id";
    public static final String COLUMN_MOO         = "community_moo";
    public static final String COLUMN_CODENAME    = "community_name";
    public static final String COLUMN_CREATE_BY   = "create_by";
    public static final String COLUMN_CREATE_DATE = "create_date";
    public static final String COLUMN_MODIFY_BY   = "modify_by";
    public static final String COLUMN_MODIFY_DATE = "modify_date";

    private int id;
    private String code;
    private String moo;
    private String codename;
    private String create_by;
    private String create_date;
    private String modify_by;
    private String modify_date;

    public Community() {
    }

    public Community(int id, String code, String moo, String codename, String create_by, String create_date, String modify_by, String modify_date) {
        this.id = id;
        this.code = code;
        this.moo = moo;
        this.codename = codename;
        this.create_by = create_by;
        this.create_date = create_date;
        this.modify_by = modify_by;
        this.modify_date = modify_date;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getMoo() { return moo;}

    public String getCodename() {
        return codename;
    }

    public String getCreate_by() { return create_by; }

    public String getCreate_date() { return create_date; }

    public String getModify_by() { return modify_by; }

    public String getModify_date() { return modify_date; }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMoo(String moo) { this.moo = moo; }

    public void setCodename(String codename) { this.codename = codename; }

    public void setCreate_by(String create_by) { this.create_by = create_by; }

    public void setCreate_date(String create_date) { this.create_date = create_date; }

    public void setModify_by(String modify_by) { this.modify_by = modify_by; }

    public void setModify_date(String modify_date) { this.modify_date = modify_date; }

}
