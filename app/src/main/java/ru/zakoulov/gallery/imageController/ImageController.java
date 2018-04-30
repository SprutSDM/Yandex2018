package ru.zakoulov.gallery.imageController;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ru.zakoulov.gallery.activity.DailyPhotoFragment;
import ru.zakoulov.gallery.activity.NewsFeedFragment;

/**
 * Created by Илья on 27.04.2018.
 */
public class ImageController implements TaskResponse {
    String rootUrl = "https://apod.nasa.gov/apod/";

    int countImagesPerUpdate = 10; // Количество загружаемых картинок при обновлении
    int countViewedLinks = 0; // Количество посмотренных ссылок для загрузки изображений
    // != количеству элементов в списке, т.к. иногда встречаются видео, которые не сохраняются в списке.

    Calendar calendar;

    boolean isLoad = false;

    List<Image> images;

    public DailyPhotoFragment dailyPhotoFragment; // Экран с Фото дня
    public NewsFeedFragment newsFeedFragment; // Экран с Лентой

    public ImageController() {
        images = new ArrayList<>();
        calendar = new GregorianCalendar();
        downloadDailyImage();
    }

    // Получение всех ссылок на страницы с картинками
    public void downloadDailyImage() {
        LoadDailyImage loadDailyImage = new LoadDailyImage();
        loadDailyImage.setTaskResponse(this);
        loadDailyImage.execute(rootUrl);
    }

    // Получение ссылок на изображения
    public void downloadImagesUrl() {
        isLoad = true;
        LoadImageUrl loadImageUrl = new LoadImageUrl();
        loadImageUrl.setTaskResponse(this);
        List<String> urls = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        for (int i = 0; i < countImagesPerUpdate; i++) {
            urls.add(rootUrl + "ap" + simpleDateFormat.format(calendar.getTime()) + ".html");
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        countViewedLinks += countImagesPerUpdate;
        loadImageUrl.execute(urls);
    }

    // Callback при загрузке Фото дня
    public void loadDailyImageResponse(String date) {
        Log.d("date", "." + date + ".");
        String[] dates = date.split(" ");
        calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
        int month = 0;
        // Не использую SimpleDateFormat т.к. не получается парсить месяц
        switch (dates[1]) {
            case "January":
                month = 0; break;
            case "February":
                month = 1; break;
            case "March":
                month = 2; break;
            case "April":
                month = 3; break;
            case "May":
                month = 4; break;
            case "June":
                month = 5; break;
            case "July":
                month = 6; break;
            case "August":
                month = 7; break;
            case "September":
                month = 8; break;
            case "October":
                month = 9; break;
            case "November":
                month = 10; break;
            case "December":
                month = 11; break;
        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));
        downloadImagesUrl();
    }

    // Callback при загрузки url картинок
    public void loadImagesUrlResponse(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            Image image = new Image();
            image.setImagePath(rootUrl + items.get(i));
            images.add(image);
        }
        newsFeedFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!dailyPhotoFragment.isShow())
                    dailyPhotoFragment.showImage();
                // Если окно не видимо, то подгружаем адаптер и показываем всё
                if (!newsFeedFragment.isShow())
                    newsFeedFragment.showAllImages();
                else // Иначе надо показать изменения
                    newsFeedFragment.getAdapter().notifyDataSetChanged();
            }
        });
        isLoad = false;
    }

    public List<Image> getListImages() {
        return images;
    }

    public boolean getIsLoad() {
        return isLoad;
    }
}
