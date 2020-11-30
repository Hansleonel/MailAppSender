package com.uigitdev.mailapp.tools

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.activity.AccountActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MTools {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDate(): String {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
            return dateFormat.format(Calendar.getInstance().time)
        }

        fun customTextSnack(view: View?, context: Context, msg: String?) {
            Snackbar.make(view!!, msg!!, Snackbar.LENGTH_LONG)
                .setAction(R.string.close, View.OnClickListener { })
                .setActionTextColor(context.resources.getColor(R.color.colorAccent)).show()
        }

        fun setRequestDialog(context: Context?, title: String?, desc: String?, requestListener: RequestListener) {
            AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle(title)
                .setMessage(desc)
                .setPositiveButton(
                    android.R.string.yes) {
                        dialog, which -> requestListener.sendRequest(true) }
                .setNegativeButton(
                    android.R.string.no
                ) { dialog, which -> requestListener.sendRequest(false) }.show()
        }
    }

    fun noAccountTextSnack(view: View?, context: Context, msg: String?) {
        Snackbar.make(view!!, msg!!, Snackbar.LENGTH_LONG)
            .setAction(
                R.string.add_account
            ) { context.startActivity(Intent(context, AccountActivity::class.java)) }
            .setActionTextColor(context.resources.getColor(R.color.colorAccent)).show()
    }

    interface RequestListener {
        fun sendRequest(isSend: Boolean)
    }
}