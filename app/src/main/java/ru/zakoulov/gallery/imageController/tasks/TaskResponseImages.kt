package ru.zakoulov.gallery.imageController.tasks

import ru.zakoulov.gallery.imageController.Image

/**
 * Created by Илья on 04.05.2018.
 */
interface TaskResponseImages {
    fun responseImagesDownload(images: List<Image>)
    fun responseImagesError()
}
