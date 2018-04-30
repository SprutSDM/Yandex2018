package ru.zakoulov.gallery.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class DailyPhotoFragment extends Fragment {
    ImageController imageController;
    boolean isVisible = false;
    View view;

    public static DailyPhotoFragment newInstance(ImageController imageController) {
        DailyPhotoFragment fragment = new DailyPhotoFragment();
        fragment.imageController = imageController;
        if (imageController.dailyPhotoFragment == null)
            imageController.dailyPhotoFragment = fragment;
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
            view = inflater.inflate(R.layout.fragment_daily_photo, container, false);
            //imageController;
        }
        return view;
    }

    public void showImage() {
        isVisible = true;
        view.findViewById(R.id.progressBarDailyPhoto).setVisibility(ProgressBar.INVISIBLE);
        Picasso.with(view.getContext())
                //.load(context.getResources().getIdentifier(images.get(i).getImagePath(), "drawable", context.getPackageName()))
                .load(imageController.getListImages().get(0).getImagePath())
                .fit()
                .centerCrop()
                .into((ImageView) view.findViewById(R.id.imageViewDailyPhoto));
    }

    public boolean isShow() {
        return isVisible;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
