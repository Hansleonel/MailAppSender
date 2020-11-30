package com.uigitdev.mailapp.tools.fileimport

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.DatabaseInsertQuery
import com.uigitdev.mailapp.database.DatabaseInsertQuery.InsertListener
import com.uigitdev.mailapp.model.EmailModel
import com.uigitdev.mailapp.model.Task
import com.uigitdev.mailapp.tools.MTools
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class FileEmailImport(val activity: AppCompatActivity, val view: View){
    val LIBRARY_REQUEST_CODE = 1
    var fileName: String? = null
    var emailModels: ArrayList<EmailModel>? = null
    var fileEmailImportListener: FileEmailImportListener? = null

    private var importLine = 0
    private var successImport = 0
    private var isImportFailed = false
    var importInfo: String? = null
    private var errorImport: StringBuilder? = null

    init {
        fileEmailImportListener = activity as FileEmailImportListener
        emailModels = ArrayList()
    }

    fun openLibrary() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        activity.startActivityForResult(intent, LIBRARY_REQUEST_CODE)
    }

    fun onObservable(path: String?) {
        val tasks =
            ArrayList<Task>()
        tasks.add(Task("fun-import-email", true, 0))
        val observable =
            Observable
                .fromIterable(tasks)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
        observable.subscribe(object :
            Observer<Task> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(task: Task) {
                if (task.id == "fun-import-email") {
                    readText(path!!)
                }
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {
                try {
                    Thread.sleep(500)
                    Handler(Looper.getMainLooper()).post {
                        fileEmailImportListener!!.onDataRefresh()
                        if (isImportFailed) {
                            MTools.customTextSnack(
                                view,
                                activity,
                                activity.getString(R.string.error_import_failed)
                            )
                        } else {
                            FileTools().setSimpleDialog(
                                activity,
                                "Import complete",
                                importInfo
                            )
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun importEmails(uri: Uri?) {
        importLine = 0
        successImport = 0
        emailModels!!.clear()
        errorImport = java.lang.StringBuilder()
        if (FileTools().getFileType(uri, activity) == "txt") {
            fileName = FileTools().getFileName(uri!!, activity)

            var path = FileUtils(activity).getPath(uri)
            path = path!!.substring(path.indexOf(":") + 1)
            if (path.contains("emulated")) {
                path = path.substring(path.indexOf("0") + 1)
            }

            onObservable(path)
        } else {
            MTools.customTextSnack(
                view,
                activity,
                activity.getString(R.string.error_file_format)
            )
        }
    }

    private fun readText(input: String) {
        val file =
            File(Environment.getExternalStorageDirectory(), input)
        try {
            val bufferedReader =
                BufferedReader(FileReader(file))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                createObject(line!!)
            }
            bufferedReader.close()
            importInfo = if (successImport > 0) {
                "Successfully imported $successImport email addresses\n$errorImport"
            } else {
                errorImport.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            isImportFailed = true
        }
    }

    private fun createObject(line: String) {
        importLine++
        val data = line.split(",".toRegex()).toTypedArray()
        if (data.size == 4) {
            val photo = data[0]
            val name = data[1]
            val email = data[2]
            val date = data[3]
            if (photo != null && name != null && email != null && date != null) {
                if (photo.length > 0 && name.length > 0 && email.length > 0 && date.length > 0) {
                    val emailModel = EmailModel(photo, name, email, date)
                    DatabaseInsertQuery().insertEmail(
                        activity,
                        emailModel,
                        object : InsertListener {
                            override fun dataExists(text: String?) {
                                FileTools().emailImportError(errorImport!!, importLine, text)
                            }

                            override fun dataSuccess() {
                                successImport++
                                emailModels!!.add(emailModel)
                            }
                        })
                } else {
                    FileTools().emailImportError(errorImport!!, importLine, "Incorrect line format")
                }
            } else {
                FileTools().emailImportError(errorImport!!, importLine, "Incorrect line format")
            }
        } else {
            FileTools().emailImportError(errorImport!!, importLine, "Incorrect line format")
        }
    }

    interface FileEmailImportListener {
        fun onDataRefresh()
    }
}