package com.uigitdev.mailapp.model

import android.annotation.SuppressLint

class EmailModel {
    private var id: Int = 0
    private var pictureURL: String
    private var name: String
    private var email: String
    private var date: String
    private var selected: Boolean = false;

    @SuppressLint("DefaultLocale")
    constructor(id: Int, pictureURL: String, name: String, email: String, date: String) {
        this.id = id
        this.pictureURL = pictureURL
        this.name = name
        this.email = email.toLowerCase()
        this.date = date
    }

    @SuppressLint("DefaultLocale")
    constructor(pictureURL: String, name: String, email: String, date: String) {
        this.pictureURL = pictureURL
        this.name = name
        this.email = email.toLowerCase()
        this.date = date
    }

    fun getId(): Int {
        return id;
    }

    fun getPictureURL(): String {
        return pictureURL
    }

    fun getName(): String {
        return name
    }

    fun getEmail(): String {
        return email
    }

    fun getDate(): String {
        return date
    }

    fun isSelected(): Boolean {
        return selected
    }

    fun setSelected(selected: Boolean) {
        this.selected = selected
    }
}
