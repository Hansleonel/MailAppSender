package com.uigitdev.mailapp.design.fabmenu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.design.fabmenu.model.FabMenuModel
import com.uigitdev.mailapp.design.fabmenu.viewholder.FabMenuItemViewHolder
import java.util.*

class FabMenuItemAdapter(private val context: Context, private val arrayList: ArrayList<FabMenuModel>, private val fabMenuListener: FabMenuListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.design_fab_menu_item, parent, false)
        return FabMenuItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FabMenuItemViewHolder).itemIcon.setImageResource(arrayList[position].itemIcon)
        holder.itemText.text = arrayList[position].itemTitle
        holder.button.setOnClickListener {
            fabMenuListener.optionClick(arrayList[position].itemClickId)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface FabMenuListener {
        fun optionClick(clickId: Int)
    }
}