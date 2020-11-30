package com.uigitdev.mailapp.model

class SentModel {
    private var id: Int = 0
    private var title: String
    private var text: String
    private var date: String

    constructor(id: Int, title: String, text: String, date: String) {
        this.id = id
        this.title = title
        this.text = text
        this.date = date
    }

    constructor(title: String, text: String, date: String) {
        this.title = title
        this.text = text
        this.date = date
    }

    fun getId(): Int {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getText(): String {
        return text
    }

    fun getDate(): String {
        return date
    }
}