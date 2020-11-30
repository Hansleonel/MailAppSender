package com.uigitdev.mailapp.tools.fileimport

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class FileTools {

    fun checkPermission(activity: Activity?, permission: String, dialogTitle: String?, dialogText: String?): Boolean {
        val GRANT_REQUEST_CODE = 0
        return if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                val alertBuilder = AlertDialog.Builder(activity)
                alertBuilder.setCancelable(true)
                alertBuilder.setTitle(dialogTitle)
                alertBuilder.setMessage(dialogText)
                alertBuilder.setPositiveButton(R.string.yes) { dialog, which ->
                    ActivityCompat.requestPermissions(
                        activity, arrayOf(permission), GRANT_REQUEST_CODE)
                }
                val alert = alertBuilder.create()
                alert.show()
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    GRANT_REQUEST_CODE
                )
            }
            false
        } else {
            true
        }
    }

    fun getFileType(uri: Uri?, context: Context): String? {
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

    fun getFileName(uri: Uri, context: Context): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }
        return result
    }

    fun emailImportError(stringBuilder: StringBuilder, line: Int, msg: String?) {
        stringBuilder
            .append("Import error")
            .append(",").append(" ").append("LINE").append(":")
            .append(" ").append(line)
            .append(" ").append("(").append(msg).append(")")
            .append("\n")
    }

    fun setSimpleDialog(context: Context?, title: String?, desc: String?) {
        AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
            .setTitle(title)
            .setMessage(desc)
            .setPositiveButton(R.string.yes) { dialog, which -> }
            .show()
    }
}