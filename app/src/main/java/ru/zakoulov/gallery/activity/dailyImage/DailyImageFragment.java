package ru.zakoulov.gallery.activity.dailyImage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 30.04.2018.
 */
public class DailyImageFragment extends Fragment implements TaskResponseDailyImage {
    ImageController imageController;
    boolean isVisible = false;
    View view;

    public static DailyImageFragment newInstance() {
        DailyImageFragment fragment = new DailyImageFragment();
        ImageController.downloadDailyImage(fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setContentView(R.layout.fragment_news_feed);
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_daily_image, container, false);
            //imageController;
        }
        return view;
    }

    public void showImage() {
        isVisible = true;
        view.findViewById(R.id.progressBarDailyImage).setVisibility(ProgressBar.INVISIBLE);
        Picasso.with(view.getContext())
                .load(imageController.dailyImage.getFullPath())
                .fit()
                .centerCrop()
                .into((ImageView) view.findViewById(R.id.imageViewDailyImage));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void responseDailyImageDownload(Image image) {
        if (image == null)
            return;
        image.setPath(ImageController.rootPath + image.getPath());
        image.setFullPath(ImageController.rootPath + image.getFullPath());
        ImageController.dailyImage = image;
        ImageController.getListImages().add(image);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showImage();
            }
        });
        ImageController.downloadImages(ImageController.newsFeedFragment);
    }
}
