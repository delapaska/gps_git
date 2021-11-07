package com.example.gps_git.database

import android.provider.BaseColumns

object db : BaseColumns {
    const val TABLE_NAME = "my_table"
    const val COLUMN_NAME_CONTENT = "content"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "CoordsDataBase.db"

    const val CREAT_TABLE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" + "${BaseColumns._ID} INTEGER PRIMARY KEY,$COLUMN_NAME_CONTENT TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
}