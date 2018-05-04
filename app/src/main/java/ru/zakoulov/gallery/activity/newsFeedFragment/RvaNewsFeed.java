package ru.zakoulov.gallery.activity.newsFeedFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.activity.fullImageActivity.FullImageActivity;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 26.04.2018.
 */
public class RvaNewsFeed extends RecyclerView.Adapter<RvaNewsFeed.ViewHolder> {
    private List<Image> images;
    private Context context;
    private TaskResponseImages taskResponse;

    public RvaNewsFeed(Context context, TaskResponseImages taskResponse) {
        this.context = context;
        this.images = ImageController.getListImages();
        this.taskResponse = taskResponse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_news_feed, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context)
                .load(images.get(i).getPath())
                .fit()
                .centerCrop()
                .into(viewHolder.image);
        if (i + 10 > images.size() && !ImageController.getIsLoad())
            ImageController.downloadImages(taskResponse);
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.listImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) { // Обработка нажатий
            int position = getAdapterPosition();
            Log.d("click", "click");
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, FullImageActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        }
    }
}
