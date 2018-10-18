package ru.zakoulov.gallery.activity.newsFeedFragment

import android.content.Context.MODE_PRIVATE
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.imageController.Image
import ru.zakoulov.gallery.imageController.ImageController
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages

/**
 * Created by Илья on 25.04.2018.
 */
class NewsFeedFragment : Fragment(), TaskResponseImages {
    lateinit var adapter: RvaNewsFeed
    lateinit var recyclerView: RecyclerView
    lateinit var thisView: View

    var numberOfColumns = 2
    /** Возвращает true, если картинки отображены на экране  */
    var isShow = false

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        thisView = inflater.inflate(R.layout.fragment_news_feed, container, false)
        recyclerView = thisView.findViewById(R.id.recyclerViewNewsFeed)
        return thisView
    }

    /** Отображает картинки на экране  */
    fun showAllImages() {
        isShow = true
        adapter = RvaNewsFeed(thisView.context, this, ImageController.instance)
        val layoutManager = GridLayoutManager(thisView.context, numberOfColumns)
        thisView.findViewById<View>(R.id.progressBar).visibility = ProgressBar.INVISIBLE
        recyclerView.visibility = RecyclerView.VISIBLE
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    /** Сохраняет загруженные картинки и уведомляет adapter об необходимости обновить содержимое  */
    override fun responseImagesDownload(images: List<Image>) {
        ImageController.instance.isLoading = false
        ImageController.instance.listImages.addAll(images)
        saveImages(images)
        activity.runOnUiThread {
            /* Если окно не видимо, то подгружаем адаптер и показываем всё */
            if (!isShow)
                showAllImages()
            else
            /* Иначе надо показать изменения */
                adapter.notifyDataSetChanged()
        }
    }

    override fun responseImagesError() {
        activity.runOnUiThread { Toast.makeText(context, "Нет интернет соединения!", Toast.LENGTH_LONG).show() }
    }

    private fun saveImages(items: List<Image>) {
        val sPref = activity.getPreferences(MODE_PRIVATE)
        val ed = sPref.edit()
        val images = ImageController.instance.listImages
        ed.putInt("count_images", images.size)
        for (i in images.size - items.size until images.size) {
            ed.putString("image_" + i + "_name", images[i].name)
            ed.putString("image_" + i + "_path", images[i].path)
            ed.putLong("image_" + i + "_date", images[i].date.time)
        }
        ed.apply()
    }
}
