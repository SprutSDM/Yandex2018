package ru.zakoulov.gallery.imageController.tasks

import ru.zakoulov.gallery.imageController.Image


interface TaskResponseDailyImage {
    fun responseDailyImageDownload(image: Image)
    fun responseDailyImageError()
}
