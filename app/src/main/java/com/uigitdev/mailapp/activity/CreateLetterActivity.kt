package com.uigitdev.mailapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.DatabaseGetQuery
import com.uigitdev.mailapp.database.DatabaseInsertQuery
import com.uigitdev.mailapp.database.DatabaseInsertQuery.InsertListener
import com.uigitdev.mailapp.design.CustomButton
import com.uigitdev.mailapp.design.CustomLongInputField
import com.uigitdev.mailapp.design.SendToCard
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.GmailAccount
import com.uigitdev.mailapp.model.SentModel
import com.uigitdev.mailapp.model.Task
import com.uigitdev.mailapp.tools.ActivityIntentTags
import com.uigitdev.mailapp.tools.MSharedPreferences
import com.uigitdev.mailapp.tools.MTools
import com.uigitdev.mailapp.tools.MTools.Companion.getCurrentDate
import com.uigitdev.mailapp.tools.MTools.RequestListener
import com.uigitdev.mailapp.tools.MailAPI
import com.uigitdev.mailapp.tools.MailAPI.MailListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateLetterActivity : AppCompatActivity() {
    private var item_button: CustomButton? = null
    private var item_title: CustomLongInputField? = null
    private var item_text: CustomLongInputField? = null
    private var type: Boolean = false
    private var email: Boolean = false
    private var item_send_to_all: SendToCard? = null
    private var item_send_some: SendToCard? = null
    private var editLock: Boolean = false;
    private var mSharedPreferences: MSharedPreferences? = null
    private var isSuccess = false

    companion object {
        var selectedList: ArrayList<EmailModel>? = null
    }

    override fun onBackPressed() {
        if (!editLock) {
            finish()
        } else {
            MTools.customTextSnack(
                findViewById(android.R.id.content),
                this@CreateLetterActivity,
                getString(R.string.email_sending_active)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_letter)
        setType()
        getData()
        setToolbar()
        setInputSettings()
        setSendToSettings()
        setButtonSetting()
        setButtonClick()
    }

    private fun getData() {
        type = intent.getBooleanExtra(ActivityIntentTags.LETTER_TYPE, true)
        email = intent.getBooleanExtra(ActivityIntentTags.LETTER_EMAIL, false)

        if (!type) {
            val id = intent.getIntExtra(ActivityIntentTags.LETTER_ID, -1)
            val title = intent.getStringExtra(ActivityIntentTags.LETTER_TITLE)
            val text = intent.getStringExtra(ActivityIntentTags.LETTER_TEXT)
            val date = intent.getStringExtra(ActivityIntentTags.LETTER_DATE)
            item_title!!.setText(title)
            item_text!!.setText(text)
        }

        if (email) {
            val userId = intent.getIntExtra(ActivityIntentTags.LETTER_USER_ID, -1)
            val userPictureURL = intent.getStringExtra(ActivityIntentTags.LETTER_USER_PIC)
            val userName = intent.getStringExtra(ActivityIntentTags.LETTER_USER_NAME)
            val userEmail = intent.getStringExtra(ActivityIntentTags.LETTER_USER_EMAIL)
            val userDate = intent.getStringExtra(ActivityIntentTags.LETTER_USER_DATE)

            selectedList!!.add(EmailModel(userId, userPictureURL, userName, userEmail, userDate))
            item_send_to_all!!.setSelected(false)
            item_send_some!!.setSelected(true)
            item_send_some!!.setInfoVisibility(true)
            item_send_some!!.setInfoText(selectedList!!.size.toString() + " " + getString(R.string.selected_info_text))
        }
    }

    private fun setType() {
        item_button =
            CustomButton(findViewById(R.id.item_button))
        item_title =
            CustomLongInputField(findViewById(R.id.item_title))
        item_text =
            CustomLongInputField(findViewById(R.id.item_text))
        item_send_to_all =
            SendToCard(findViewById(R.id.item_send_to_all))
        item_send_some =
            SendToCard(findViewById(R.id.item_send_some))
        mSharedPreferences = MSharedPreferences(this@CreateLetterActivity)
        selectedList = ArrayList()
    }

    @SuppressLint("ResourceType")
    private fun setInputSettings() {
        item_title!!.errorTextVisibility(false)
        item_text!!.errorTextVisibility(false)
        item_title!!.hint(getString(R.string.hint_title))
        item_text!!.hint(getString(R.string.hint_text))
        item_title!!.setLines(2)
        item_text!!.setLines(7)
        item_title!!.setVectorIcon(
            R.drawable.ic_baseline_short_text_24,
            getString(R.color.colorLabel)
        )
        item_text!!.setVectorIcon(
            R.drawable.ic_baseline_format_align_left_24,
            getString(R.color.colorLabel)
        )
    }

    private fun setSendToSettings() {
        if (!email) {
            item_send_to_all!!.setSelected(true)
        }
        item_send_to_all!!.setText(getString(R.string.all_email_addresses))
        item_send_to_all!!.getButton()!!.setOnClickListener {
            if (!editLock) {
                item_send_to_all!!.setSelected(true)
                item_send_some!!.setSelected(false)
                item_send_some!!.setInfoVisibility(false)
                selectedList!!.clear()
            }
        }

        if (!email) {
            item_send_some!!.setSelected(false)
        }
        item_send_some!!.setText(getString(R.string.select_email_addresses))
        item_send_some!!.getButton()!!.setOnClickListener {
            if (!editLock) {
                item_send_some!!.setSelected(true)
                item_send_to_all!!.setSelected(false)
                selectedList!!.clear()
                startActivity(
                    Intent(
                        this@CreateLetterActivity,
                        SelectEmailAddressesActivity::class.java
                    )
                )
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.fab_menu_item_1)
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_arrow_left_24)
    }

    private fun setButtonSetting() {
        item_button!!.setButtonText(getString(R.string.send_email_button_text))
    }

    private fun setButtonClick() {
        item_button!!.getButton().setOnClickListener {
            if (!editLock) {
                if (item_title!!.getText().isNotEmpty()) {
                    item_title!!.errorTextVisibility(false)
                    if (item_text!!.getText().isNotEmpty()) {
                        item_text!!.errorTextVisibility(false)
                        if (item_send_to_all!!.isChecked()) {
                            selectedList =
                                DatabaseGetQuery().getAllEmail(this@CreateLetterActivity)
                        }
                        if (selectedList!!.isNotEmpty()) {
                            val sentModel = SentModel(
                                item_title!!.getText(), item_text!!.getText(), getCurrentDate()
                            )
                            MTools.setRequestDialog(
                                this@CreateLetterActivity,
                                getString(R.string.send_permission_title),
                                getString(R.string.send_permission_text),
                                object : RequestListener {
                                    override fun sendRequest(isSend: Boolean) {
                                        if (isSend) {
                                            val gmailAccount = GmailAccount(mSharedPreferences!!.getStringPreferences(MSharedPreferences.PREF_KEYS.PREF_EMAIL.toString())!!, mSharedPreferences!!.getStringPreferences(MSharedPreferences.PREF_KEYS.PREF_PASS.toString())!!)

                                            if (gmailAccount.getEmail().isNotEmpty() && gmailAccount.getPassword().isNotEmpty()) {

                                                editLock = true
                                                item_button!!.setProgressVisibility(true)
                                                item_title!!.editDisable(true)
                                                item_text!!.editDisable(true)
                                                item_send_some!!.setDisable(true)
                                                item_send_to_all!!.setDisable(true)
                                                item_button!!.setButtonText(getString(R.string.sending_button_text))

                                                sendingEmail(sentModel, gmailAccount)

                                            } else {
                                                MTools().noAccountTextSnack(
                                                    findViewById<View>(
                                                        android.R.id.content
                                                    ),
                                                    this@CreateLetterActivity,
                                                    getString(R.string.enter_your_email_data)
                                                )
                                            }
                                        }
                                    }
                                })
                        } else {
                            MTools.customTextSnack(
                                findViewById(android.R.id.content),
                                this@CreateLetterActivity,
                                getString(R.string.no_recipients_selected)
                            )
                        }
                    } else {
                        item_text!!.errorTextVisibility(true)
                        item_text!!.errorText(getString(R.string.error_input_field))
                    }
                } else {
                    item_title!!.errorTextVisibility(true)
                    item_title!!.errorText(getString(R.string.error_input_field))
                }
            } else {
                MTools.customTextSnack(
                    findViewById(android.R.id.content),
                    this@CreateLetterActivity,
                    getString(R.string.email_sending_active)
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home ->
                if (!editLock) {
                    finish()
                } else {
                    MTools.customTextSnack(
                        findViewById(android.R.id.content),
                        this@CreateLetterActivity,
                        getString(R.string.email_sending_active)
                    )
                }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (item_send_some!!.isChecked()) {
            if (!selectedList!!.isEmpty()) {
                item_send_some!!.setInfoVisibility(true)
                item_send_some!!.setInfoText(
                    selectedList!!.size.toString() + " " + getString(
                        R.string.selected_info_text
                    )
                )
            } else {
                item_send_some!!.setInfoVisibility(false)
                item_send_some!!.setSelected(false)
                item_send_to_all!!.setSelected(true)
            }
        }
    }

    private fun sendingEmail(sentModel: SentModel, gmailAccount: GmailAccount) {
        val tasks = java.util.ArrayList<Task>()
        tasks.add(Task("fun-sending-email", true, 0))
        val observable = Observable.fromIterable(tasks).subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io())
        observable.subscribe(object : Observer<Task> {

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(task: Task) {
                if (task.id == "fun-sending-email") {

                    val mailAPI = MailAPI(sentModel, gmailAccount)

                    for (i in selectedList!!.indices) {
                        mailAPI.sendToData(selectedList!![i])
                        mailAPI.send(object : MailListener {
                            override fun sendEmail(value: Boolean) {
                                isSuccess = value
                            }
                        })
                    }
                }
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {
                try {
                    Thread.sleep(500)
                    Handler(Looper.getMainLooper()).post {
                        editLock = false
                        item_button!!.setProgressVisibility(false)
                        item_title!!.editDisable(false)
                        item_text!!.editDisable(false)
                        item_send_some!!.setDisable(false)
                        item_send_to_all!!.setDisable(false)
                        item_button!!.setButtonText(getString(R.string.send_email_button_text))
                        selectedList!!.clear()
                        item_title!!.setText("")
                        item_text!!.setText("")
                        item_send_some!!.setInfoVisibility(false)
                        item_send_to_all!!.setSelected(true)
                        item_send_some!!.setSelected(false)
                        if (isSuccess) {
                            MTools.customTextSnack(findViewById(android.R.id.content), this@CreateLetterActivity, getString(R.string.success_email_sent))

                            DatabaseInsertQuery().insertSentText(this@CreateLetterActivity, sentModel, object : InsertListener {
                                    override fun dataExists(text: String?) {}
                                    override fun dataSuccess() {}
                                })

                        } else {
                            MTools.customTextSnack(findViewById(android.R.id.content), this@CreateLetterActivity, getString(R.string.error_sending_text))
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        })
    }
}