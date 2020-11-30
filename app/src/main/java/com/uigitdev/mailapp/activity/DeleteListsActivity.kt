package com.uigitdev.mailapp.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.DatabaseDeleteQuery
import com.uigitdev.mailapp.database.DatabaseKeys
import com.uigitdev.mailapp.tools.MTools
import com.uigitdev.mailapp.design.CustomButton

class DeleteListsActivity : AppCompatActivity() {
    private var item_delete_sent: CustomButton? = null
    private var item_delete_emails: CustomButton? = null
    private var item_delete_saved: CustomButton? = null

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_lists)
        setType()
        setToolbar()
        setButtonStyles()
        setButtonClick()
    }

    private fun setType() {
        item_delete_sent =
            CustomButton(findViewById(R.id.item_delete_sent))
        item_delete_emails =
            CustomButton(findViewById(R.id.item_delete_emails))
        item_delete_saved =
            CustomButton(findViewById(R.id.item_delete_saved))
    }

    private fun setButtonClick() {
        val emailTable = DatabaseKeys.EMAIL.EMAIL_TABLE.toString()
        val savedTable = DatabaseKeys.SAVED.SAVED_TABLE.toString()
        val sentTable = DatabaseKeys.SENT.SENT_TABLE.toString()
        item_delete_sent!!.getButton().setOnClickListener {
            DatabaseDeleteQuery().deleteTableList(this@DeleteListsActivity, sentTable)
            deletedText()
        }
        item_delete_emails!!.getButton()
            .setOnClickListener {
                DatabaseDeleteQuery().deleteTableList(this@DeleteListsActivity, emailTable)
                deletedText()
            }
        item_delete_saved!!.getButton()
            .setOnClickListener {
                DatabaseDeleteQuery().deleteTableList(this@DeleteListsActivity, savedTable)
                deletedText()
            }
    }

    private fun deletedText() {
        MTools.customTextSnack(
            findViewById(android.R.id.content),
            this@DeleteListsActivity,
            getString(R.string.deleted_data_success)
        )
    }

    private fun setButtonStyles() {
        item_delete_sent!!.setButtonText(getString(R.string.delete_button_sent))
        item_delete_emails!!.setButtonText(getString(R.string.delete_button_email))
        item_delete_saved!!.setButtonText(getString(R.string.delete_button_saved))
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.fab_menu_item_5)
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_arrow_left_24)
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
}