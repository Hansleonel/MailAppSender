package com.uigitdev.mailapp.database

import android.content.ContentValues
import android.content.Context
import com.uigitdev.mailapp.model.SavedModel

class DatabaseUpdateQuery {

    fun updateSavedModel(context: Context?, savedModel: SavedModel) {
        val db = InternalDatabase(context).writableDatabase
        val table = DatabaseKeys.SAVED.SAVED_TABLE.toString()
        val id = DatabaseKeys.SAVED.ID.toString()
        val title = DatabaseKeys.SAVED.TITLE.toString()
        val text = DatabaseKeys.SAVED.TEXT.toString()
        val date = DatabaseKeys.SAVED.DATE.toString()
        val contentValues = ContentValues()
        contentValues.put(title, savedModel.getTitle())
        contentValues.put(text, savedModel.getText())
        contentValues.put(date, savedModel.getDate())
        db.update(table, contentValues, "$id = ? ", arrayOf(savedModel.getId().toString()))
        db.close()
    }
}
