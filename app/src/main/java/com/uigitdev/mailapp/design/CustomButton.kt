package com.uigitdev.mailapp.design

import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.uigitdev.mailapp.R

class CustomButton(view: View) {
    private lateinit var item_button_card_parent: CardView
    private lateinit var item_button_click_parent: RelativeLayout
    private lateinit var item_button_text: TextView
    private lateinit var item_progress: ProgressBar

    private fun setType(view: View) {
        item_button_card_parent = view.findViewById(R.id.item_button_card_parent)
        item_button_click_parent = view.findViewById(R.id.item_button_click_parent)
        item_button_text = view.findViewById(R.id.item_button_text)
        item_progress = view.findViewById(R.id.item_progress)
    }

    fun setButtonText(text: String?) {
        item_button_text!!.text = text
    }

    fun setButtonBackgroundColor(color: String?) {
        item_button_card_parent!!.setCardBackgroundColor(Color.parseColor(color))
    }

    fun getButton(): RelativeLayout {
        return item_button_click_parent
    }

    fun setProgressVisibility(isVisible: Boolean) {
        if (isVisible) {
            item_progress.visibility = View.VISIBLE
        } else {
            item_progress.visibility = View.GONE
        }
    }

    init {
        setType(view)
    }
}