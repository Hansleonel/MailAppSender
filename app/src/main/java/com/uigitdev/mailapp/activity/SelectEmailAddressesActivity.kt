package com.uigitdev.mailapp.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.list.data.ListData
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.tools.MTools
import com.uigitdev.mailapp.design.CustomButton
import com.uigitdev.mailapp.list.adapter.SelectEmailCardAdapter
import java.util.*

class SelectEmailAddressesActivity : AppCompatActivity(), SelectEmailCardAdapter.SelectEmailListener {
    private var emailModels: ArrayList<EmailModel>? = null
    private var item_button: CustomButton? = null
    private var item_empty_parent: RelativeLayout? = null
    private var selectedEmails: ArrayList<EmailModel>? = null

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_email_addresses)
        setType()
        setToolbar()
        setAdapter()
        checkEmpty()
        setOnClickSave()
    }

    private fun setOnClickSave() {
        item_button!!.getButton().setOnClickListener {
            if (!selectedEmails!!.isEmpty()) {
                CreateLetterActivity.selectedList = selectedEmails
                finish()
            } else {
                CreateLetterActivity.selectedList!!.clear()
                MTools.customTextSnack(
                    findViewById(android.R.id.content),
                    this@SelectEmailAddressesActivity,
                    getString(R.string.no_selected_email)
                )
            }
        }
    }

    private fun setType() {
        emailModels = ArrayList()
        selectedEmails = ArrayList()
        item_button =
            CustomButton(findViewById(R.id.item_button))
        item_empty_parent = findViewById(R.id.item_empty_parent)
    }

    private fun checkEmpty() {
        if (emailModels!!.isEmpty()) {
            item_empty_parent!!.visibility = View.VISIBLE
        } else {
            item_empty_parent!!.visibility = View.GONE
        }
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.select_email_addresses)
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_arrow_left_24)
    }

    private fun setAdapter() {
        emailModels = ListData()
            .getEmailList(this@SelectEmailAddressesActivity)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager =
            LinearLayoutManager(this@SelectEmailAddressesActivity)
        recyclerView.layoutManager = layoutManager
        val adapter =
            SelectEmailCardAdapter(
                this@SelectEmailAddressesActivity,
                emailModels!!,
                this@SelectEmailAddressesActivity
            )
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun selectedEmails(selectedEmails: ArrayList<EmailModel>?) {
        this.selectedEmails = selectedEmails
    }
}