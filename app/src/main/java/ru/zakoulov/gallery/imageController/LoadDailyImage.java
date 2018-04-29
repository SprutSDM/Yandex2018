package ru.zakoulov.gallery.imageController;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Илья on 27.04.2018.
 */
public class LoadDailyImage extends AsyncTask<String, Void, String> {
    TaskResponse taskResponse;

    @Override
    protected String doInBackground(String... params) {
        String date = "fail";
        try {
            Document doc = Jsoup.connect(params[0]).get();
            date = doc.body().select("center").first().select("p").get(1).text();
        } catch (IOException e) {

        }
        taskResponse.loadDailyImageResponse(date);
        return date;
    }

    public void setTaskResponse(TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }
}
