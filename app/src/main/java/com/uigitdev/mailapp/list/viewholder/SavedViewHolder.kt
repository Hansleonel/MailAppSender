package com.uigitdev.mailapp.list.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.design.SavedElement

class SavedViewHolder(view: View, context: Context?) : RecyclerView.ViewHolder(view) {
    val savedElement: SavedElement = SavedElement(view, context!!)
}