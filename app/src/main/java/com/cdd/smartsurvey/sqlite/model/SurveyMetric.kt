package com.cdd.smartsurvey.sqlite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SurveyMetricList(
        var list: List<SurveyMetric>
) : Parcelable

@Parcelize
data class SurveyMetric(
        var id: Int = 0,
        var tbl_survey_group_masterid: String = "",
        var metric_no: String = "",
        var metric_name: String = "",
        var metric_display: String = "",
        var metric_description: String = "",
        var metric_orderby: String = ""
) : Parcelable {
    companion object {
        const val TABLE_NAME = "tbl_survey_metric_master"
        const val COLUMN_ID = "id"
        const val COLUMN_TBL_SURVEY_GROUP_MASTERID = "tbl_survey_group_masterid"
        const val COLUMN_METRIC_NO = "metric_no"
        const val COLUMN_METRIC_NAME = "metric_name"
        const val COLUMN_METRIC_DISPLAY = "metric_display"
        const val COLUMN_METRIC_DESCRIPTION = "metric_description"
        const val COLUMN_METRIC_ORDERBY = "metric_orderby"
    }
}