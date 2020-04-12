package com.cdd.smartsurvey.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cdd.smartsurvey.sqlite.model.*
import java.util.*

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {}

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    // close db connection

    // return notes list
    val allSurveyGroups: List<SurveyGroup>
        get() {
            val surveyGroups: MutableList<SurveyGroup> = ArrayList()
            val seletQuery = "SELECT * FROM " + SurveyGroup.TABLE_NAME + " ORDER BY " +
                    SurveyGroup.COLUMN_GROUP_ORDERBY
            val db = this.writableDatabase
            val cursor = db.rawQuery(seletQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val surveyGroup = SurveyGroup()
                    surveyGroup.id = cursor.getInt(cursor.getColumnIndex(SurveyGroup.COLUMN_ID))
                    surveyGroup.groupName = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NAME))
                    surveyGroup.groupNo = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NO))
                    surveyGroup.groupDisplay = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_DISPLAY))
                    surveyGroup.groupOrderby = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NO))
                    surveyGroup.groupImage = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_IMAGE))
                    surveyGroup.groupMetric = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_METRIC))
                    surveyGroups.add(surveyGroup)
                } while (cursor.moveToNext())
            }
            // close db connection
            db.close()

            // return notes list
            return surveyGroups
        }

    fun getSurveyGroup(id: Int): SurveyGroup? {
        val seletQuery = "SELECT * FROM " + SurveyGroup.TABLE_NAME + " WHERE " + SurveyGroup.COLUMN_ID + " = " + id
        val db = this.readableDatabase
        val cursor = db.rawQuery(seletQuery, null)
        if (cursor.moveToFirst()) {
            val surveyGroup = SurveyGroup()
            surveyGroup.id = cursor.getInt(cursor.getColumnIndex(SurveyGroup.COLUMN_ID))
            surveyGroup.groupName = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NAME))
            surveyGroup.groupNo = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NO))
            surveyGroup.groupDisplay = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_DISPLAY))
            surveyGroup.groupOrderby = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_NO))
            surveyGroup.groupImage = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_IMAGE))
            surveyGroup.groupMetric = cursor.getString(cursor.getColumnIndex(SurveyGroup.COLUMN_GROUP_METRIC))
            return surveyGroup
        }
        return null
    }

    fun getAllSurveyMetrics(tbl_survey_groupid: String): List<SurveyMetric> {
        val surveyMetrics: MutableList<SurveyMetric> = ArrayList()
        val seletQuery = "SELECT * FROM " + SurveyMetric.TABLE_NAME + " WHERE " + SurveyMetric.COLUMN_TBL_SURVEY_GROUP_MASTERID + " = " +
                tbl_survey_groupid + " ORDER BY " + SurveyMetric.COLUMN_METRIC_ORDERBY
        val db = this.writableDatabase
        val cursor = db.rawQuery(seletQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val surveyMetric = SurveyMetric()
                surveyMetric.id = cursor.getInt(cursor.getColumnIndex(SurveyMetric.COLUMN_ID))
                surveyMetric.tbl_survey_group_masterid = cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_TBL_SURVEY_GROUP_MASTERID))
                surveyMetric.metric_no = cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_NO))
                surveyMetric.metric_name = cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_NAME))
                surveyMetric.metric_display = cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_DISPLAY))
                surveyMetric.metric_description = cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_DESCRIPTION))
                surveyMetric.metric_orderby = cursor.getString(cursor.getColumnIndex(SurveyMetric.COLUMN_METRIC_ORDERBY))
                surveyMetrics.add(surveyMetric)
            } while (cursor.moveToNext())
        }
        // close db connection
        db.close()

        // return notes list
        return surveyMetrics
    }

    // Select All Query
    val allPrefixs: List<Prefix>
        get() {
            val prefixs: MutableList<Prefix> = ArrayList()

            // Select All Query
            val selectQuery = "SELECT  * FROM " + Prefix.TABLE_NAME + " ORDER BY " +
                    Prefix.COLUMN_ID
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val prefix = Prefix()
                    prefix.id = cursor.getInt(cursor.getColumnIndex(Prefix.COLUMN_ID))
                    prefix.code = cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CODE))
                    prefix.codename = cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CODENAME))
                    prefix.create_by = cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CREATE_BY))
                    prefix.create_date = cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_CREATE_DATE))
                    prefix.modify_by = cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_MODIFY_BY))
                    prefix.modify_date = cursor.getString(cursor.getColumnIndex(Prefix.COLUMN_MODIFY_DATE))
                    prefixs.add(prefix)
                } while (cursor.moveToNext())
            }

            // close db connection
            db.close()

            // return notes list
            return prefixs
        }

    // Select All Query
    val allGenders: List<Gender>
        get() {
            val genders: MutableList<Gender> = ArrayList()

            // Select All Query
            val selectQuery = "SELECT  * FROM " + Gender.TABLE_NAME + " ORDER BY " +
                    Gender.COLUMN_ID
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val gender = Gender()
                    gender.id = cursor.getInt(cursor.getColumnIndex(Gender.COLUMN_ID))
                    gender.code = cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CODE))
                    gender.codename = cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CODENAME))
                    gender.create_by = cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CREATE_BY))
                    gender.create_date = cursor.getString(cursor.getColumnIndex(Gender.COLUMN_CREATE_DATE))
                    gender.modify_by = cursor.getString(cursor.getColumnIndex(Gender.COLUMN_MODIFY_BY))
                    gender.modify_date = cursor.getString(cursor.getColumnIndex(Gender.COLUMN_MODIFY_DATE))
                    genders.add(gender)
                } while (cursor.moveToNext())
            }

            // close db connection
            db.close()

            // return notes list
            return genders
        }

    // Select All Query
    val allProvinces: List<Province>
        get() {
            val provinces: MutableList<Province> = ArrayList()

            // Select All Query
            val selectQuery = "SELECT  * FROM " + Province.TABLE_NAME + " ORDER BY " +
                    Province.COLUMN_ID
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val province = Province()
                    province.id = cursor.getInt(cursor.getColumnIndex(Province.COLUMN_ID))
                    province.code = cursor.getString(cursor.getColumnIndex(Province.COLUMN_CODE))
                    province.codename = cursor.getString(cursor.getColumnIndex(Province.COLUMN_CODENAME))
                    province.create_by = cursor.getString(cursor.getColumnIndex(Province.COLUMN_CREATE_BY))
                    province.create_date = cursor.getString(cursor.getColumnIndex(Province.COLUMN_CREATE_DATE))
                    province.modify_by = cursor.getString(cursor.getColumnIndex(Province.COLUMN_MODIFY_BY))
                    province.modify_date = cursor.getString(cursor.getColumnIndex(Province.COLUMN_MODIFY_DATE))
                    provinces.add(province)
                } while (cursor.moveToNext())
            }

            // close db connection
            db.close()

            // return notes list
            return provinces
        }

    val allCareers: List<Career>
        get() {
            val careers: MutableList<Career> = ArrayList()

            // Select All Query
            val selectQuery = "SELECT  * FROM " + Career.TABLE_NAME + " ORDER BY " +
                    Career.COLUMN_ID
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val career = Career()
                    career.id = cursor.getInt(cursor.getColumnIndex(Career.COLUMN_ID))
                    career.code = cursor.getString(cursor.getColumnIndex(Career.COLUMN_CODE))
                    career.codename = cursor.getString(cursor.getColumnIndex(Career.COLUMN_CODENAME))
                    career.create_by = cursor.getString(cursor.getColumnIndex(Career.COLUMN_CREATE_BY))
                    career.create_date = cursor.getString(cursor.getColumnIndex(Career.COLUMN_CREATE_DATE))
                    career.modify_by = cursor.getString(cursor.getColumnIndex(Career.COLUMN_MODIFY_BY))
                    career.modify_date = cursor.getString(cursor.getColumnIndex(Career.COLUMN_MODIFY_DATE))
                    careers.add(career)
                } while (cursor.moveToNext())
            }

            // close db connection
            db.close()

            // return notes list
            return careers
        }

    val allEducations: List<Education>
        get() {
            val educations: MutableList<Education> = ArrayList()

            // Select All Query
            val selectQuery = "SELECT  * FROM " + Education.TABLE_NAME + " ORDER BY " +
                    Education.COLUMN_ID
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val education = Education()
                    education.id = cursor.getInt(cursor.getColumnIndex(Education.COLUMN_ID))
                    education.code = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CODE))
                    education.codename = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CODENAME))
                    education.create_by = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CREATE_BY))
                    education.create_date = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CREATE_DATE))
                    education.modify_by = cursor.getString(cursor.getColumnIndex(Education.COLUMN_MODIFY_BY))
                    education.modify_date = cursor.getString(cursor.getColumnIndex(Education.COLUMN_MODIFY_DATE))
                    educations.add(education)
                } while (cursor.moveToNext())
            }

            // close db connection
            db.close()

            // return notes list
            return educations
        }

    val allReligions: List<Religion>
        get() {
            val religions: MutableList<Religion> = ArrayList()

            // Select All Query
            val selectQuery = "SELECT  * FROM " + Religion.TABLE_NAME + " ORDER BY " +
                    Religion.COLUMN_ID
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val religion = Religion()
                    religion.id = cursor.getInt(cursor.getColumnIndex(Education.COLUMN_ID))
                    religion.code = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CODE))
                    religion.codename = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CODENAME))
                    religion.create_by = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CREATE_BY))
                    religion.create_date = cursor.getString(cursor.getColumnIndex(Education.COLUMN_CREATE_DATE))
                    religion.modify_by = cursor.getString(cursor.getColumnIndex(Education.COLUMN_MODIFY_BY))
                    religion.modify_date = cursor.getString(cursor.getColumnIndex(Education.COLUMN_MODIFY_DATE))
                    religions.add(religion)
                } while (cursor.moveToNext())
            }

            // close db connection
            db.close()

            // return notes list
            return religions
        }

    fun getAllAmphurs(keyValue: String): List<Amphur> {
        val amphurs: MutableList<Amphur> = ArrayList()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + Amphur.TABLE_NAME + " WHERE substr(" + Amphur.COLUMN_CODE + ",1,2) = " + "'" + keyValue + "'" + " ORDER BY " +
                Amphur.COLUMN_ID
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val amphur = Amphur()
                amphur.id = cursor.getInt(cursor.getColumnIndex(Amphur.COLUMN_ID))
                amphur.code = cursor.getString(cursor.getColumnIndex(Amphur.COLUMN_CODE))
                amphur.codename = cursor.getString(cursor.getColumnIndex(Amphur.COLUMN_CODENAME))
                amphur.create_by = cursor.getString(cursor.getColumnIndex(Amphur.COLUMN_CREATE_BY))
                amphur.create_date = cursor.getString(cursor.getColumnIndex(Amphur.COLUMN_CREATE_DATE))
                amphur.modify_by = cursor.getString(cursor.getColumnIndex(Amphur.COLUMN_MODIFY_BY))
                amphur.modify_date = cursor.getString(cursor.getColumnIndex(Amphur.COLUMN_MODIFY_DATE))
                amphurs.add(amphur)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()

        // return notes list
        return amphurs
    }

    fun getAllTumbons(keyValue: String): List<Tumbon> {
        val tumbons: MutableList<Tumbon> = ArrayList()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + Tumbon.TABLE_NAME + " WHERE substr(" + Tumbon.COLUMN_CODE + ",1,4) = " + "'" + keyValue + "'" + " ORDER BY " +
                Tumbon.COLUMN_ID
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val tumbon = Tumbon()
                tumbon.id = cursor.getInt(cursor.getColumnIndex(Tumbon.COLUMN_ID))
                tumbon.code = cursor.getString(cursor.getColumnIndex(Tumbon.COLUMN_CODE))
                tumbon.codename = cursor.getString(cursor.getColumnIndex(Tumbon.COLUMN_CODENAME))
                tumbon.create_by = cursor.getString(cursor.getColumnIndex(Tumbon.COLUMN_CREATE_BY))
                tumbon.create_date = cursor.getString(cursor.getColumnIndex(Tumbon.COLUMN_CREATE_DATE))
                tumbon.modify_by = cursor.getString(cursor.getColumnIndex(Tumbon.COLUMN_MODIFY_BY))
                tumbon.modify_date = cursor.getString(cursor.getColumnIndex(Tumbon.COLUMN_MODIFY_DATE))
                tumbons.add(tumbon)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()

        // return notes list
        return tumbons
    }

    fun getAllCommunitys(keyValue: String): List<Community> {
        val communitys: MutableList<Community> = ArrayList()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + Community.TABLE_NAME + " WHERE substr(" + Community.COLUMN_CODE + ",1,6) = " + "'" + keyValue + "'" + " ORDER BY " +
                Community.COLUMN_ID
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val community = Community()
                community.id = cursor.getInt(cursor.getColumnIndex(Community.COLUMN_ID))
                community.code = cursor.getString(cursor.getColumnIndex(Community.COLUMN_CODE))
                community.moo = cursor.getString(cursor.getColumnIndex(Community.COLUMN_MOO))
                community.codename = cursor.getString(cursor.getColumnIndex(Community.COLUMN_CODENAME))
                community.create_by = cursor.getString(cursor.getColumnIndex(Community.COLUMN_CREATE_BY))
                community.create_date = cursor.getString(cursor.getColumnIndex(Community.COLUMN_CREATE_DATE))
                community.modify_by = cursor.getString(cursor.getColumnIndex(Community.COLUMN_MODIFY_BY))
                community.modify_date = cursor.getString(cursor.getColumnIndex(Community.COLUMN_MODIFY_DATE))
                communitys.add(community)
            } while (cursor.moveToNext())
        }

        // close db connection
        db.close()

        // return notes list
        return communitys
    }

    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "smartsurvey_master.db"
    }
}