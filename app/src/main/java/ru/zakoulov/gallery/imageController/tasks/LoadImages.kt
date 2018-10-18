package ru.zakoulov.gallery.imageController.tasks

import android.os.AsyncTask
import android.util.Log

import org.jsoup.Jsoup

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

import ru.zakoulov.gallery.imageController.Image

/**
 * Created by Илья on 27.04.2018.
 */
class LoadImages(val countImages: Int = 0,
                 val date: Date,
                 val rootPath: String,
                 val taskResponse: TaskResponseImages) : AsyncTask<String, Void, Void?>() {

    override fun doInBackground(params: Array<String>): Void? {
        val images = ArrayList<Image>()
        val calendar = GregorianCalendar()
        calendar.time = date
        val sdf = SimpleDateFormat("yyMMdd", Locale.getDefault())
        var url: String
        try {
            while (images.size < countImages) {
                url = rootPath + "ap" + sdf.format(calendar.time) + ".html"
                Log.d("loadImages", url)
                Log.d("loadImages", images.size.toString() + "")
                val doc = Jsoup.connect(url).get()
                val elements = doc.body().select("center").first().select("p")[1].select("a")
                Log.d("loadImages", elements.toString() + "")
                if (elements.size == 0 || !elements.first().parent().`is`("p")) {
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                    continue
                }
                val image = Image(
                    date=calendar.time,
                    path=rootPath+elements.first().select("img").first().attr("src"),
                    fullPath=rootPath+elements.first().attr("href"),
                    name=doc.body().select("center")[1].select("b").first().text()
                )
                images.add(image)
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            }
        } catch (e: IOException) {
            Log.d("error", e.message)
            taskResponse.responseImagesError()
            return null
        }
        taskResponse.responseImagesDownload(images)
        return null
    }
}
