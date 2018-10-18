package ru.zakoulov.gallery.activity.fullImageActivity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.imageController.ImageController
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages

/**
 * Created by Илья on 04.05.2018.
 */
class RvaFullImage(val context: Context,
                   val taskResponse: TaskResponseImages) : RecyclerView.Adapter<RvaFullImage.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_full_image, viewGroup, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        Picasso.with(context)
                .load(ImageController.instance.listImages[i].path)
                .into(viewHolder.image)
        if (i + 10 > ImageController.instance.listImages.size && !ImageController.instance.isLoading)
            ImageController.instance.downloadImages(taskResponse)
    }


    override fun getItemCount(): Int {
        return ImageController.instance.listImages.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: PhotoView = itemView.findViewById(R.id.fullImage)
    }
}
