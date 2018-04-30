package ru.zakoulov.gallery.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import ru.zakoulov.gallery.R;

public class MainActivity extends FragmentActivity {
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        adapterViewPager = new PageFragmentAdapter(getSupportFragmentManager(), getBaseContext());
        viewPager.setAdapter(adapterViewPager);
        //Intent intent = new Intent(this, NewsFeedFragment.class);
        //startActivity(intent);
    }
}
