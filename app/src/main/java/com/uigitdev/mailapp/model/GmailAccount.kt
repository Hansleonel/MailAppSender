package com.uigitdev.mailapp.model

import java.util.*

class GmailAccount(email: String, password: String) {
    private var email: String
    private var password: String

    fun getEmail(): String {
        return email.toLowerCase(Locale.ROOT)
    }

    fun getPassword():String{
        return password
    }

    fun setEmail(email: String) {
        this.email = email.toLowerCase(Locale.ROOT)
    }

    init {
        this.email = email.toLowerCase(Locale.ROOT)
        this.password = password
    }
}