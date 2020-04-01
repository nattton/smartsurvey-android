package com.cdd.smartsurvey.sqlite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Amphur(
        var id: Int = 0,
        var code: String = "",
        var codename: String = "",
        var create_by: String = "",
        var create_date: String = "",
        var modify_by: String = "",
        var modify_date: String = ""
) : Parcelable {
    companion object {
        const val TABLE_NAME = "tbl_amphur_master"
        const val COLUMN_ID = "id"
        const val COLUMN_CODE = "amphur_id"
        const val COLUMN_CODENAME = "amphur_name"
        const val COLUMN_CREATE_BY = "create_by"
        const val COLUMN_CREATE_DATE = "create_date"
        const val COLUMN_MODIFY_BY = "modify_by"
        const val COLUMN_MODIFY_DATE = "modify_date"
    }
}