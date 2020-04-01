package com.cdd.smartsurvey.sqlite

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class DBManager(private val context: Context) {
    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DBManager {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

}