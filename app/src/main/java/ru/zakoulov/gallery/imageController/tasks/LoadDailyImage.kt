package ru.zakoulov.gallery.imageController.tasks

import android.os.AsyncTask
import android.util.Log

import org.jsoup.Jsoup

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

import ru.zakoulov.gallery.imageController.Image

class LoadDailyImage(val taskResponse: TaskResponseDailyImage) : AsyncTask<String, Void, Void>() {
    var calendar: Calendar = GregorianCalendar()

    override fun doInBackground(vararg params: String): Void? {
        val sdf = SimpleDateFormat("yyMMdd", Locale.getDefault())
        var path = params[0]
        var run = true
        var firstIteration = true
        lateinit var image: Image
        while (run) {
            try {
                run = false
                if (!firstIteration)
                    path = params[0] + "ap" + sdf.format(calendar.time) + ".html"
                firstIteration = false

                val doc = Jsoup.connect(path).get()
                val elements = doc.body().select("center").first().select("p")[1].select("a")

                val date = doc.body().select("center").first().select("p")[1].text() // Получение даты
                parseDate(date) // Парсинг даты

                if (elements.size == 0) {
                    run = true
                    calendar.add(Calendar.DAY_OF_YEAR, -1) // Переход к предыдущей картинке
                    continue
                }

                image = Image(
                        date=calendar.time,
                        path=elements.first().select("img").first().attr("src"),
                        fullPath=elements.first().attr("href"),
                        name=doc.body().select("center")[1].select("b").first().text()
                )
            } catch (e: IOException) {
                Log.d("error", e.message)
                taskResponse.responseDailyImageError()
                return null
            }

        }
        taskResponse.responseDailyImageDownload(image)
        return null
    }

    /** Из строки date сохраняет дату в calendar
     * @param date Строка с сайта, в которой написана дата публикации картинки
     */
    private fun parseDate(date: String) {
        val dates = date.split(" ")
        calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]))
        var month = 0
        // Не использую SimpleDateFormat т.к. не получается парсить месяц
        when (dates[1]) {
            "January" -> month = 0
            "February" -> month = 1
            "March" -> month = 2
            "April" -> month = 3
            "May" -> month = 4
            "June" -> month = 5
            "July" -> month = 6
            "August" -> month = 7
            "September" -> month = 8
            "October" -> month = 9
            "November" -> month = 10
            "December" -> month = 11
        }
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]))
    }
}
