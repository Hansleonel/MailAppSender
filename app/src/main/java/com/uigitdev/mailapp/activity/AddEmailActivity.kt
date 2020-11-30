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
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.tools.MTools
import com.uigitdev.mailapp.tools.MTools.Companion.getCurrentDate
import com.uigitdev.mailapp.design.CustomButton
import com.uigitdev.mailapp.design.CustomInputField

class AddEmailActivity : AppCompatActivity() {
    private var item_button: CustomButton? = null
    private var item_picture: CustomInputField? = null
    private var item_name: CustomInputField? = null
    private var item_email: CustomInputField? = null

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_email)
        setType()
        setToolbar()
        setInputDesign()
        setSaveClick()
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.fab_menu_item_3)
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_arrow_left_24)
    }

    private fun setType() {
        item_button =
            CustomButton(findViewById(R.id.item_button))
        item_picture =
            CustomInputField(findViewById(R.id.item_picture))
        item_name =
            CustomInputField(findViewById(R.id.item_name))
        item_email =
            CustomInputField(findViewById(R.id.item_email))
    }

    @SuppressLint("ResourceType")
    private fun setInputDesign() {
        item_picture!!.errorTextVisibility(false)
        item_name!!.errorTextVisibility(false)
        item_email!!.errorTextVisibility(false)

        item_picture!!.hint(getString(R.string.hint_picture_url))
        item_name!!.hint(getString(R.string.hint_name))
        item_email!!.hint(getString(R.string.hint_email))

        item_picture!!.setVectorIcon(R.drawable.ic_baseline_image_24, getString(R.color.colorLabel))
        item_name!!.setVectorIcon(R.drawable.ic_baseline_text_format_24, getString(R.color.colorLabel))
        item_email!!.setVectorIcon(R.drawable.ic_baseline_alternate_email_24, getString(R.color.colorLabel))
    }

    private fun setSaveClick() {
        item_button!!.getButton().setOnClickListener {
            if (item_picture!!.getText().isNotEmpty()) {
                item_picture!!.errorTextVisibility(false)
                if (item_name!!.getText().isNotEmpty()) {
                    item_name!!.errorTextVisibility(false)
                    if (item_email!!.getText().isNotEmpty()) {
                        item_email!!.errorTextVisibility(false)
                        val emailModel = EmailModel(
                            item_picture!!.getText(),
                            item_name!!.getText(),
                            item_email!!.getText(),
                            getCurrentDate()
                        )
                        saveToDB(emailModel)
                    } else {
                        item_email!!.errorTextVisibility(true)
                        item_email!!.errorText(getString(R.string.error_input_field))
                    }
                } else {
                    item_name!!.errorTextVisibility(true)
                    item_name!!.errorText(getString(R.string.error_input_field))
                }
            } else {
                item_picture!!.errorTextVisibility(true)
                item_picture!!.errorText(getString(R.string.error_input_field))
            }
        }
    }

    private fun saveToDB(emailModel: EmailModel) {
        DatabaseInsertQuery().insertEmail(
            this@AddEmailActivity,
            emailModel,
            object : InsertListener {
                override fun dataExists(text: String?) {
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@AddEmailActivity,
                        text
                    )
                }

                override fun dataSuccess() {
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@AddEmailActivity,
                        getString(R.string.success_added)
                    )
                    item_picture!!.setText("")
                    item_name!!.setText("")
                    item_email!!.setText("")
                    item_picture!!.getInputField()!!.requestFocus()
                }
            })
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