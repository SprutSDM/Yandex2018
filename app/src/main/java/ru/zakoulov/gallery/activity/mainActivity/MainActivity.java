package ru.zakoulov.gallery.activity.mainActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage;

public class MainActivity extends FragmentActivity implements TaskResponseDailyImage {
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageController.load();
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.pager);
        adapterViewPager = new PageFragmentAdapter(getSupportFragmentManager(), getBaseContext());
        viewPager.setAdapter(adapterViewPager);
    }

    @Override
    public void responseDailyImageDownload(Image images) {

    }
}
