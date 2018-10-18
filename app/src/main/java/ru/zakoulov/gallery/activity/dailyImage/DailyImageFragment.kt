package ru.zakoulov.gallery.activity.dailyImage

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast

import com.squareup.picasso.Picasso

import java.util.Date

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.imageController.Image
import ru.zakoulov.gallery.imageController.ImageController
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage

/**
 * Created by Илья on 30.04.2018.
 */
class DailyImageFragment: Fragment(), TaskResponseDailyImage {
    private var showedImage = false
    private lateinit var thisView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        thisView = inflater.inflate(R.layout.fragment_daily_image, container, false)
        return thisView
    }

    /** Отображает картинку на экране, убирая progressBar */
    private fun showImage() {
        showedImage = true
        thisView.findViewById<View>(R.id.progressBarDailyImage).visibility = ProgressBar.INVISIBLE
        Picasso.with(thisView.context)
                .load(ImageController.instance.dailyImage.path)
                .fit()
                .centerCrop()
                .into(thisView.findViewById<View>(R.id.imageViewDailyImage) as ImageView)
    }

    /** Сохраняет картинку дня и запускает загрузку других картинок. */
    override fun responseDailyImageDownload(image: Image) {
        image.path = ImageController.instance.rootPath + image.path
        image.fullPath = ImageController.instance.rootPath + image.fullPath
        ImageController.instance.dailyImage = image
        ImageController.instance.listImages.add(image)
        saveImage(image) // Сохранение в SharedPref
        activity.runOnUiThread { showImage() }
        // Загрузка других картинок
        ImageController.instance.downloadImages(ImageController.instance.newsFeedFragment)
    }

    override fun responseDailyImageError() {
        activity.runOnUiThread { Toast.makeText(context, "Нет интернет соединения!", Toast.LENGTH_LONG).show() }
        loadSavedImages()
    }

    /** Загружает картинки с предыдущего сеанса и отображает их  */
    private fun loadSavedImages() {
        val sPref = activity.getPreferences(MODE_PRIVATE)
        val countImages = sPref.getInt("count_images", 0)
        Log.d("count_images", countImages.toString() + "")
        if (countImages == 0)
            return
        for (i in 0 until countImages) {
            val image = Image(
                    name=sPref.getString("image_" + i + "_name", ""),
                    path=sPref.getString("image_" + i + "_path", ""),
                    fullPath=sPref.getString("image_" + i + "_fullPath", ""),
                    date=Date(sPref.getLong("image_" + i + "_date", 0))
            )
            ImageController.instance.listImages.add(image)
            if (i == 0)
                ImageController.instance.dailyImage = image
        }
        activity.runOnUiThread {
            showImage()
            ImageController.instance.newsFeedFragment.showAllImages()
        }
    }

    private fun saveImage(image: Image) {
        Log.d("onDestroy", "start saving")
        val sPref = activity.getPreferences(MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putInt("count_images", 1)
        ed.putString("image_0_name", image.name)
        ed.putString("image_0_path", image.path)
        ed.putLong("image_0_date", image.date.time)
        ed.apply()
    }
}
