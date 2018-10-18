package ru.zakoulov.gallery.activity.mainActivity

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.activity.dailyImage.DailyImageFragment
import ru.zakoulov.gallery.activity.newsFeedFragment.NewsFeedFragment

const val PAGE_COUNT = 2

class PageFragmentAdapter(fm: FragmentManager,
                          var context: Context) : FragmentPagerAdapter(fm) {
    val dailyImageFragment = DailyImageFragment()
    val newsFeedFragment = NewsFeedFragment()

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return dailyImageFragment
        return newsFeedFragment
    }

    override fun getPageTitle(position: Int): String {
        if (position == 0)
            return context.getString(R.string.daily_photo)
        return context.getString(R.string.feed_news)
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }
}
