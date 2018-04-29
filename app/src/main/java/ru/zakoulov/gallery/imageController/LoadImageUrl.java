package ru.zakoulov.gallery.imageController;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Илья on 27.04.2018.
 */
public class LoadImageUrl extends AsyncTask<List<String>, Void, List> {
    TaskResponse taskResponse;

    @Override
    protected List doInBackground(List<String>... params) {
        List<String> urls = new ArrayList<>();
        try {
            for (int i = 0; i < params[0].size(); i++) {
                Document doc = Jsoup.connect(params[0].get(i)).get();
                //Log.d("url", params[0]);
                Elements elements = doc.body().select("center").first().select("p").get(1).select("a");
                if (elements.size() != 0)
                    urls.add(elements.first().select("img").first().attr("src"));
            }
        } catch (IOException e) {

        }
        taskResponse.loadImagesUrlResponse(urls);
        return urls;
    }

    public void setTaskResponse(TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }
}
