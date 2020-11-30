package com.uigitdev.mailapp.design

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import com.uigitdev.mailapp.R

class BottomComponent(view: View, var bottomComponentListener: BottomComponentListener) {

    companion object {
        val HOME: Int = 0
        val EMAIL: Int = 1
        val SAVED: Int = 2
    }

    private var CURRENT: Int = HOME
    private lateinit var bottom_parent: CardView
    private lateinit var bottom_item_1_click_parent: RelativeLayout
    private lateinit var bottom_item_1_icon: ImageView
    private lateinit var bottom_item_2_click_parent: RelativeLayout
    private lateinit var bottom_item_2_icon: ImageView
    private lateinit var bottom_item_3_click_parent: RelativeLayout
    private lateinit var bottom_item_3_icon: ImageView
    private lateinit var item_1_label: TextView
    private lateinit var item_2_label: TextView
    private lateinit var item_3_label: TextView

    private var selectedColor: String = "#FFFFFF"
    private var unselectedColor: String = "#4D505A"

    init {
        setType(view)
        setOnClick(bottom_item_1_click_parent, HOME)
        setOnClick(bottom_item_2_click_parent, EMAIL)
        setOnClick(bottom_item_3_click_parent, SAVED)
        setSelectedStyle()
    }

    private fun setType(view: View) {
        bottom_parent = view.findViewById(R.id.bottom_parent)
        bottom_item_1_click_parent = view.findViewById(R.id.bottom_item_1_click_parent)
        bottom_item_1_icon = view.findViewById(R.id.bottom_item_1_icon)
        bottom_item_2_click_parent = view.findViewById(R.id.bottom_item_2_click_parent)
        bottom_item_2_icon = view.findViewById(R.id.bottom_item_2_icon)
        bottom_item_3_click_parent = view.findViewById(R.id.bottom_item_3_click_parent)
        bottom_item_3_icon = view.findViewById(R.id.bottom_item_3_icon)
        item_1_label = view.findViewById(R.id.item_1_label)
        item_2_label = view.findViewById(R.id.item_2_label)
        item_3_label = view.findViewById(R.id.item_3_label)
        bottomComponentListener.selectedTab(CURRENT)
    }

    fun setItemBackgroundColor(color: String) {
        bottom_parent.setCardBackgroundColor(Color.parseColor(color))
    }

    fun setIcons(icon1: Int, icon2: Int, icon3: Int) {
        bottom_item_1_icon.setImageResource(icon1)
        bottom_item_2_icon.setImageResource(icon2)
        bottom_item_3_icon.setImageResource(icon3)
    }

    private fun setIconTint(icon: ImageView, color: String) {
        icon.setColorFilter(Color.parseColor(color), android.graphics.PorterDuff.Mode.SRC_IN)
        ImageViewCompat.setImageTintList(icon, ColorStateList.valueOf(Color.parseColor(color)))
    }

    private fun getSelectedColor(): String {
        return selectedColor
    }

    fun setSelectedColor(selectedColor: String) {
        this.selectedColor = selectedColor
    }

    private fun getUnselectedColor(): String {
        return unselectedColor
    }

    fun setUnselectedColor(unselectedColor: String) {
        this.unselectedColor = unselectedColor
    }

    private fun setOnClick(parent: RelativeLayout, BOTTOM_ITEM: Int) {
        parent.setOnClickListener {
            if (CURRENT != BOTTOM_ITEM) {
                CURRENT = BOTTOM_ITEM
                setSelectedStyle()
                bottomComponentListener.selectedTab(CURRENT)
            }
        }
    }

    private fun setLabelColor(textView: TextView, color: String) {
        textView.setTextColor(Color.parseColor(color))
    }

    private fun setSelectedStyle() {
        when (CURRENT) {
            HOME -> {
                setIconTint(bottom_item_1_icon, getSelectedColor())
                setLabelColor(item_1_label, getSelectedColor())
                setIconTint(bottom_item_2_icon, getUnselectedColor())
                setLabelColor(item_2_label, getUnselectedColor())
                setIconTint(bottom_item_3_icon, getUnselectedColor())
                setLabelColor(item_3_label, getUnselectedColor())
            }
            EMAIL -> {
                setIconTint(bottom_item_2_icon, getSelectedColor())
                setLabelColor(item_2_label, getSelectedColor())
                setIconTint(bottom_item_1_icon, getUnselectedColor())
                setLabelColor(item_1_label, getUnselectedColor())
                setIconTint(bottom_item_3_icon, getUnselectedColor())
                setLabelColor(item_3_label, getUnselectedColor())
            }
            SAVED -> {
                setIconTint(bottom_item_3_icon, getSelectedColor())
                setLabelColor(item_3_label, getSelectedColor())
                setIconTint(bottom_item_1_icon, getUnselectedColor())
                setLabelColor(item_1_label, getUnselectedColor())
                setIconTint(bottom_item_2_icon, getUnselectedColor())
                setLabelColor(item_2_label, getUnselectedColor())
            }
        }
    }

    fun setDefaultSelected(BOTTOM_ITEM: Int) {
        CURRENT = BOTTOM_ITEM
        setSelectedStyle()
    }

    interface BottomComponentListener {
        fun selectedTab(BOTTOM_ITEM: Int)
    }
}