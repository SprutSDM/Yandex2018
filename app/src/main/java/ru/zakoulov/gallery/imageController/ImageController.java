package ru.zakoulov.gallery.imageController;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.zakoulov.gallery.activity.ListImagesActivity;

/**
 * Created by Илья on 27.04.2018.
 */
public class ImageController implements TaskResponse {
    String rootUrl = "https://apod.nasa.gov/apod/";
    String archiveUrl = "https://apod.nasa.gov/apod/archivepix.html";

    int countImagesPerUpdate = 10; // Количество загружаемых картинок при обновлении
    int countViewedLinks = 0; // Количество посмотренных ссылок для загрузки изображений
    // != количеству элементов в списке, т.к. иногда встречаются видео, которые не сохраняются в списке.

    ListImagesActivity listImagesActivity;

    boolean isLoad = false;

    List<Image> images;
    List<String> allUrls;

    public ImageController(ListImagesActivity listImagesActivity) {
        this.listImagesActivity = listImagesActivity;
        images = new ArrayList<>();
        downloadAllUrls();
    }

    // Получение всех ссылок на страницы с картинками
    public void downloadAllUrls() {
        LoadAllUrls loadAllUrls = new LoadAllUrls();
        loadAllUrls.setTaskResponse(this);
        loadAllUrls.execute(archiveUrl);
    }

    public void downloadUrls() {
        isLoad = true;
        LoadImageUrl loadImageUrl = new LoadImageUrl();
        loadImageUrl.setTaskResponse(this);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < countImagesPerUpdate; i++) {
            urls.add(rootUrl + allUrls.get(countViewedLinks + i));
        }
        countViewedLinks += countImagesPerUpdate;
        loadImageUrl.execute(urls);
    }

    public List<Image> getListImages() {
        return images;
    }

    // Callback при загрузке всех url
    public void loadAllUrlsResponse(List<String> items) {
        allUrls = items;
        downloadUrls();
    }

    // Callback при загрузки url картинок
    public void loadImageUrlResponse(List<String> items) {
        List<String> urlsImages = items;
        for (int i = 0; i < urlsImages.size(); i++) {
            Image image = new Image();
            image.setImagePath(rootUrl + urlsImages.get(i));
            images.add(image);
        }
        listImagesActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Если окно не видимо, то подгружаем адаптер и показываем всё
                if (!listImagesActivity.isVisible())
                    listImagesActivity.showAllImages();
                else // Иначе надо показать изменения
                    listImagesActivity.getAdapter().notifyDataSetChanged();
            }
        });
        isLoad = false;
    }

    public void setIsLoad(boolean value) {
        isLoad = value;
    }

    public boolean getIsLoad() {
        return isLoad;
    }
}
