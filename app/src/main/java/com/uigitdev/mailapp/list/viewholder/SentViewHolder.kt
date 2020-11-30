package com.uigitdev.mailapp.list.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.design.SentElement

class SentViewHolder(view: View, context: Context?) : RecyclerView.ViewHolder(view) {
    val sentElement: SentElement = SentElement(view, context!!)
}