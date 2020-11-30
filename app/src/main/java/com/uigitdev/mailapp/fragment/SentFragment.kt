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
import com.uigitdev.mailapp.list.adapter.SentAdapter
import com.uigitdev.mailapp.list.data.ListData
import com.uigitdev.mailapp.model.SentModel
import com.uigitdev.mailapp.tools.ActivityIntentTags
import com.uigitdev.mailapp.activity.CreateLetterActivity
import com.uigitdev.mailapp.design.CustomButton
import java.util.*
import kotlin.collections.ArrayList

class SentFragment(val sentAdapterListener: SentAdapter.SentAdapterListener) : Fragment() {
    private var sentModels: ArrayList<SentModel>? = null
    private var adapter: SentAdapter? = null
    private var item_empty_parent: RelativeLayout? = null
    private var design_button: CustomButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sent, container, false)
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

    private fun createOnClick() {
        design_button!!.getButton().setOnClickListener {
            val intent = Intent(context, CreateLetterActivity::class.java)
            intent.putExtra(ActivityIntentTags.LETTER_TYPE, true)
            Objects.requireNonNull(context!!).startActivity(intent)
        }
    }

    private fun setAdapter(view: View) {
        sentModels = ListData().getSentList(context)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = SentAdapter(sentModels!!, context!!, item_empty_parent!!, sentAdapterListener)
        recyclerView.adapter = adapter
    }

    companion object{
        fun checkEmpty(sentModels: ArrayList<SentModel>, item_empty_parent: RelativeLayout) {
            if (sentModels!!.isEmpty()) {
                item_empty_parent!!.visibility = View.VISIBLE
            } else {
                item_empty_parent!!.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setAdapter(view!!)
        checkEmpty(sentModels!!, item_empty_parent!!)
    }
}