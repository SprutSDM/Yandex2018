package ru.zakoulov.gallery.activity.dailyImage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 30.04.2018.
 */
public class DailyImageFragment extends Fragment {
    ImageController imageController;
    boolean isVisible = false;
    View view;

    public static DailyImageFragment newInstance() {
        DailyImageFragment fragment = new DailyImageFragment();
        if (ImageController.dailyPhotoFragment == null)
            ImageController.dailyPhotoFragment = fragment;
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
}
