package com.uigitdev.mailapp.design

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.uigitdev.mailapp.R

class SavedElement(view: View, val context: Context) {
    private lateinit var item_card_parent: CardView
    private lateinit var item_click_parent: RelativeLayout
    private lateinit var item_option_icon: ImageView
    private lateinit var item_title: TextView
    private lateinit var item_text: TextView

    init {
        setType(view)
    }

    private fun setType(view: View) {
        item_card_parent = view.findViewById(R.id.item_card_parent)
        item_click_parent = view.findViewById(R.id.item_click_parent)
        item_option_icon = view.findViewById(R.id.item_option_icon)
        item_title = view.findViewById(R.id.item_title)
        item_text = view.findViewById(R.id.item_text)
    }

    fun setTitle(text: String) {
        item_title!!.text = text
    }

    fun setText(text: String) {
        item_text!!.text = text
    }

    fun getMenuButton(): ImageView? {
        return item_option_icon
    }

    fun getButton(): RelativeLayout {
        return item_click_parent
    }
}