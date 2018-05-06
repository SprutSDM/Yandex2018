package ru.zakoulov.gallery.activity.fullImageActivity;

import android.app.Activity;
import android.os.Bundle;
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
        //recyclerView.dispatchNestedFling(0, 0, false);
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
            params[0] = ImageController.getListImages()
                    .get(layoutManager.findFirstVisibleItemPosition()).getPath();
            params[1] = ImageController.getListImages()
                    .get(layoutManager.findFirstVisibleItemPosition()).getName();
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
