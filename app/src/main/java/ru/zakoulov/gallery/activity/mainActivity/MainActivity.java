package ru.zakoulov.gallery.activity.mainActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage;

public class MainActivity extends AppCompatActivity implements TaskResponseDailyImage {
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageController.load();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        adapterViewPager = new PageFragmentAdapter(getSupportFragmentManager(), getBaseContext());
        viewPager.setAdapter(adapterViewPager);

    }

    @Override
    public void responseDailyImageDownload(Image images) {

    }
}
