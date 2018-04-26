package ru.zakoulov.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Илья on 25.04.2018.
 */
public class ListImagesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        List<Image> images = new ArrayList<>();
        testFill(images);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        int numberOfColumns = 2;
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(images);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void testFill(List<Image> images) {
        for (int i = 0; i < 10; i++) {
            Image image = new Image();
            image.setImageID(R.drawable.image1);
            images.add(image);
        }
    }
}
