package com.uigitdev.mailapp.database

class DatabaseCreateQuery {

    fun createEmailTable(): String {
        val table = DatabaseKeys.EMAIL.EMAIL_TABLE.toString()
        val id = DatabaseKeys.EMAIL.ID.toString()
        val picture_url = DatabaseKeys.EMAIL.PICTURE_URL.toString()
        val name = DatabaseKeys.EMAIL.NAME.toString()
        val email = DatabaseKeys.EMAIL.EMAIL.toString()
        val date = DatabaseKeys.EMAIL.DATE.toString()
        return ("CREATE TABLE "
                + table + " ("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + picture_url + " TEXT, "
                + name + " TEXT, "
                + email + " TEXT, "
                + date + " TEXT"
                + ")")
    }

    fun createSavedTable(): String {
        val table = DatabaseKeys.SAVED.SAVED_TABLE.toString()
        val id = DatabaseKeys.SAVED.ID.toString()
        val title = DatabaseKeys.SAVED.TITLE.toString()
        val text = DatabaseKeys.SAVED.TEXT.toString()
        val date = DatabaseKeys.SAVED.DATE.toString()
        return ("CREATE TABLE "
                + table + " ("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + title + " TEXT, "
                + text + " TEXT, "
                + date + " TEXT"
                + ")")
    }

    fun createSentTable(): String {
        val table = DatabaseKeys.SENT.SENT_TABLE.toString()
        val id = DatabaseKeys.SENT.ID.toString()
        val title = DatabaseKeys.SENT.TITLE.toString()
        val text = DatabaseKeys.SENT.TEXT.toString()
        val date = DatabaseKeys.SENT.DATE.toString()
        return ("CREATE TABLE "
                + table + " ("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + title + " TEXT, "
                + text + " TEXT, "
                + date + " TEXT"
                + ")")
    }
}