package com.example.gps_git.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDataBase(context: Context) :
    SQLiteOpenHelper(context, db.DATABASE_NAME, null, db.DATABASE_VERSION) {


    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(com.example.gps_git.database.db.CREAT_TABLE)
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(com.example.gps_git.database.db.SQL_DELETE_TABLE)
        onCreate(p0)
    }
}