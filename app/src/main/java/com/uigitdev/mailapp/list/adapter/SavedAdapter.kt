package com.uigitdev.mailapp.list.adapter

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.DatabaseDeleteQuery
import com.uigitdev.mailapp.fragment.SavedFragment
import com.uigitdev.mailapp.list.viewholder.SavedViewHolder
import com.uigitdev.mailapp.model.SavedModel
import com.uigitdev.mailapp.tools.ActivityIntentTags
import com.uigitdev.mailapp.activity.CreateLetterActivity
import com.uigitdev.mailapp.activity.CreateTemplateActivity
import java.util.*

class SavedAdapter(
    private val savedModels: ArrayList<SavedModel>,
    private val context: Context,
    private val item_empty_parent: RelativeLayout,
    private val savedAdapterListener: SavedAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved, parent, false)
        return SavedViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SavedViewHolder).savedElement.setTitle(savedModels[position].getTitle())
        (holder as SavedViewHolder).savedElement.setText(savedModels[position].getText())
        (holder as SavedViewHolder).savedElement.getButton()
            .setOnClickListener(View.OnClickListener {
                sendItem(
                    savedModels[position].getId(),
                    savedModels[position].getTitle(),
                    savedModels[position].getText(),
                    savedModels[position].getDate()
                )
            })

        holder.savedElement.getMenuButton()!!.setOnClickListener(View.OnClickListener { v ->
            val popupMenu = PopupMenu(context, v, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.saved_element_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_edit -> itemEdit(
                        savedModels[position].getId(),
                        savedModels[position].getTitle(),
                        savedModels[position].getText(),
                        savedModels[position].getDate()
                    )
                    R.id.item_copy -> itemCopy(
                        savedModels[position].getTitle(),
                        savedModels[position].getText()
                    )
                    R.id.item_delete -> itemDelete(
                        savedModels[position].getId(),
                        position
                    )
                }
                true
            }
            popupMenu.show()
        })
    }

    override fun getItemCount(): Int {
        return savedModels.size
    }

    private fun itemCopy(title: String, text: String) {
        val sb = StringBuffer()
        sb.append(title).append("\n\n").append(text)
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("saved-text-copy", sb.toString())
        Objects.requireNonNull(clipboard)
            .setPrimaryClip(clip)
        savedAdapterListener.menuItemClick(context.getString(R.string.menu_copy_success))
    }

    private fun itemDelete(id: Int, position: Int) {
        DatabaseDeleteQuery().deleteSavedText(context, id)
        savedModels.removeAt(position)
        notifyDataSetChanged()
        savedAdapterListener.menuItemClick(context.getString(R.string.menu_delete_success))
        SavedFragment.checkEmpty(savedModels, item_empty_parent)
    }

    private fun itemEdit(id: Int, title: String, text: String, date: String) {
        val intent = Intent(context, CreateTemplateActivity::class.java)
        intent.putExtra(ActivityIntentTags.LETTER_TYPE, false)
        intent.putExtra(ActivityIntentTags.LETTER_ID, id)
        intent.putExtra(ActivityIntentTags.LETTER_TITLE, title)
        intent.putExtra(ActivityIntentTags.LETTER_TEXT, text)
        intent.putExtra(ActivityIntentTags.LETTER_DATE, date)
        context.startActivity(intent)
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

    interface SavedAdapterListener {
        fun menuItemClick(msg: String?)
    }
}