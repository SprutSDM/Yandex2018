package ru.zakoulov.gallery.imageController

import ru.zakoulov.gallery.activity.newsFeedFragment.NewsFeedFragment
import ru.zakoulov.gallery.imageController.tasks.LoadDailyImage
import ru.zakoulov.gallery.imageController.tasks.LoadImages
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages
import java.util.*

/**
 * Created by Илья on 27.04.2018.
 */
class ImageController private constructor() {
    var calendar: Calendar = GregorianCalendar()
    var listImages: MutableList<Image> = ArrayList()
    var rootPath = "https://apod.nasa.gov/apod/"
    var countImagesPerUpdate = 10 // Количество загружаемых картинок при обновлении

    /** Возвращает true, если в данный момент идёт загрузка картинок  */
    var isLoading = false

    lateinit var newsFeedFragment: NewsFeedFragment
    lateinit var dailyImage: Image

    /** Загружает фото дня для получения текущей даты */
    fun downloadDailyImage(taskResponse: TaskResponseDailyImage) {
        val loadDailyImage = LoadDailyImage(taskResponse)

        loadDailyImage.execute(rootPath)
    }

    /** Загружает ссылки на изображения
     * @param count Количество, которе надо загрузить
     */
    fun downloadImages(taskResponse: TaskResponseImages, date: Date, count: Int = countImagesPerUpdate) {
        isLoading = true
        val loadImages = LoadImages(countImages=count, date=date, rootPath=rootPath, taskResponse=taskResponse)
        loadImages.execute()
    }

    /** Загружает ссылки на изображения
     * @param taskResponse Callback при загрузке ссылок
     */
    fun downloadImages(taskResponse: TaskResponseImages) {
        if (isLoading)
            return
        calendar.time = listImages[listImages.size - 1].date
        calendar.add(Calendar.DAY_OF_YEAR, -1) // Вычитание даты из за того, что хотим получить более старые картинки
        downloadImages(taskResponse, calendar.time)
    }

    companion object {
        val instance: ImageController by lazy { ImageController() }
    }
}

