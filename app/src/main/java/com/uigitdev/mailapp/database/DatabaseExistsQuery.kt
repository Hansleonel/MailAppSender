package com.uigitdev.mailapp.database

import android.content.Context
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.SavedModel
import java.lang.String

class DatabaseExistsQuery {

    fun isExistsEmail(context: Context?, emailModel: EmailModel): Boolean {
        val db = InternalDatabase(context).readableDatabase
        val table = DatabaseKeys.EMAIL.EMAIL_TABLE.toString()
        val email = DatabaseKeys.EMAIL.EMAIL.toString()
        val cursor = db.rawQuery("SELECT COUNT (*) FROM $table WHERE $email = ? ", arrayOf(String.valueOf(emailModel.getEmail())))
        var count = 0
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                count = cursor.getInt(0)
                cursor.close()
            }
        }
        db.close()
        return count > 0
    }

    fun isExistsSavedText(context: Context?, savedModel: SavedModel): Boolean {
        val db = InternalDatabase(context).readableDatabase
        val table = DatabaseKeys.SAVED.SAVED_TABLE.toString()
        val title = DatabaseKeys.SAVED.TITLE.toString()
        val cursor = db.rawQuery("SELECT COUNT (*) FROM $table WHERE $title = ? ", arrayOf(String.valueOf(savedModel.getTitle())))
        var count = 0

        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                count = cursor.getInt(0)
                cursor.close()
            }
        }
        db.close()
        return count > 0
    }
}