package com.uigitdev.mailapp.database

class DatabaseDropQuery {

    fun dropTable(table: String): String {
        return "DROP IF TABLE EXISTS $table"
    }
}