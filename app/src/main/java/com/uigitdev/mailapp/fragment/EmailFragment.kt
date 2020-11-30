package com.uigitdev.mailapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.list.adapter.EmailAdapter
import com.uigitdev.mailapp.list.data.ListData
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.activity.AddEmailActivity
import com.uigitdev.mailapp.design.CustomButton
import java.util.*

class EmailFragment : Fragment() {
    private var emailModels: ArrayList<EmailModel>? = null
    private var adapter: EmailAdapter? = null
    private var item_empty_parent: RelativeLayout? = null
    private var design_button: CustomButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_email, container, false)
        setType(view)
        setButtonStyle()
        createOnClick()
        return view
    }

    private fun setType(view: View) {
        item_empty_parent = view.findViewById(R.id.item_empty_parent)
        design_button =
            CustomButton(view.findViewById(R.id.item_button))
    }

    private fun setButtonStyle() {
        design_button!!.setButtonText(getString(R.string.empty_button_text))
    }

    private fun setAdapter(view: View) {
        emailModels = ListData().getEmailList(context)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = EmailAdapter(emailModels!!, context!!)
        recyclerView.adapter = adapter
    }

    private fun checkEmpty() {
        if (emailModels!!.isEmpty()) {
            item_empty_parent!!.visibility = View.VISIBLE
        } else {
            item_empty_parent!!.visibility = View.GONE
        }
    }

    private fun createOnClick() {
        design_button!!.getButton()
            .setOnClickListener { startActivity(Intent(context, AddEmailActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        setAdapter(view!!)
        checkEmpty()
    }
}