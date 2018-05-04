package ru.zakoulov.gallery.activity.fullImageActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.DownloadImage;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 02.05.2018.
 */
public class FullImageActivity extends Activity implements TaskResponseImages {
    int position;
    RecyclerView recyclerView;
    RvaFullImage adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getIntent().getIntExtra("position", 0); // Получение позиции изображения
        setContentView(R.layout.activity_full_image);

        recyclerView = findViewById(R.id.recyclerViewFullImage);
        adapter = new RvaFullImage(this, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
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
            params[0] = ImageController.getListImages().get(position).getFullPath();
            params[1] = ImageController.getListImages().get(position).getName();
            downloadImage.setActivity(this);
            downloadImage.execute(params);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responseImagesDownload(List<Image> images) {
        ImageController.getListImages().addAll(images);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        ImageController.isLoading = false;
    }
}
