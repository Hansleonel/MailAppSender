package com.uigitdev.mailapp.tools

import android.content.Context
import android.content.SharedPreferences

class MSharedPreferences(private val context: Context){
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    enum class PREF_KEYS {
        PREF_EMAIL, PREF_PASS
    }

    init {
        sharedPreferences = context.getSharedPreferences("pref_manager", 0)
        editor = sharedPreferences!!.edit()
    }

    fun setStringPreferences(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun getStringPreferences(key: String?): String? {
        return sharedPreferences!!.getString(key, "")
    }

    fun deletePreferences(key: String?) {
        sharedPreferences!!.edit().remove(key).apply()
    }
}