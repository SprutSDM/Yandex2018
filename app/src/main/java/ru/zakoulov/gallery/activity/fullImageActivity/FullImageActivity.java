package ru.zakoulov.gallery.activity.fullImageActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.DownloadImage;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 02.05.2018.
 */
public class FullImageActivity extends AppCompatActivity implements TaskResponseImages {
    int position;
    SnappyRecyclerView recyclerView;
    RvaFullImage adapter;
    LinearLayoutManager layoutManager;
    TextView textView;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getIntent().getIntExtra("position", 0); // Получение позиции изображения
        setContentView(R.layout.activity_full_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFullImage);
        setSupportActionBar(toolbar);

        setTitle(ImageController.getListImages()
                .get(position).getName());

        sdf = new SimpleDateFormat("dd MMM yyyy, EEE");

        textView = (TextView) findViewById(R.id.timeText);
        textView.setText(sdf.format(ImageController.getListImages().get(position).getDate()));

        recyclerView = (SnappyRecyclerView)findViewById(R.id.recyclerViewFullImage);
        adapter = new RvaFullImage(this, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(position);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setTitle(ImageController.getListImages()
                            .get(layoutManager.findFirstVisibleItemPosition()).getName());
                    textView.setText(sdf.format(ImageController.getListImages()
                            .get(layoutManager.findFirstVisibleItemPosition()).getDate()));
                }
            }
        });

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
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
            // Запрос прав на изменение данных на носителе для версии >= M
            int REQUEST_WRITE_STORAGE = 112;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                boolean hasPermission = (ContextCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission)
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
            }
            DownloadImage downloadImage = new DownloadImage();
            String params[] = new String[2];
            params[0] = ImageController.getListImages()
                    .get(layoutManager.findFirstVisibleItemPosition()).getPath();
            params[1] = ImageController.getListImages()
                    .get(layoutManager.findFirstVisibleItemPosition()).getName();
            downloadImage.setActivity(this);
            downloadImage.execute(params);
        }
        return super.onOptionsItemSelected(item);
    }

    /** Сохраняет загруженные картинки и уведомляет adapter об необходимости обновить содержимое */
    @Override
    public void responseImagesDownload(List<Image> images) {
        ImageController.isLoading = false;
        if (images == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        ImageController.getListImages().addAll(images);
        saveImages(images);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void saveImages(List<Image> items) {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        List<Image> images = ImageController.getListImages();
        ed.putInt("count_images", images.size());
        for (int i = images.size() - items.size(); i < images.size(); i++) {
            ed.putString("image_" + i + "_name", images.get(i).getName());
            ed.putString("image_" + i + "_path", images.get(i).getPath());
            ed.putLong("image_" + i + "_date", images.get(i).getDate().getTime());
        }
        ed.commit();
    }
}
