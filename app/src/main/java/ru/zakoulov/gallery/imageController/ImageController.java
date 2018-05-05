package ru.zakoulov.gallery.imageController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.zakoulov.gallery.activity.newsFeedFragment.NewsFeedFragment;
import ru.zakoulov.gallery.imageController.tasks.LoadDailyImage;
import ru.zakoulov.gallery.imageController.tasks.LoadImages;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 27.04.2018.
 */
public class ImageController {
    public static String rootPath = "https://apod.nasa.gov/apod/";

    static int countImagesPerUpdate = 10; // Количество загружаемых картинок при обновлении

    static Calendar calendar;

    public static boolean isLoading = false;

    public static Image dailyImage = null;
    static List<Image> images;

    public static ImageController imageController; // Ссылка на этот же класс. Создаётся при создании

    public static NewsFeedFragment newsFeedFragment; // Экран с Лентой

    public static void load() {
        images = new ArrayList<>();
        calendar = new GregorianCalendar();
    }

    /** Загружает фото дня для получения текущей даты */
    public static void downloadDailyImage(TaskResponseDailyImage taskResponse) {
        LoadDailyImage loadDailyImage = new LoadDailyImage();
        loadDailyImage.setTaskResponse(taskResponse);

        loadDailyImage.execute(rootPath);
    }

    /** Получает ссылки на изображения */
    public static void downloadImages(TaskResponseImages taskResponse, Date date, int count) {

        isLoading = true;
        LoadImages loadImages = new LoadImages();
        loadImages.setCountImages(count);
        loadImages.setDate(date);
        loadImages.setRootPath(rootPath);
        loadImages.setTaskResponse(taskResponse);
        loadImages.execute();
    }

    /** Загружает ссылки на картинки
     * @param date Дата, с которой начинать загружать картинки */
    public static void downloadImages(TaskResponseImages taskResponse, Date date) {
        downloadImages(taskResponse, date, countImagesPerUpdate);
    }

    public static void downloadImages(TaskResponseImages taskResponse) {
        if (isLoading)
            return;
        calendar.setTime(images.get(images.size() - 1).getDate());
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Вычитание даты из за того, что хотим получить более старые картинки
        downloadImages(taskResponse, calendar.getTime());
    }

    /** Callback при загрузке Фото дня */
    public static void responseDailyImage(Image image) {

    }

    public static List<Image> getListImages() {
        return images;
    }

    public static boolean getIsLoad() {
        return isLoading;
    }
}
