package ru.zakoulov.gallery.imageController

import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.activity.fullImageActivity.FullImageActivity


class DownloadImage(var context: Context, var fullImageActivity: FullImageActivity) : AsyncTask<String, Void, Void>() {

    override fun doInBackground(vararg params: String): Void? {
        val path = params[0]
        val name = params[1]
        try {
            Log.d("picture", Environment.getExternalStorageDirectory().toString() + "/SpaceGallery/" + name + ".jpg")
            var file = File(Environment.getExternalStorageDirectory().toString() + "/SpaceGallery")
            if (!file.exists())
                file.mkdir() // Создание папки для сохранения картинок, если её не существует
            file = File(Environment.getExternalStorageDirectory().toString() + "/SpaceGallery/" + name + ".jpg")
            if (!file.exists())
                file.createNewFile() // Создание файла, в который будем сохранять картинку
            else {
                fullImageActivity.runOnUiThread { Toast.makeText(context, R.string.uploadedImage, Toast.LENGTH_SHORT).show() }
                return null
            }

            val url = URL(path)

            val fin = url.openStream()
            val fout = FileOutputStream(file)
            val buffer = ByteArray(4096)
            while (fin.read(buffer) != -1) {
                fout.write(buffer)
            }

            fin.close()
            fout.close()
            Log.d("finish", "finish save file")
            fullImageActivity.runOnUiThread { Toast.makeText(context, R.string.finishDownload, Toast.LENGTH_SHORT).show() }
        } catch (e: IOException) {
            e.printStackTrace()
            fullImageActivity.runOnUiThread { Toast.makeText(context, R.string.permissionDenied, Toast.LENGTH_LONG).show() }
        }

        return null
    }

    fun setActivity(fullImageActivity: FullImageActivity) {
        this.fullImageActivity = fullImageActivity
        context = fullImageActivity.baseContext
    }

}
