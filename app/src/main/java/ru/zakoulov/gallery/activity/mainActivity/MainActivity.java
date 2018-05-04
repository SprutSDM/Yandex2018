package ru.zakoulov.gallery.activity.mainActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.ImageController;

public class MainActivity extends FragmentActivity {
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mainActivity", "destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mainActivity", "pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mainActivity", "stop");
    }
}
