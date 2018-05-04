package ru.zakoulov.gallery.imageController.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 27.04.2018.
 */
public class LoadDailyImage extends AsyncTask<String, Void, Image> {
    Calendar calendar = null;
    TaskResponseDailyImage taskResponse;

    @Override
    protected Image doInBackground(String... params) {
        Image image = new Image();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        String path = params[0];
        boolean run = true;
        while (run) {
            try {
                run = false;
                if (calendar != null) // Выполняется в случае, когда идёт повторная итерация и надо загружать другую картинку
                    path = params[0] + "ap" + sdf.format(calendar.getTime()) + ".html";
                else
                    calendar = new GregorianCalendar();

                Document doc = Jsoup.connect(path).get();
                Elements elements = doc.body().select("center").first().select("p").get(1).select("a");

                String date = doc.body().select("center").first().select("p").get(1).text(); // Получение даты
                parseDate(date); // Парсинг даты

                if (elements.size() == 0) {
                    run = true;
                    calendar.add(Calendar.DAY_OF_YEAR, -1); // Переход к предыдущей картинке
                    continue;
                }
                image.setDate(calendar.getTime());
                image.setPath(elements.first().select("img").first().attr("src"));
                image.setFullPath(elements.first().attr("href"));
                image.setName(doc.body().select("center").get(1).select("b").first().text());
            } catch (IOException e) {
                Log.d("error", e.getMessage());
                taskResponse.responseDailyImageDownload(null);
                return null;
            }
        }
        taskResponse.responseDailyImageDownload(image);
        return image;
    }

    private void parseDate(String date) {
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
    }

    public void setTaskResponse(TaskResponseDailyImage taskResponse) {
        this.taskResponse = taskResponse;
    }
}
