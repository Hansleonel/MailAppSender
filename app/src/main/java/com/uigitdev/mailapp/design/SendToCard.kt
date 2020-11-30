package com.uigitdev.mailapp.design

import android.graphics.Color
import android.view.View
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.uigitdev.mailapp.R

class SendToCard (private val view: View){
    private var item_card_parent: CardView? = null
    private var item_click_parent: RelativeLayout? = null
    private var item_radio_button: RadioButton? = null
    private var item_text: TextView? = null
    private var item_info_text: TextView? = null

    init {
        setType(view)
    }

    private fun setType(view: View) {
        item_card_parent = view.findViewById(R.id.item_card_parent)
        item_click_parent = view.findViewById(R.id.item_click_parent)
        item_radio_button = view.findViewById(R.id.item_radio_button)
        item_info_text = view.findViewById(R.id.item_info_text)
        item_text = view.findViewById(R.id.item_text)
    }

    fun setItemBackgroundColor(color: String?) {
        item_card_parent!!.setCardBackgroundColor(Color.parseColor(color))
    }

    fun getButton(): RelativeLayout? {
        return item_click_parent
    }

    fun setText(text: String?) {
        item_text!!.text = text
    }

    fun setSelected(isSelected: Boolean) {
        item_radio_button!!.isChecked = isSelected
    }

    fun isChecked(): Boolean {
        return item_radio_button!!.isChecked
    }

    fun setInfoVisibility(isVisible: Boolean) {
        if (isVisible) {
            item_info_text!!.visibility = View.VISIBLE
        } else {
            item_info_text!!.visibility = View.GONE
        }
    }

    fun setInfoText(text: String?) {
        item_info_text!!.text = text
    }

    fun setDisable(isDisable: Boolean) {
        item_radio_button!!.isEnabled = !isDisable
    }
}