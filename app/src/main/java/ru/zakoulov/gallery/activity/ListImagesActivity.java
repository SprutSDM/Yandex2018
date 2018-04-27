package ru.zakoulov.gallery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 25.04.2018.
 */
public class ListImagesActivity extends Activity {
    ImageController imageController;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    int numberOfColumns = 2;
    boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        imageController = new ImageController(this);
    }

    public void showAllImages() {
        adapter = new RecyclerViewAdapter(this, imageController.getListImages());
        LinearLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        findViewById(R.id.progressBar).setVisibility(ProgressBar.INVISIBLE);
        recyclerView.setVisibility(RecyclerView.VISIBLE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
