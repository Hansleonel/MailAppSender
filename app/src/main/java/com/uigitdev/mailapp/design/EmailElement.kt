package com.uigitdev.mailapp.design

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.uigitdev.mailapp.R

class EmailElement(view: View) {
    private lateinit var item_card_parent: CardView
    private lateinit var item_click_parent: RelativeLayout
    private lateinit var item_send_icon: ImageView
    private lateinit var item_title: TextView
    private lateinit var item_text: TextView
    private lateinit var item_picture: ImageView

    init {
        setType(view)
    }

    private fun setType(view: View) {
        item_card_parent = view.findViewById(R.id.item_card_parent)
        item_click_parent = view.findViewById(R.id.item_click_parent)
        item_send_icon = view.findViewById(R.id.item_send_icon)
        item_title = view.findViewById(R.id.item_title)
        item_text = view.findViewById(R.id.item_text)
        item_picture = view.findViewById(R.id.item_picture)
    }

    fun setName(text: String) {
        item_title!!.text = text
    }

    fun setEmail(text: String) {
        item_text!!.text = text
    }

    fun setImageURL(imageURL: String) {
        Picasso.get().load(imageURL).fit().centerCrop().into(item_picture, object : Callback {
            override fun onSuccess() {}
            override fun onError(e: Exception) {
                Picasso.get()
                    .load("https://uigitdev.com/wp-content/uploads/2020/07/uigitdev_new_logo.png")
                    .into(item_picture)
            }
        })
    }

    fun getButton(): RelativeLayout {
        return item_click_parent
    }
}