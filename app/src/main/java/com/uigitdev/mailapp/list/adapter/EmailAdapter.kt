package com.uigitdev.mailapp.list.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.activity.CreateLetterActivity
import com.uigitdev.mailapp.list.viewholder.EmailViewHolder
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.tools.ActivityIntentTags
import java.util.*

class EmailAdapter(private val emailModels: ArrayList<EmailModel>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EmailViewHolder).emailElement.setImageURL(emailModels[position].getPictureURL())
        (holder as EmailViewHolder).emailElement.setEmail(emailModels[position].getEmail())
        (holder as EmailViewHolder).emailElement.setName(emailModels[position].getName())
        (holder as EmailViewHolder).emailElement.getButton()
            .setOnClickListener(View.OnClickListener {
                val intent = Intent(context, CreateLetterActivity::class.java)
                intent.putExtra(ActivityIntentTags.LETTER_TYPE, true)
                intent.putExtra(ActivityIntentTags.LETTER_EMAIL, true)
                intent.putExtra(ActivityIntentTags.LETTER_USER_ID, emailModels[position].getId())
                intent.putExtra(ActivityIntentTags.LETTER_USER_PIC, emailModels[position].getPictureURL())
                intent.putExtra(ActivityIntentTags.LETTER_USER_NAME, emailModels[position].getName())
                intent.putExtra(ActivityIntentTags.LETTER_USER_EMAIL, emailModels[position].getEmail())
                intent.putExtra(ActivityIntentTags.LETTER_USER_DATE, emailModels[position].getDate())
                Objects.requireNonNull(context).startActivity(intent)
            })
    }

    override fun getItemCount(): Int {
        return emailModels.size
    }
}