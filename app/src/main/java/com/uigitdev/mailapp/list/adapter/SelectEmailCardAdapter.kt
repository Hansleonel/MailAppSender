package com.uigitdev.mailapp.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.list.viewholder.SelectEmailCardViewHolder
import java.util.*

class SelectEmailCardAdapter(private val context: Context, private val emailModels: ArrayList<EmailModel>, private val selectEmailListener: SelectEmailListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var selectedEmailModels: ArrayList<EmailModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.design_select_email, parent, false)
        return SelectEmailCardViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SelectEmailCardViewHolder).getSelectEmailCard()!!.setData(
            emailModels[position].getPictureURL(),
            emailModels[position].getName(),
            emailModels[position].getEmail()
        )

        if (emailModels[position].isSelected()) {
            holder.getSelectEmailCard()!!.setSelected(true)
        } else {
            holder.getSelectEmailCard()!!.setSelected(false)
        }
        holder.getSelectEmailCard()!!.getButton()!!.setOnClickListener(View.OnClickListener {
                if (emailModels[position].isSelected()) {
                    selectedEmailModels.remove(emailModels[position])
                    emailModels[position].setSelected(false)
                    holder.getSelectEmailCard()!!.setSelected(false)
                } else {
                    selectedEmailModels.add(emailModels[position])
                    emailModels[position].setSelected(true)
                    holder.getSelectEmailCard()!!.setSelected(true)
                }
                selectEmailListener.selectedEmails(selectedEmailModels)
            })
    }

    override fun getItemCount(): Int {
        return emailModels.size
    }

    interface SelectEmailListener {
        fun selectedEmails(selectedEmails: ArrayList<EmailModel>?)
    }
}