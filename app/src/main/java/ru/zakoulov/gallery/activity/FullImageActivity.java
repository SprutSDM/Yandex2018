package ru.zakoulov.gallery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.DownloadImage;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 02.05.2018.
 */
public class FullImageActivity extends Activity {
    ImageController imageController;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getIntent().getIntExtra("position", 0); // Получение позиции изображения
        setContentView(R.layout.activity_full_image);
        setTitle(imageController.getListImages().get(position).getName());
        PhotoView photoView = findViewById(R.id.fullImage);
        Picasso.with(getBaseContext()).load(imageController.getListImages().get(position).getPath())
                .into(photoView); // Здесь не загружается картинка в более высоком качестве, т.к. у них изначально высокое качество.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_full_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.download) {
            DownloadImage downloadImage = new DownloadImage();
            String params[] = new String[2];
            params[0] = imageController.getListImages().get(position).getFullPath();
            params[1] = imageController.getListImages().get(position).getName();
            downloadImage.setActivity(this);
            downloadImage.execute(params);
        }
        return super.onOptionsItemSelected(item);
    }
}
