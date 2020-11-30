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
import com.uigitdev.mailapp.list.adapter.SavedAdapter
import com.uigitdev.mailapp.list.adapter.SavedAdapter.SavedAdapterListener
import com.uigitdev.mailapp.list.data.ListData
import com.uigitdev.mailapp.model.SavedModel
import com.uigitdev.mailapp.activity.CreateTemplateActivity
import com.uigitdev.mailapp.design.CustomButton
import java.util.*

class SavedFragment(val savedAdapterListener: SavedAdapterListener) : Fragment() {
    private var item_empty_parent: RelativeLayout? = null
    private var design_button: CustomButton? = null
    private var savedModels: ArrayList<SavedModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_saved, container, false)
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
        savedModels = ListData().getSavedList(context!!)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        val adapter = SavedAdapter(savedModels!!, context!!, item_empty_parent!!, savedAdapterListener)
        recyclerView.adapter = adapter
    }

    private fun createOnClick() {
        design_button!!.getButton().setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CreateTemplateActivity::class.java
                )
            )
        }
    }

    companion object{
        fun checkEmpty(savedModels: ArrayList<SavedModel>, item_empty_parent: RelativeLayout) {
            if (savedModels.isEmpty()) {
                item_empty_parent.visibility = View.VISIBLE
            } else {
                item_empty_parent.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setAdapter(view!!)
        checkEmpty(savedModels!!, item_empty_parent!!)
    }
}