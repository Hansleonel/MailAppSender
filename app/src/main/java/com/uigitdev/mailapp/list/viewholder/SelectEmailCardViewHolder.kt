package com.uigitdev.mailapp.list.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.design.SelectEmailCard

class SelectEmailCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var selectEmailCard: SelectEmailCard? = null

    init {
        selectEmailCard =
            SelectEmailCard(itemView)
    }

    fun getSelectEmailCard(): SelectEmailCard? {
        return selectEmailCard
    }
}