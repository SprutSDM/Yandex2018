package ru.zakoulov.gallery.imageController;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.zakoulov.gallery.activity.DailyPhotoFragment;
import ru.zakoulov.gallery.activity.NewsFeedFragment;

/**
 * Created by Илья on 27.04.2018.
 */
public class ImageController implements TaskResponse {
    String rootPath = "https://apod.nasa.gov/apod/";

    int countImagesPerUpdate = 10; // Количество загружаемых картинок при обновлении

    Calendar calendar;

    boolean isLoading = false;

    public Image dailyImage = null;
    static List<Image> images;

    public ImageController imageController; // Ссылка на этот же класс. Создаётся при создании

    public DailyPhotoFragment dailyPhotoFragment; // Экран с Фото дня
    public NewsFeedFragment newsFeedFragment; // Экран с Лентой

    public ImageController() {
        images = new ArrayList<>();
        calendar = new GregorianCalendar();
        downloadDailyImage();
    }

    // Загрузить фото дня для получения текущей даты
    public void downloadDailyImage() {
        LoadDailyImage loadDailyImage = new LoadDailyImage();
        loadDailyImage.setTaskResponse(this);
        loadDailyImage.execute(rootPath);
    }

    // Получение ссылок на изображения
    public void downloadImages(Date date, int count) {

        isLoading = true;
        LoadImages loadImages = new LoadImages();
        loadImages.setTaskResponse(this);
        loadImages.setCountImages(count);
        loadImages.setDate(date);
        loadImages.setRootPath(rootPath);

        loadImages.execute();
    }

    public void downloadImages(Date date) {
        downloadImages(date, countImagesPerUpdate);
    }

    public void downloadImages() {
        if (isLoading)
            return;
        calendar.setTime(images.get(images.size() - 1).getDate());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        downloadImages(calendar.getTime());
    }

    // Callback при загрузке Фото дня
    public void loadDailyImageResponse(Image image) {
        if (image == null) {
            return;
        }
        Log.d("path", image.getPath());
        Log.d("full_path", image.getFullPath());
        Log.d("date", image.getDate().toString());
        Log.d("name", image.getName());
        image.setPath(rootPath + image.getPath());
        image.setFullPath(rootPath + image.getFullPath());
        dailyImage = image;
        images.add(dailyImage);
        dailyPhotoFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dailyPhotoFragment.showImage();
                downloadImages();
            }
        });
        downloadImages();
    }

    // Callback при загрузки url картинок
    public void loadImagesResponse(List<Image> items) {
        images.addAll(items);
        newsFeedFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Если окно не видимо, то подгружаем адаптер и показываем всё
                if (!newsFeedFragment.isShow())
                    newsFeedFragment.showAllImages();
                else // Иначе надо показать изменения
                    newsFeedFragment.getAdapter().notifyDataSetChanged();
            }
        });
        isLoading = false;
    }

    public static List<Image> getListImages() {
        return images;
    }

    public boolean getIsLoad() {
        return isLoading;
    }
}
