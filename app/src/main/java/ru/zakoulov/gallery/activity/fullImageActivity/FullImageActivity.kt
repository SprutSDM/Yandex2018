package ru.zakoulov.gallery.activity.fullImageActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

import java.text.SimpleDateFormat

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.imageController.DownloadImage
import ru.zakoulov.gallery.imageController.Image
import ru.zakoulov.gallery.imageController.ImageController
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages
import java.util.*

class FullImageActivity: AppCompatActivity(), TaskResponseImages {
    init {}
    var position: Int = 0
    lateinit var recyclerView: SnappyRecyclerView
    lateinit var adapter: RvaFullImage
    lateinit var layoutManager: LinearLayoutManager
    lateinit var textView: TextView
    lateinit var sdf: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = intent.getIntExtra("position", 0) // Получение позиции изображения
        setContentView(R.layout.activity_full_image)

        val toolbar = findViewById<Toolbar>(R.id.toolbarFullImage)
        setSupportActionBar(toolbar)

        title = ImageController.instance.listImages[position].name

        sdf = SimpleDateFormat("dd MMM yyyy, EEE", Locale.getDefault())

        textView = findViewById(R.id.timeText)
        textView.text = sdf.format(ImageController.instance.listImages[position].date)

        recyclerView = findViewById(R.id.recyclerViewFullImage)
        adapter = RvaFullImage(this, this)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.scrollToPosition(position)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    title = ImageController.instance.listImages[layoutManager.findFirstVisibleItemPosition()].name
                    textView.text = sdf.format(ImageController.instance.listImages[layoutManager.findFirstVisibleItemPosition()].date)
                }
            }
        })

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_full_image, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.download) {
            // Запрос прав на изменение данных на носителе для версии >= M
            val REQUEST_WRITE_STORAGE = 112
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val hasPermission = ContextCompat.checkSelfPermission(baseContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                if (!hasPermission)
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            REQUEST_WRITE_STORAGE)
            }
            val downloadImage = DownloadImage(this, this)
            val params = arrayOfNulls<String>(2)
            params[0] = ImageController.instance.listImages[layoutManager.findFirstVisibleItemPosition()].path
            params[1] = ImageController.instance.listImages[layoutManager.findFirstVisibleItemPosition()].name
            downloadImage.setActivity(this)
            downloadImage.execute(*params)
        }
        return super.onOptionsItemSelected(item)
    }

    /** Сохраняет загруженные картинки и уведомляет adapter об необходимости обновить содержимое  */
    override fun responseImagesDownload(images: List<Image>) {
        ImageController.instance.isLoading = false
        ImageController.instance.listImages.addAll(images)
        saveImages(images)
        runOnUiThread { adapter.notifyDataSetChanged() }
    }

    override fun responseImagesError() {
        runOnUiThread { Toast.makeText(baseContext, "Нет интернет соединения!", Toast.LENGTH_LONG).show() }
    }

    private fun saveImages(items: List<Image>) {
        val sPref = getPreferences(MODE_PRIVATE)
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
