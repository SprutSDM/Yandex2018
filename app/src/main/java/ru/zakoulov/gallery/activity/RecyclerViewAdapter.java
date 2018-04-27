package ru.zakoulov.gallery.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 26.04.2018.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ImageController imageController;
    private List<Image> images;
    private Context context;

    public RecyclerViewAdapter(Context context, ImageController imageController) {
        this.context = context;
        this.imageController = imageController;
        this.images = imageController.getListImages();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_image_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //Log.d("abacaba", context.getResources().getIdentifier(images.get(i).getImagePath(), "drawable", context.getPackageName()));
        Picasso.with(context)
                //.load(context.getResources().getIdentifier(images.get(i).getImagePath(), "drawable", context.getPackageName()))
                .load(images.get(i).getImagePath())
                .fit()
                .centerCrop()
                .into(viewHolder.image);
        if (i + 10 > images.size() && !imageController.getIsLoad())
            imageController.downloadUrls();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.listImage);
        }
    }
}
