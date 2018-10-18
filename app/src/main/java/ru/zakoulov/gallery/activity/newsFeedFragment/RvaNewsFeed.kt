package ru.zakoulov.gallery.activity.newsFeedFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

import ru.zakoulov.gallery.R
import ru.zakoulov.gallery.activity.fullImageActivity.FullImageActivity
import ru.zakoulov.gallery.imageController.Image
import ru.zakoulov.gallery.imageController.ImageController
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages


class RvaNewsFeed(val context: Context,
                  val taskResponse: TaskResponseImages,
                  val imageController: ImageController) : RecyclerView.Adapter<RvaNewsFeed.ViewHolder>() {
    private val images: List<Image>

    init {
        this.images = imageController.listImages
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_news_feed, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        Picasso.with(context)
                .load(images[i].path)
                .fit()
                .centerCrop()
                .into(viewHolder.image)
        if (i + 10 > images.size && !imageController.isLoading)
            imageController.downloadImages(taskResponse)
    }


    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val image: ImageView = itemView.findViewById(R.id.listImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) { // Обработка нажатий
            val position = adapterPosition
            Log.d("click", "click")
            if (position != RecyclerView.NO_POSITION) {
                val intent = Intent(context, FullImageActivity::class.java)
                val bundle = Bundle()
                intent.putExtra("position", position)
                context.startActivity(intent)
            }
        }
    }
}
