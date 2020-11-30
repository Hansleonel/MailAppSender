package com.uigitdev.mailapp.design

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import com.uigitdev.mailapp.R

class CustomInputField(view: View) {
    private lateinit var item_card_parent: CardView
    private lateinit var item_input_text: EditText
    private lateinit var item_input_icon: ImageView
    private lateinit var item_error_text: TextView

    private fun setType(view: View) {
        item_card_parent = view.findViewById(R.id.item_card_parent)
        item_input_text = view.findViewById(R.id.item_input_text)
        item_input_icon = view.findViewById(R.id.item_input_icon)
        item_error_text = view.findViewById(R.id.item_error_text)
    }

    fun getText(): String{
        return item_input_text.text.toString()
    }

    fun setBackgroundColor(color: String?) {
        item_card_parent.setCardBackgroundColor(Color.parseColor(color))
    }

    fun setVectorIcon(iconId: Int, tint: String?) {
        item_input_icon.setImageResource(iconId)
        ImageViewCompat.setImageTintList(
            item_input_icon,
            ColorStateList.valueOf(Color.parseColor(tint))
        )
    }

    fun errorTextVisibility(isVisible: Boolean) {
        if (isVisible) {
            item_error_text.visibility = View.VISIBLE
        } else {
            item_error_text.visibility = View.GONE
        }
    }

    fun errorText(text: String?) {
        item_error_text.text = text
    }

    fun hint(text: String?) {
        item_input_text.hint = text
    }

    fun setText(text: String?) {
        item_input_text.setText(text)
    }

    fun getInputField(): EditText? {
        return item_input_text
    }

    fun editDisable(isDisable: Boolean) {
        item_input_text.isEnabled = !isDisable
    }

    init {
        setType(view)
    }
}
