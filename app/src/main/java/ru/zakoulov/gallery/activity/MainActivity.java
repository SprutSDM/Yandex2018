package ru.zakoulov.gallery.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.ImageController;

public class MainActivity extends FragmentActivity {
    FragmentPagerAdapter adapterViewPager;
    ImageController imageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageController = new ImageController();
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.pager);
        adapterViewPager = new PageFragmentAdapter(getSupportFragmentManager(), getBaseContext(), imageController);
        viewPager.setAdapter(adapterViewPager);
    }
}
