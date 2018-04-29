package ru.zakoulov.gallery.imageController;

import android.content.ClipData;

import java.util.List;

/**
 * Created by Илья on 27.04.2018.
 */
public interface TaskResponse {
    void loadDailyImageResponse(String date);
    void loadImagesUrlResponse(List<String> items);
}
