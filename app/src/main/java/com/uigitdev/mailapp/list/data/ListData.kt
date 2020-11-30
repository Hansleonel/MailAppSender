package com.uigitdev.mailapp.list.data

import android.content.Context
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.DatabaseGetQuery
import com.uigitdev.mailapp.design.fabmenu.model.FabMenuModel
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.SavedModel
import com.uigitdev.mailapp.model.SentModel
import java.util.*

class ListData {

    fun getSavedList(context: Context): ArrayList<SavedModel>? {
        return DatabaseGetQuery().getAllSavedText(context)
    }

    fun getEmailList(context: Context?): ArrayList<EmailModel>? {
        return DatabaseGetQuery().getAllEmail(context)
    }

    fun getSentList(context: Context?): ArrayList<SentModel>? {
        return DatabaseGetQuery().getAllSentText(context)
    }

    fun getFabMenuList(context: Context): ArrayList<FabMenuModel>? {
        val arrayList = ArrayList<FabMenuModel>()
        arrayList.add(
            FabMenuModel(
                0,
                R.drawable.ic_baseline_create_24,
                context.getString(R.string.fab_menu_item_1)
            )
        )
        arrayList.add(
            FabMenuModel(
                1,
                R.drawable.ic_baseline_text_fields_24,
                context.getString(R.string.fab_menu_item_2)
            )
        )
        arrayList.add(
            FabMenuModel(
                2,
                R.drawable.ic_baseline_person_add_24,
                context.getString(R.string.fab_menu_item_3)
            )
        )
        arrayList.add(
            FabMenuModel(
                3,
                R.drawable.ic_baseline_group_add_24,
                context.getString(R.string.fab_menu_item_4)
            )
        )

        arrayList.add(
            FabMenuModel(
                4,
                R.drawable.ic_baseline_delete_24,
                context.getString(R.string.fab_menu_item_5)
            )
        )

        return arrayList
    }
}
