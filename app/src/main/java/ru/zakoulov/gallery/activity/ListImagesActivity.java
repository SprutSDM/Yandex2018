package ru.zakoulov.gallery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.zakoulov.gallery.Image;
import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.RecyclerViewAdapter;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 25.04.2018.
 */
public class ListImagesActivity extends Activity {
    ImageController imageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageController = new ImageController();
        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        int numberOfColumns = 2;
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, imageController.getListImages());
        LinearLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void testFill(List<Image> images) {
        for (int i = 0; i < 50; i++) {
            Image image = new Image();
            image.setImagePath("image" + ((i % 7) + 1));
            images.add(image);
        }
    }
}
