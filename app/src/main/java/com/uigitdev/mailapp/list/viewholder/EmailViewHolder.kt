package com.uigitdev.mailapp.list.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.design.EmailElement

class EmailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val emailElement: EmailElement = EmailElement(view)
}