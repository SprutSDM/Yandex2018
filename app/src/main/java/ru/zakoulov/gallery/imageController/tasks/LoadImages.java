package ru.zakoulov.gallery.imageController.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 27.04.2018.
 */
public class LoadImages extends AsyncTask<String, Void, List> {
    int countImages;
    Date date;
    String rootPath;

    @Override
    protected List doInBackground(String[] params) {
        List<Image> images = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        String url;
        try {
            while (images.size() < countImages) {
                Image image = new Image();
                url = rootPath + "ap" + sdf.format(calendar.getTime()) + ".html";
                Log.d("loadImages", url);
                Log.d("loadImages", images.size() + "");
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.body().select("center").first().select("p").get(1).select("a");
                if (elements.size() == 0) {
                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                    continue;
                }

                image.setDate(calendar.getTime());
                image.setPath(rootPath + elements.first().select("img").first().attr("src"));
                image.setFullPath(rootPath + elements.first().attr("href"));
                image.setName(doc.body().select("center").get(1).select("b").first().text());
                images.add(image);
                calendar.add(Calendar.DAY_OF_YEAR, -1);
            }
        } catch (IOException e) {
            Log.d("error", e.getMessage());
            ImageController.responseImagesDownload(null);
            return null;
        }
        ImageController.responseImagesDownload(images);
        return null;
    }

    public void setCountImages(int countImages) {
        this.countImages = countImages;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
