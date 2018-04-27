package ru.zakoulov.gallery.imageController;

import android.content.ClipData;

import java.util.List;

/**
 * Created by Илья on 27.04.2018.
 */
public interface TaskResponse {
    void loadAllUrlsResponse(List<String> items);
    void loadImageUrlResponse(List<String> items);
}
