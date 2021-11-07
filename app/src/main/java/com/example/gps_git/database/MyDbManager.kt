package com.example.gps_git.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyDbManager(context: Context) {
    val myDbHelper = MyDataBase(context)
    var db: SQLiteDatabase? = null
    fun openDB() {
        db = myDbHelper.writableDatabase
    }

    fun insertTo_Db(title: String, content: String) {
        val values = ContentValues().apply {
            put(com.example.gps_git.database.db.COLUMN_NAME_CONTENT, content)
        }
        db?.insert(com.example.gps_git.database.db.TABLE_NAME, null, values)
    }

    fun readDbData(): ArrayList<String> {
        val dataList = ArrayList<String>()
        val cursor = db?.query(
            com.example.gps_git.database.db?.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (this?.moveToNext()!!) {
                val dataText =
                    cursor?.getString(cursor.getColumnIndex(com.example.gps_git.database.db.COLUMN_NAME_CONTENT))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList

    }

    fun closeDB() {
        myDbHelper.close()
    }

}