package com.cdd.smartsurvey.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cdd.smartsurvey.sqlite.model.Amphur;
import com.cdd.smartsurvey.sqlite.model.Community;
import com.cdd.smartsurvey.sqlite.model.Gender;
import com.cdd.smartsurvey.sqlite.model.Prefix;
import com.cdd.smartsurvey.sqlite.model.Province;
import com.cdd.smartsurvey.sqlite.model.SurveyGroup;
import com.cdd.smartsurvey.sqlite.model.SurveyMetric;
import com.cdd.smartsurvey.sqlite.model.Tumbon;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "smartsurvey_master.db";

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<SurveyGroup> getAllSurveyGroups() {
        List<SurveyGroup> surveyGroups = new ArrayList<>();

        String seletQuery = "SELECT * FROM " + SurveyGroup.TABLE_NAME + " ORDER BY " +
                SurveyGroup.COLUMN_GROUP_ORDERBY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(seletQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SurveyGroup surveyGroup = new SurveyGroup();
                surveyGroup.setId(cursor.getInt(cursor.getColumnIndex(SurveyGroup.COLUMN_ID)));
                surveyGroup.setGroupName(cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NAME)));
                surveyGroup.setGroupNo(cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NO)));
                surveyGroup.setGroupDisplay(cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_DISPLAY)));
                surveyGroup.setGroupOrderby(cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NO)));
                surveyGroup.setGroupImage(cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_IMAGE)));
                surveyGroup.setGroupMetric(cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_METRIC)));
                surveyGroups.add(surveyGroup);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return notes list
        return surveyGroups;
    }

    public List<SurveyMetric> getAllSurveyMetrics(String tbl_survey_groupid) {
        List<SurveyMetric> surveyMetrics = new ArrayList<>();

        String seletQuery = "SELECT * FROM " + SurveyMetric.TABLE_NAME + " WHERE " + SurveyMetric.COLUMN_TBL_SURVEY_GROUP_MASTERID + " = " +
                tbl_survey_groupid + " ORDER BY " + SurveyMetric.COLUMN_METRIC_ORDERBY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(seletQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SurveyMetric surveyMetric = new SurveyMetric();
                surveyMetric.setId(cursor.getInt(cursor.getColumnIndex(SurveyMetric.COLUMN_ID)));
                surveyMetric.setTbl_survey_group_masterid(cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_TBL_SURVEY_GROUP_MASTERID)));
                surveyMetric.setMetric_no(cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_NO)));
                surveyMetric.setMetric_name(cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_NAME)));
                surveyMetric.setMetric_display(cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_DISPLAY)));
                surveyMetric.setMetric_description(cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_DESCRIPTION)));
                surveyMetric.setMetric_orderby(cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_ORDERBY)));
                surveyMetrics.add(surveyMetric);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return notes list
        return surveyMetrics;
    }


    public List<Prefix> getAllPrefixs() {
        List<Prefix> prefixs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Prefix.TABLE_NAME + " ORDER BY " +
                Prefix.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Prefix prefix = new Prefix();
                prefix.setId(cursor.getInt(cursor.getColumnIndex(Prefix.COLUMN_ID)));
                prefix.setCode(cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CODE)));
                prefix.setCodename(cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CODENAME)));
                prefix.setCreate_by(cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CREATE_BY)));
                prefix.setCreate_date(cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CREATE_DATE)));
                prefix.setModify_by(cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_MODIFY_BY)));
                prefix.setModify_date(cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_MODIFY_DATE)));

                prefixs.add(prefix);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return prefixs;
    }

    public List<Gender> getAllGenders() {
        List<Gender> genders = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Gender.TABLE_NAME + " ORDER BY " +
                Gender.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Gender gender = new Gender();
                gender.setId(cursor.getInt(cursor.getColumnIndex(Gender.COLUMN_ID)));
                gender.setCode(cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CODE)));
                gender.setCodename(cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CODENAME)));
                gender.setCreate_by(cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CREATE_BY)));
                gender.setCreate_date(cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CREATE_DATE)));
                gender.setModify_by(cursor.getString(cursor.getColumnIndex(Gender.COLUMN_MODIFY_BY)));
                gender.setModify_date(cursor.getString(cursor.getColumnIndex(Gender.COLUMN_MODIFY_DATE)));

                genders.add(gender);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return genders;
    }

    public List<Province> getAllProvinces() {
        List<Province> provinces = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Province.TABLE_NAME + " ORDER BY " +
                Province.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex(Province.COLUMN_ID)));
                province.setCode(cursor.getString(cursor.getColumnIndex(Province.COLUMN_CODE)));
                province.setCodename(cursor.getString(cursor.getColumnIndex(Province.COLUMN_CODENAME)));
                province.setCreate_by(cursor.getString(cursor.getColumnIndex(Province.COLUMN_CREATE_BY)));
                province.setCreate_date(cursor.getString(cursor.getColumnIndex(Province.COLUMN_CREATE_DATE)));
                province.setModify_by(cursor.getString(cursor.getColumnIndex(Province.COLUMN_MODIFY_BY)));
                province.setModify_date(cursor.getString(cursor.getColumnIndex(Province.COLUMN_MODIFY_DATE)));

                provinces.add(province);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return provinces;
    }

    public List<Amphur> getAllAmphurs(String keyValue) {
        List<Amphur> amphurs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Amphur.TABLE_NAME + " WHERE substr(" + Amphur.COLUMN_CODE + ",1,2) = " + "'" + keyValue + "'" + " ORDER BY " +
                Amphur.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Amphur amphur = new Amphur();
                amphur.setId(cursor.getInt(cursor.getColumnIndex(amphur.COLUMN_ID)));
                amphur.setCode(cursor.getString(cursor.getColumnIndex(amphur.COLUMN_CODE)));
                amphur.setCodename(cursor.getString(cursor.getColumnIndex(amphur.COLUMN_CODENAME)));
                amphur.setCreate_by(cursor.getString(cursor.getColumnIndex(amphur.COLUMN_CREATE_BY)));
                amphur.setCreate_date(cursor.getString(cursor.getColumnIndex(amphur.COLUMN_CREATE_DATE)));
                amphur.setModify_by(cursor.getString(cursor.getColumnIndex(amphur.COLUMN_MODIFY_BY)));
                amphur.setModify_date(cursor.getString(cursor.getColumnIndex(amphur.COLUMN_MODIFY_DATE)));

                amphurs.add(amphur);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return amphurs;
    }

    public List<Tumbon> getAllTumbons(String keyValue) {
        List<Tumbon> tumbons = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Tumbon.TABLE_NAME + " WHERE substr(" + Tumbon.COLUMN_CODE + ",1,4) = " + "'" + keyValue + "'" + " ORDER BY " +
                Tumbon.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tumbon tumbon = new Tumbon();
                tumbon.setId(cursor.getInt(cursor.getColumnIndex(tumbon.COLUMN_ID)));
                tumbon.setCode(cursor.getString(cursor.getColumnIndex(tumbon.COLUMN_CODE)));
                tumbon.setCodename(cursor.getString(cursor.getColumnIndex(tumbon.COLUMN_CODENAME)));
                tumbon.setCreate_by(cursor.getString(cursor.getColumnIndex(tumbon.COLUMN_CREATE_BY)));
                tumbon.setCreate_date(cursor.getString(cursor.getColumnIndex(tumbon.COLUMN_CREATE_DATE)));
                tumbon.setModify_by(cursor.getString(cursor.getColumnIndex(tumbon.COLUMN_MODIFY_BY)));
                tumbon.setModify_date(cursor.getString(cursor.getColumnIndex(tumbon.COLUMN_MODIFY_DATE)));

                tumbons.add(tumbon);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return tumbons;
    }

    public List<Community> getAllCommunitys(String keyValue) {
        List<Community> communitys = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Community.TABLE_NAME + " WHERE substr(" + Community.COLUMN_CODE + ",1,6) = " + "'" + keyValue + "'" + " ORDER BY " +
                Community.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Community community = new Community();
                community.setId(cursor.getInt(cursor.getColumnIndex(community.COLUMN_ID)));
                community.setCode(cursor.getString(cursor.getColumnIndex(community.COLUMN_CODE)));
                community.setMoo(cursor.getString(cursor.getColumnIndex(community.COLUMN_MOO)));
                community.setCodename(cursor.getString(cursor.getColumnIndex(community.COLUMN_CODENAME)));
                community.setCreate_by(cursor.getString(cursor.getColumnIndex(community.COLUMN_CREATE_BY)));
                community.setCreate_date(cursor.getString(cursor.getColumnIndex(community.COLUMN_CREATE_DATE)));
                community.setModify_by(cursor.getString(cursor.getColumnIndex(community.COLUMN_MODIFY_BY)));
                community.setModify_date(cursor.getString(cursor.getColumnIndex(community.COLUMN_MODIFY_DATE)));

                communitys.add(community);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return communitys;
    }
}