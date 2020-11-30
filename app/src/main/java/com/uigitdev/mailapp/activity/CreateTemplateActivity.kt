package com.uigitdev.mailapp.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.DatabaseInsertQuery
import com.uigitdev.mailapp.database.DatabaseInsertQuery.InsertListener
import com.uigitdev.mailapp.database.DatabaseUpdateQuery
import com.uigitdev.mailapp.model.SavedModel
import com.uigitdev.mailapp.tools.ActivityIntentTags
import com.uigitdev.mailapp.tools.MTools
import com.uigitdev.mailapp.tools.MTools.Companion.getCurrentDate
import com.uigitdev.mailapp.design.CustomButton
import com.uigitdev.mailapp.design.CustomLongInputField

class CreateTemplateActivity : AppCompatActivity() {
    private var item_button: CustomButton? = null
    private var item_title: CustomLongInputField? = null
    private var item_text: CustomLongInputField? = null
    private var type = false
    private var editSavedModel: SavedModel? = null

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_template)
        setType()
        getData()
        setToolbar()
        setInputSettings()
        setSaveClick()
    }

    private fun getData() {
        type = intent.getBooleanExtra(ActivityIntentTags.LETTER_TYPE, true)

        if (!type) {
            val id = intent.getIntExtra(ActivityIntentTags.LETTER_ID, -1)
            val title = intent.getStringExtra(ActivityIntentTags.LETTER_TITLE)
            val text = intent.getStringExtra(ActivityIntentTags.LETTER_TEXT)
            val date = intent.getStringExtra(ActivityIntentTags.LETTER_DATE)

            editSavedModel = SavedModel(id, title, text, date)

            item_title!!.setText(editSavedModel!!.getTitle())
            item_text!!.setText(editSavedModel!!.getText())
        }
    }

    private fun setType() {
        item_button =
            CustomButton(findViewById(R.id.item_button))
        item_title =
            CustomLongInputField(findViewById(R.id.item_title))
        item_text =
            CustomLongInputField(findViewById(R.id.item_text))
    }

    private fun setSaveClick() {
        item_button!!.getButton().setOnClickListener {
            if (item_title!!.getText().isNotEmpty()) {
                item_title!!.errorTextVisibility(false)
                if (item_text!!.getText().isNotEmpty()) {
                    item_text!!.errorTextVisibility(false)
                    val savedModel = SavedModel(
                        item_title!!.getText(),
                        item_text!!.getText(),
                        getCurrentDate()
                    )

                    if(type){
                        saveToDB(savedModel)
                    }else{
                        savedModel.setId(editSavedModel!!.getId())
                        savedModel.setDate(getCurrentDate())
                        DatabaseUpdateQuery().updateSavedModel(this@CreateTemplateActivity, savedModel)
                        MTools.customTextSnack(findViewById(android.R.id.content), this@CreateTemplateActivity, getString(R.string.update_success))
                    }

                } else {
                    item_text!!.errorTextVisibility(true)
                    item_text!!.errorText(getString(R.string.error_input_field))
                }
            } else {
                item_title!!.errorTextVisibility(true)
                item_title!!.errorText(getString(R.string.error_input_field))
            }
        }
    }

    private fun saveToDB(savedModel: SavedModel) {
        DatabaseInsertQuery().insertSavedText(
            this@CreateTemplateActivity,
            savedModel,
            object : InsertListener {
                override fun dataExists(text: String?) {
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@CreateTemplateActivity,
                        text
                    )
                }

                override fun dataSuccess() {
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@CreateTemplateActivity,
                        getString(R.string.success_added)
                    )
                    item_title!!.setText("")
                    item_text!!.setText("")
                    item_title!!.getInputField().requestFocus()
                }
            })
    }

    @SuppressLint("ResourceType")
    private fun setInputSettings() {
        item_title!!.errorTextVisibility(false)
        item_text!!.errorTextVisibility(false)
        item_title!!.hint(getString(R.string.hint_title))
        item_text!!.hint(getString(R.string.hint_text))
        item_title!!.setLines(2)
        item_text!!.setLines(7)
        item_title!!.setVectorIcon(R.drawable.ic_baseline_short_text_24, getString(R.color.colorLabel))
        item_text!!.setVectorIcon(R.drawable.ic_baseline_format_align_left_24, getString(R.color.colorLabel))
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.fab_menu_item_2)
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