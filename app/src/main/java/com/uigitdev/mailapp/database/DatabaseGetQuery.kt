package com.uigitdev.mailapp.database

import android.content.Context
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.SavedModel
import com.uigitdev.mailapp.model.SentModel
import java.util.*

class DatabaseGetQuery {

    fun getAllEmail(context: Context?): ArrayList<EmailModel>? {
        val db = InternalDatabase(context).readableDatabase
        val table = DatabaseKeys.EMAIL.EMAIL_TABLE.toString()
        val date = DatabaseKeys.EMAIL.DATE.toString()

        val cursor = db.rawQuery("SELECT * FROM $table ORDER BY $date DESC ", null)
        val arrayList = ArrayList<EmailModel>()

        var data_id: Int
        var data_picture: String
        var data_name: String
        var data_email: String
        var data_date: String

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                data_id = cursor.getInt(cursor.getColumnIndex(DatabaseKeys.EMAIL.ID.toString()))
                data_picture = cursor.getString(cursor.getColumnIndex(DatabaseKeys.EMAIL.PICTURE_URL.toString()))
                data_name = cursor.getString(cursor.getColumnIndex(DatabaseKeys.EMAIL.NAME.toString()))
                data_email = cursor.getString(cursor.getColumnIndex(DatabaseKeys.EMAIL.EMAIL.toString()))
                data_date = cursor.getString(cursor.getColumnIndex(DatabaseKeys.EMAIL.DATE.toString()))

                arrayList.add(
                    EmailModel(
                        data_id,
                        data_picture,
                        data_name,
                        data_email,
                        data_date
                    )
                )
                cursor.moveToNext()
            }
        }
        db.close()
        return arrayList
    }

    fun getAllSavedText(context: Context?): ArrayList<SavedModel>? {
        val db = InternalDatabase(context).readableDatabase
        val table = DatabaseKeys.SAVED.SAVED_TABLE.toString()
        val date = DatabaseKeys.SAVED.DATE.toString()

        val cursor = db.rawQuery("SELECT * FROM $table ORDER BY $date DESC ", null)
        val arrayList = ArrayList<SavedModel>()

        var data_id: Int
        var data_title: String
        var data_text: String
        var data_date: String

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                data_id = cursor.getInt(cursor.getColumnIndex(DatabaseKeys.SAVED.ID.toString()))
                data_title = cursor.getString(cursor.getColumnIndex(DatabaseKeys.SAVED.TITLE.toString()))
                data_text = cursor.getString(cursor.getColumnIndex(DatabaseKeys.SAVED.TEXT.toString()))
                data_date = cursor.getString(cursor.getColumnIndex(DatabaseKeys.SAVED.DATE.toString()))

                arrayList.add(
                    SavedModel(
                        data_id,
                        data_title,
                        data_text,
                        data_date
                    )
                )
                cursor.moveToNext()
            }
        }
        db.close()
        return arrayList
    }

    fun getAllSentText(context: Context?): ArrayList<SentModel>? {
        val db = InternalDatabase(context).readableDatabase
        val table = DatabaseKeys.SENT.SENT_TABLE.toString()
        val date = DatabaseKeys.SENT.DATE.toString()

        val cursor = db.rawQuery("SELECT * FROM $table ORDER BY $date DESC ", null)
        val arrayList = ArrayList<SentModel>()

        var data_id: Int
        var data_title: String?
        var data_text: String?
        var data_date: String?

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                data_id = cursor.getInt(cursor.getColumnIndex(DatabaseKeys.SENT.ID.toString()))
                data_title = cursor.getString(cursor.getColumnIndex(DatabaseKeys.SENT.TITLE.toString()))
                data_text = cursor.getString(cursor.getColumnIndex(DatabaseKeys.SENT.TEXT.toString()))
                data_date = cursor.getString(cursor.getColumnIndex(DatabaseKeys.SENT.DATE.toString()))

                arrayList.add(
                    SentModel(
                        data_id,
                        data_title,
                        data_text,
                        data_date
                    )
                )
                cursor.moveToNext()
            }
        }
        db.close()
        return arrayList
    }
}