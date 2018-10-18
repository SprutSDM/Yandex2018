package ru.zakoulov.gallery.activity.mainActivity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.imageController.ImageController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        /*
        * Сначала создаются два фрагмента: Фото дня и Лента новостей.
        * Затем создаётся imageController и в него передаются оба фрагмента.
        * После, imageController передаётся в оба фрагмента. */
        val pageFragmentAdapter = PageFragmentAdapter(supportFragmentManager, baseContext)
        viewPager.adapter = pageFragmentAdapter

        ImageController.instance.newsFeedFragment = pageFragmentAdapter.newsFeedFragment
        ImageController.instance.downloadDailyImage(pageFragmentAdapter.dailyImageFragment)
    }
}
