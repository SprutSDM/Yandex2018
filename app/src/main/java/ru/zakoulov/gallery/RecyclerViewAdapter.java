package ru.zakoulov.gallery;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Илья on 26.04.2018.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Image> images;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
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
                .load(context.getResources().getIdentifier(images.get(i).getImagePath(), "drawable", context.getPackageName()))
                .fit()
                .centerCrop()
                .into(viewHolder.image);
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
