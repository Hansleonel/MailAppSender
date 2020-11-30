package com.uigitdev.mailapp.database

import android.content.ContentValues
import android.content.Context
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.SavedModel
import com.uigitdev.mailapp.model.SentModel

class DatabaseInsertQuery {

    fun insertEmail(context: Context, emailModel: EmailModel, insertListener: InsertListener) {
        val db = InternalDatabase(context).writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DatabaseKeys.EMAIL.PICTURE_URL.toString(), emailModel.getPictureURL())
        contentValues.put(DatabaseKeys.EMAIL.NAME.toString(), emailModel.getName())
        contentValues.put(DatabaseKeys.EMAIL.EMAIL.toString(), emailModel.getEmail())
        contentValues.put(DatabaseKeys.EMAIL.DATE.toString(), emailModel.getDate())

        if (!DatabaseExistsQuery().isExistsEmail(context, emailModel)) {
            db.insert(DatabaseKeys.EMAIL.EMAIL_TABLE.toString(), null, contentValues)
            insertListener.dataSuccess()
        } else {
            insertListener.dataExists(emailModel.getEmail() + ", " + context.getString(R.string.database_exists_email))
        }
        db.close()
    }

    fun insertSavedText(context: Context, savedModel: SavedModel, insertListener: InsertListener) {
        val db = InternalDatabase(context).writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DatabaseKeys.SAVED.TITLE.toString(), savedModel.getTitle())
        contentValues.put(DatabaseKeys.SAVED.TEXT.toString(), savedModel.getText())
        contentValues.put(DatabaseKeys.SAVED.DATE.toString(), savedModel.getDate())

        if (!DatabaseExistsQuery().isExistsSavedText(context, savedModel)) {
            db.insert(DatabaseKeys.SAVED.SAVED_TABLE.toString(), null, contentValues)
            insertListener.dataSuccess()
        } else {
            insertListener.dataExists(context.getString(R.string.database_exists_saved_text))
        }
        db.close()
    }

    fun insertSentText(context: Context?, sentModel: SentModel, insertListener: InsertListener) {
        val db = InternalDatabase(context).writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DatabaseKeys.SENT.TITLE.toString(), sentModel.getTitle())
        contentValues.put(DatabaseKeys.SENT.TEXT.toString(), sentModel.getText())
        contentValues.put(DatabaseKeys.SENT.DATE.toString(), sentModel.getDate())
        db.insert(DatabaseKeys.SENT.SENT_TABLE.toString(), null, contentValues)
        insertListener.dataSuccess()
        db.close()
    }

    interface InsertListener {
        fun dataExists(text: String?)
        fun dataSuccess()
    }
}