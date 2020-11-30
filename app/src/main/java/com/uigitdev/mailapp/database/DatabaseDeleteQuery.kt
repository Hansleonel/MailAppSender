package com.uigitdev.mailapp.database

import android.content.Context

class DatabaseDeleteQuery {

    fun deleteSavedText(context: Context?, deleteId: Int) {
        val db = InternalDatabase(context).writableDatabase
        val table = DatabaseKeys.SAVED.SAVED_TABLE.toString()
        val id = DatabaseKeys.SAVED.ID.toString()
        val delete = "DELETE FROM $table WHERE $id = ? "
        db.execSQL(delete, arrayOf(deleteId.toString()))
        db.close()
    }

    fun deleteTableList(context: Context?, table: String) {
        val db = InternalDatabase(context).writableDatabase
        val delete = "DELETE FROM $table"
        db.execSQL(delete)
        db.close()
    }

    fun deleteSentText(context: Context?, deleteId: Int) {
        val db = InternalDatabase(context).writableDatabase
        val table = DatabaseKeys.SENT.SENT_TABLE.toString()
        val id = DatabaseKeys.SENT.ID.toString()
        val delete = "DELETE FROM $table WHERE $id = ? "
        db.execSQL(delete, arrayOf(deleteId.toString()))
        db.close()
    }
}