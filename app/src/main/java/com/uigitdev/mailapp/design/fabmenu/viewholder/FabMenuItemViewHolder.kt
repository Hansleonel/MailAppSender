package com.uigitdev.mailapp.design.fabmenu.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R

class FabMenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val button: RelativeLayout = itemView.findViewById(R.id.item_click_parent)
    val itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
    val itemText: TextView = itemView.findViewById(R.id.item_text)
}