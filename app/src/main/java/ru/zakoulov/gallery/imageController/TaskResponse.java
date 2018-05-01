package ru.zakoulov.gallery.imageController;

import java.util.List;

/**
 * Created by Илья on 27.04.2018.
 */
public interface TaskResponse {
    void loadDailyImageResponse(Image image);
    void loadImagesResponse(List<Image> items);
}
