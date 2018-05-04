package ru.zakoulov.gallery.activity.fullImageActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 04.05.2018.
 */
public class RvaFullImage extends RecyclerView.Adapter<RvaFullImage.ViewHolder> {
    private ImageController imageController;
    private Context context;

    public RvaFullImage(Context context, ImageController imageController) {
        this.context = context;
        this.imageController = imageController;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_full_image, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context)
                .load(imageController.getListImages().get(i).getPath())
                .into(viewHolder.image);
        if (i + 10 > imageController.getListImages().size() && !imageController.getIsLoad())
            imageController.downloadImages();
    }


    @Override
    public int getItemCount() {
        return imageController.getListImages().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private PhotoView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.fullImage);
        }
    }
}
