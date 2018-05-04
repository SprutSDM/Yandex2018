package ru.zakoulov.gallery.imageController;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.activity.fullImageActivity.FullImageActivity;


/**
 * Created by Илья on 02.05.2018.
 */
public class DownloadImage extends AsyncTask<String, Void, Void> {
    Context context;
    FullImageActivity fullImageActivity;

    @Override
    protected Void doInBackground(String... params) {
        String path = params[0];
        String name = params[1];
        try {
            Log.d("picture", Environment.getExternalStorageDirectory() + "/SpaceGallery/" + name + ".jpg");
            File file = new File(Environment.getExternalStorageDirectory() + "/SpaceGallery");
            if (!file.exists())
                file.mkdir(); // Создание папки для сохранения картинок, если её не существует
            file = new File(Environment.getExternalStorageDirectory() + "/SpaceGallery/" + name + ".jpg");
            if (!file.exists())
                file.createNewFile(); // Создание файла, в который будем сохранять картинку
            else
                return null;

            URL url = new URL(path);

            InputStream fin = url.openStream();
            OutputStream fout = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            while (fin.read(buffer) != -1) {
                fout.write(buffer);
            }

            fin.close();
            fout.close();
            Log.d("finish", "finish save file");
            fullImageActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, R.string.finishDownload, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setActivity(FullImageActivity fullImageActivity) {
        this.fullImageActivity = fullImageActivity;
        context = fullImageActivity.getBaseContext();
    }

}
