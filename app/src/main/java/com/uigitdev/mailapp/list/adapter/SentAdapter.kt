package com.uigitdev.mailapp.list.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.activity.CreateLetterActivity
import com.uigitdev.mailapp.database.DatabaseDeleteQuery
import com.uigitdev.mailapp.fragment.SentFragment
import com.uigitdev.mailapp.list.viewholder.SentViewHolder
import com.uigitdev.mailapp.model.SentModel
import com.uigitdev.mailapp.tools.ActivityIntentTags
import com.uigitdev.mailapp.tools.fileimport.FileTools
import java.util.*

class SentAdapter(private val sentModels: ArrayList<SentModel>, private val context: Context ,private val item_empty_parent: RelativeLayout, private val sentAdapterListener: SentAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sent, parent, false)
        return SentViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SentViewHolder).sentElement.setTitle(sentModels[position].getTitle())
        (holder as SentViewHolder).sentElement.setText(sentModels[position].getText())
        (holder as SentViewHolder).sentElement.getButton().setOnClickListener(View.OnClickListener {
            sendItem(
                sentModels[position].getId(),
                sentModels[position].getTitle(),
                sentModels[position].getText(),
                sentModels[position].getDate()
            )
        })

        holder.sentElement.getMenuButton()!!.setOnClickListener(View.OnClickListener { v ->
            val popupMenu = PopupMenu(
                context,
                v,
                AlertDialog.THEME_DEVICE_DEFAULT_DARK
            )

            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.sent_element_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_info -> {
                        FileTools().setSimpleDialog(
                            context,
                            sentModels[position].getTitle(),
                            sentModels[position].getDate()
                        )
                    }
                    R.id.item_delete -> {
                        deleteSent(sentModels[position].getId(), position)
                        sentAdapterListener.dataRefresh(context.getString(R.string.menu_delete_success))
                        SentFragment.checkEmpty(sentModels, item_empty_parent)
                    }
                }
                true
            }
            popupMenu.show()
        })
    }

    override fun getItemCount(): Int {
        return sentModels.size
    }

    private fun deleteSent(id: Int, position: Int) {
        DatabaseDeleteQuery().deleteSentText(context, id)
        sentModels.removeAt(position)
        notifyDataSetChanged()
    }

    private fun sendItem(id: Int, title: String, text: String, date: String) {
        val intent = Intent(context, CreateLetterActivity::class.java)
        intent.putExtra(ActivityIntentTags.LETTER_TYPE, false)
        intent.putExtra(ActivityIntentTags.LETTER_ID, id)
        intent.putExtra(ActivityIntentTags.LETTER_TITLE, title)
        intent.putExtra(ActivityIntentTags.LETTER_TEXT, text)
        intent.putExtra(ActivityIntentTags.LETTER_DATE, date)
        context.startActivity(intent)
    }

    interface SentAdapterListener {
        fun dataRefresh(msg: String?)
    }
}