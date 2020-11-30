package com.uigitdev.mailapp.design

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.uigitdev.mailapp.R

class SelectEmailCard(private val view: View) {
    private var item_card_parent: CardView? = null
    private var item_click_parent: RelativeLayout? = null
    private var item_radio_button: RadioButton? = null
    private var item_title: TextView? = null
    private var item_text: TextView? = null
    private var item_picture: ImageView? = null

    init {
        setType(view)
    }

    private fun setType(view: View) {
        item_card_parent = view.findViewById(R.id.item_card_parent)
        item_click_parent = view.findViewById(R.id.item_click_parent)
        item_radio_button = view.findViewById(R.id.item_radio_button)
        item_title = view.findViewById(R.id.item_title)
        item_text = view.findViewById(R.id.item_text)
        item_picture = view.findViewById(R.id.item_picture)
    }

    fun setItemBackgroundColor(color: String?) {
        item_card_parent!!.setCardBackgroundColor(Color.parseColor(color))
    }

    fun setData(imageURL: String?, name: String?, email: String?) {
        item_title!!.text = name
        item_text!!.text = email
        Picasso.get().load(imageURL).fit().centerCrop()
            .into(item_picture, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception) {
                    Picasso.get()
                        .load("https://uigitdev.com/wp-content/uploads/2020/07/uigitdev_new_logo.png")
                        .into(item_picture)
                }
            })
    }

    fun getButton(): RelativeLayout? {
        return item_click_parent
    }

    fun setSelected(isSelected: Boolean) {
        item_radio_button!!.isChecked = isSelected
    }

    fun isChecked(): Boolean {
        return item_radio_button!!.isChecked
    }
}