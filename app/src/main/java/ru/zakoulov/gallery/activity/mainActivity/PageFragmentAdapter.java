package ru.zakoulov.gallery.activity.mainActivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.activity.dailyImage.DailyImageFragment;
import ru.zakoulov.gallery.activity.newsFeedFragment.NewsFeedFragment;

/**
 * Created by Илья on 30.04.2018.
 */
public class PageFragmentAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT = 2;
    Context context;

    public PageFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return DailyImageFragment.newInstance();
        if (position == 1)
            return NewsFeedFragment.newInstance();
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public String getPageTitle(int position) {
        if (position == 0)
            return context.getString(R.string.daily_photo);
        if (position == 1)
            return context.getString(R.string.feed_news);
        return "";
    }
}
