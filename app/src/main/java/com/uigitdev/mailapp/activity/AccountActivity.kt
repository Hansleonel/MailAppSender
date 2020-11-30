package com.uigitdev.mailapp.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.design.CustomButton
import com.uigitdev.mailapp.design.CustomInputField
import com.uigitdev.mailapp.model.GmailAccount
import com.uigitdev.mailapp.tools.MSharedPreferences
import com.uigitdev.mailapp.tools.MTools

class AccountActivity : AppCompatActivity() {
    private var item_button: CustomButton? = null
    private var item_email: CustomInputField? = null
    private var item_password: CustomInputField? = null

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setToolbar()
        setType()
        setInputSetting()
        setSaveClick()
    }

    private fun setSaveClick() {
        item_button!!.getButton().setOnClickListener {
            val email = item_email!!.getText()
            val password = item_password!!.getText()
            if (email.isNotEmpty()) {
                item_email!!.errorTextVisibility(false)
                if (password.isNotEmpty()) {
                    item_button!!.setProgressVisibility(true)
                    item_password!!.errorTextVisibility(false)
                    val mail = GmailAccount(email, password)
                    saveAccountData(mail)
                    item_button!!.setProgressVisibility(false)
                    item_email!!.setText("")
                    item_password!!.setText("")
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@AccountActivity,
                        getString(R.string.account_saved_data)
                    )
                } else {
                    item_password!!.errorTextVisibility(true)
                }
            } else {
                item_email!!.errorTextVisibility(true)
            }
        }
    }

    private fun saveAccountData(mail: GmailAccount) {
        MSharedPreferences(this@AccountActivity).setStringPreferences(
            MSharedPreferences.PREF_KEYS.PREF_EMAIL.toString(),
            mail.getEmail()
        )
        MSharedPreferences(this@AccountActivity).setStringPreferences(
            MSharedPreferences.PREF_KEYS.PREF_PASS.toString(),
            mail.getPassword()
        )
    }

    @SuppressLint("ResourceType")
    private fun setInputSetting() {
        item_email!!.hint(getString(R.string.account_email_hint))
        item_password!!.hint(getString(R.string.account_password_hint))
        item_email!!.errorTextVisibility(false)
        item_password!!.errorTextVisibility(false)
        item_email!!.setVectorIcon(
            R.drawable.ic_baseline_alternate_email_24,
            getString(R.color.colorLabel)
        )
        item_password!!.setVectorIcon(R.drawable.ic_baseline_lock_24, getString(R.color.colorLabel))
        item_email!!.errorText(getString(R.string.error_input_field))
        item_password!!.errorText(getString(R.string.error_input_field))
    }

    private fun setType() {
        item_button = CustomButton(findViewById(R.id.item_button))
        item_email = CustomInputField(findViewById(R.id.item_email))
        item_password = CustomInputField(findViewById(R.id.item_password))
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.email_account)
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