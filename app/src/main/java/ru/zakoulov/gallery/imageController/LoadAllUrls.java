package ru.zakoulov.gallery.imageController;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Илья on 27.04.2018.
 */
public class LoadAllUrls extends AsyncTask<String, Void, List> {
    TaskResponse taskResponse;

    @Override
    protected List doInBackground(String... params) {
        List<String> urls = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(params[0]).get();
            Elements imgs = doc.body().select("b").first().select("a[href]");
            for (Element img : imgs) {
                //Log.d("image", img.text() + " " + img.attr("href"));
                urls.add(img.attr("href"));
            }
        } catch (IOException e) {

        }
        taskResponse.loadAllUrlsResponse(urls);
        return urls;
    }

    public void setTaskResponse(TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }
}
