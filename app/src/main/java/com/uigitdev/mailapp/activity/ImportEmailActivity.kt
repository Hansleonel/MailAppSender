package com.uigitdev.mailapp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.list.adapter.EmailAdapter
import com.uigitdev.mailapp.tools.MTools
import com.uigitdev.mailapp.tools.fileimport.FileEmailImport
import com.uigitdev.mailapp.tools.fileimport.FileTools
import com.uigitdev.mailapp.design.CustomButton

class ImportEmailActivity : AppCompatActivity(), FileEmailImport.FileEmailImportListener {
    private lateinit var item_button: CustomButton
    private var info_box = false
    private lateinit var item_import_box: RelativeLayout
    private lateinit var item_import_info_content: TextView
    private lateinit var fileEmailImport: FileEmailImport
    private lateinit var adapter: EmailAdapter
    private lateinit var item_file_name: TextView
    private var isProcessing = false
    private lateinit var item_progress_parent: RelativeLayout
    private lateinit var recyclerView: RecyclerView

    override fun onBackPressed() {
        if (isProcessing) {
            MTools.customTextSnack(
                findViewById(android.R.id.content),
                this@ImportEmailActivity,
                getString(R.string.loading_active)
            )
        } else {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_email)
        setType()
        setToolbar()
        setInfoBoxContent()
        setButtonSetting()
        setAdapter()
        setInputClick()
    }

    private fun setType() {
        item_button =
            CustomButton(findViewById(R.id.item_import))
        item_import_box = findViewById(R.id.item_import_box)
        item_import_info_content = findViewById(R.id.item_import_info_content)
        item_file_name = findViewById(R.id.item_file_name)
        item_progress_parent = findViewById(R.id.item_progress_parent)
        recyclerView = findViewById(R.id.recyclerView)
        fileEmailImport = FileEmailImport(
            this@ImportEmailActivity,
            findViewById<View>(android.R.id.content)
        )
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.fab_menu_item_4)
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_arrow_left_24)
    }

    private fun setInfoBoxContent() {
        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.import_info_item_1)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_2)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_3)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_4)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_5)).append("\n")
        stringBuilder.append("\n").append(getString(R.string.import_info_item_6)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_7)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_8)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_9)).append("\n")
        stringBuilder.append(getString(R.string.import_info_item_10)).append("\n")
        stringBuilder.append("\n").append(getString(R.string.import_info_item_11)).append("\n")
        item_import_info_content.text = stringBuilder.toString()
    }

    private fun setButtonSetting() {
        item_button.setButtonText(getString(R.string.import_button_text))
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this@ImportEmailActivity)
        recyclerView.layoutManager = layoutManager
        adapter = EmailAdapter(fileEmailImport.emailModels!!, this@ImportEmailActivity)
        recyclerView.adapter = adapter
    }

    private fun setInputClick() {
        item_button.getButton().setOnClickListener {
            val permission: Boolean = FileTools().checkPermission(
                this@ImportEmailActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                getString(R.string.permission_dialog_title),
                getString(R.string.permission_dialog_text)
            )
            if (permission) {
                if (!isProcessing) {
                    fileEmailImport.openLibrary()
                } else {
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@ImportEmailActivity,
                        getString(R.string.loading_active)
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.import_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> if (isProcessing) {
                MTools.customTextSnack(
                    findViewById(android.R.id.content),
                    this@ImportEmailActivity,
                    getString(R.string.loading_active)
                )
            } else {
                finish()
            }
            R.id.item_import_info -> if (info_box) {
                item_import_box.visibility = View.GONE
                info_box = false
            } else {
                item_import_box.visibility = View.VISIBLE
                info_box = true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileEmailImport.LIBRARY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                isProcessing = true
                recyclerView.visibility = View.GONE
                item_progress_parent.visibility = View.VISIBLE
                fileEmailImport.importEmails(data.data)
                item_file_name.text = fileEmailImport.fileName
            }
        }
    }

    override fun onDataRefresh() {
        if (adapter != null) {
            adapter.notifyDataSetChanged()
            isProcessing = false
            item_progress_parent.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}