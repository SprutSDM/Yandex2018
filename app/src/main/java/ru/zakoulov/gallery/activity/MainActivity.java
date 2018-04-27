package ru.zakoulov.gallery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.activity.ListImagesActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ListImagesActivity.class);
        startActivity(intent);
    }
}
