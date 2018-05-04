package ru.zakoulov.gallery.imageController;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.zakoulov.gallery.activity.dailyImage.DailyImageFragment;
import ru.zakoulov.gallery.activity.newsFeedFragment.NewsFeedFragment;
import ru.zakoulov.gallery.imageController.tasks.LoadDailyImage;
import ru.zakoulov.gallery.imageController.tasks.LoadImages;

/**
 * Created by Илья on 27.04.2018.
 */
public class ImageController {
    static String rootPath = "https://apod.nasa.gov/apod/";

    static int countImagesPerUpdate = 10; // Количество загружаемых картинок при обновлении

    static Calendar calendar;

    static boolean isLoading = false;

    public static Image dailyImage = null;
    static List<Image> images;

    public static ImageController imageController; // Ссылка на этот же класс. Создаётся при создании

    public static DailyImageFragment dailyPhotoFragment; // Экран с Фото дня
    public static NewsFeedFragment newsFeedFragment; // Экран с Лентой

    public static void load() {
        images = new ArrayList<>();
        calendar = new GregorianCalendar();
        downloadDailyImage();
    }

    /** Загружает фото дня для получения текущей даты */
    public static void downloadDailyImage() {
        LoadDailyImage loadDailyImage = new LoadDailyImage();
        loadDailyImage.execute(rootPath);
    }

    /** Получает ссылки на изображения */
    public static void downloadImages(Date date, int count) {

        isLoading = true;
        LoadImages loadImages = new LoadImages();
        loadImages.setCountImages(count);
        loadImages.setDate(date);
        loadImages.setRootPath(rootPath);

        loadImages.execute();
    }

    /** Загружает ссылки на картинки
     * @param date Дата, с которой начинать загружать картинки */
    public static void downloadImages(Date date) {
        downloadImages(date, countImagesPerUpdate);
    }

    public static void downloadImages() {
        if (isLoading)
            return;
        calendar.setTime(images.get(images.size() - 1).getDate());
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Вычитание даты из за того, что хотим получить более старые картинки
        downloadImages(calendar.getTime());
    }

    /** Callback при загрузке Фото дня */
    public static void responseDailyImage(Image image) {
        if (image == null)
            return;
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

    /** Callback при загрузки url картинок */
    public static void responseImagesDownload(List<Image> items) {
        images.addAll(items);
        newsFeedFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /* Если окно не видимо, то подгружаем адаптер и показываем всё */
                if (!newsFeedFragment.isShow())
                    newsFeedFragment.showAllImages();
                else /* Иначе надо показать изменения */
                    newsFeedFragment.getAdapter().notifyDataSetChanged();
            }
        });
        isLoading = false;
    }

    public static List<Image> getListImages() {
        return images;
    }

    public static boolean getIsLoad() {
        return isLoading;
    }
}
