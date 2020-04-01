package com.cdd.smartsurvey.sqlite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SurveyGroup(
        var id: Int = 0,
        var groupNo: String = "",
        var groupName: String = "",
        var groupDisplay: String = "",
        var groupOrderby: String = "",
        var groupImage: String = "",
        var groupMetric: String = ""
) : Parcelable {
    companion object {
        const val TABLE_NAME = "tbl_survey_group_master"
        const val COLUMN_ID = "id"
        const val COLUMN_GROUP_NO = "group_no"
        const val COLUMN_GROUP_NAME = "group_name"
        const val COLUMN_GROUP_DISPLAY = "group_display"
        const val COLUMN_GROUP_ORDERBY = "group_orderby"
        const val COLUMN_GROUP_IMAGE = "group_image"
        const val COLUMN_GROUP_METRIC = "group_metric"
    }
}