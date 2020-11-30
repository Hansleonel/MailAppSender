package com.uigitdev.mailapp.database

class DatabaseKeys {

    companion object {
        val DATABASE_NAME = "mailapp.db"
    }

    enum class EMAIL {
        EMAIL_TABLE,
        ID,
        PICTURE_URL,
        NAME,
        EMAIL,
        DATE
    }

    enum class SAVED {
        SAVED_TABLE,
        ID,
        TITLE,
        TEXT,
        DATE
    }

    enum class SENT {
        SENT_TABLE,
        ID,
        TITLE,
        TEXT,
        DATE
    }
}