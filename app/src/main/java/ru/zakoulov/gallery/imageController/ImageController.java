package ru.zakoulov.gallery.imageController;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.zakoulov.gallery.Image;

/**
 * Created by Илья on 27.04.2018.
 */
public class ImageController {
    String rootUrl = "https://apod.nasa.gov/apod/";
    String archiveUrl = "https://apod.nasa.gov/apod/archivepix.html";

    List<Image> images;
    List<String> allUrls;

    public ImageController() {
        images = new ArrayList<>();
        downloadAllUrls();
        downloadUrls();
    }

    // Получение всех ссылок на страницы с картинками
    public void downloadAllUrls() {
        LoadAllUrls loadAllUrls = new LoadAllUrls();
        loadAllUrls.execute(archiveUrl);
        try {
            allUrls = loadAllUrls.get();
            Log.d("abacaba", allUrls.get(0));
            downloadUrls();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void downloadUrls() {
        LoadImageUrl loadImageUrl = new LoadImageUrl();
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            urls.add(rootUrl + allUrls.get(images.size() + i));
        }
        Log.d("url", rootUrl + allUrls.get(images.size() + 0));
        loadImageUrl.execute(urls);
        try {
            List<String> urlsImages = loadImageUrl.get();
            for (int i = 0; i < urlsImages.size(); i++) {
                Image image = new Image();
                image.setImagePath(rootUrl + urlsImages.get(i));
                images.add(image);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Image> getListImages() {
        return images;
    }
}
