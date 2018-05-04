package ru.zakoulov.gallery.imageController.tasks;

import java.util.List;

import ru.zakoulov.gallery.imageController.Image;

/**
 * Created by Илья on 04.05.2018.
 */
public interface TaskResponseImages {
    void responseImagesDownload(List<Image> images);
}
