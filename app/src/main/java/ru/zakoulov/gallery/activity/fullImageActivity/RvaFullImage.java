package ru.zakoulov.gallery.activity.fullImageActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 04.05.2018.
 */
public class RvaFullImage extends RecyclerView.Adapter<RvaFullImage.ViewHolder> {
    private Context context;
    private TaskResponseImages taskResponse;

    public RvaFullImage(Context context, TaskResponseImages taskResponse) {
        this.context = context;
        this.taskResponse = taskResponse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_full_image, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context)
                .load(ImageController.getListImages().get(i).getPath())
                .into(viewHolder.image);
        if (i + 10 > ImageController.getListImages().size() && !ImageController.getIsLoad())
            ImageController.downloadImages(taskResponse);
    }


    @Override
    public int getItemCount() {
        return ImageController.getListImages().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private PhotoView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.fullImage);
        }
    }
}
