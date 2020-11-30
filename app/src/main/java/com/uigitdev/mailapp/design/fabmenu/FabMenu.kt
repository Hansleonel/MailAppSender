package com.uigitdev.mailapp.design.fabmenu

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.design.fabmenu.adapter.FabMenuItemAdapter
import com.uigitdev.mailapp.design.fabmenu.model.FabMenuModel
import java.util.*

class FabMenu(view: View, private val context: Context, private val fabMenuListener: FabMenuItemAdapter.FabMenuListener) {
    private lateinit var item_close_click_parent: RelativeLayout
    private lateinit var item_recycler_view: RecyclerView

    init {
        setType(view)
    }

    private fun setType(view: View) {
        item_close_click_parent = view.findViewById(R.id.item_close_click_parent)
        item_recycler_view = view.findViewById(R.id.item_recycler_view)
    }

    fun setList(fabMenuModels: ArrayList<FabMenuModel>) {
        val layoutManager = LinearLayoutManager(context)
        item_recycler_view!!.layoutManager = layoutManager
        val adapter = FabMenuItemAdapter(context, fabMenuModels, fabMenuListener)
        item_recycler_view!!.adapter = adapter
    }

    fun getCloseButton(): RelativeLayout{
        return item_close_click_parent
    }
}