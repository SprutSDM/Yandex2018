package ru.zakoulov.gallery.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.activity.NewsFeedFragment;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 30.04.2018.
 */
public class PageFragmentAdapter extends FragmentPagerAdapter {
    private static int PAGE_COUNT = 2;
    Context context;
    ImageController imageController;

    public PageFragmentAdapter(FragmentManager fm, Context context, ImageController imageController) {
        super(fm);
        this.context = context;
        this.imageController = imageController;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return DailyPhotoFragment.newInstance(imageController);
        if (position == 1)
            return NewsFeedFragment.newInstance(imageController);
        /*if (position == 2)
            return NewsFeedFragment.newInstance(imageController);*/
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public String getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.daily_photo);
        }
        if (position == 1) {
            return context.getString(R.string.feed_news);
        }
        if (position == 2) {
            return context.getString(R.string.offline);
        }
        return "";
    }
}
