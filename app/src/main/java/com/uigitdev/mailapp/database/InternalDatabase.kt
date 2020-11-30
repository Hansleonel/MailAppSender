package com.uigitdev.mailapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class InternalDatabase(context: Context?) :

    SQLiteOpenHelper(context, DatabaseKeys.DATABASE_NAME, null, 1) {

    init {
        writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseCreateQuery().createEmailTable())
        db.execSQL(DatabaseCreateQuery().createSavedTable())
        db.execSQL(DatabaseCreateQuery().createSentTable())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseDropQuery().dropTable(DatabaseKeys.EMAIL.EMAIL_TABLE.toString()))
        db.execSQL(DatabaseDropQuery().dropTable(DatabaseKeys.SAVED.SAVED_TABLE.toString()))
        db.execSQL(DatabaseDropQuery().dropTable(DatabaseKeys.SENT.SENT_TABLE.toString()))
        onCreate(db)
    }
}