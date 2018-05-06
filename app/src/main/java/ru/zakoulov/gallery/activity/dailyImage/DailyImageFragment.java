package ru.zakoulov.gallery.activity.dailyImage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseDailyImage;

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
        if (view == null)
            view = inflater.inflate(R.layout.fragment_daily_image, container, false);
        return view;
    }

    /** Отображает картинку на экране, убирая progressBar */
    public void showImage() {
        isVisible = true;
        view.findViewById(R.id.progressBarDailyImage).setVisibility(ProgressBar.INVISIBLE);
        Picasso.with(view.getContext())
                .load(imageController.dailyImage.getPath())
                .fit()
                .centerCrop()
                .into((ImageView) view.findViewById(R.id.imageViewDailyImage));
    }

    /** Сохраняет картинку дня и запускает загрузку других картинок. */
    @Override
    public void responseDailyImageDownload(Image image) {
        if (image == null) { // В случае, если нет интернет соединения
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
            loadSavedImages();
            return;
        }
        image.setPath(ImageController.rootPath + image.getPath());
        image.setFullPath(ImageController.rootPath + image.getFullPath());
        ImageController.dailyImage = image;
        ImageController.getListImages().add(image);
        saveImage(image); // Сохранение в SharedPref
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showImage();
            }
        });
        // Загрузка других картинок
        ImageController.downloadImages(ImageController.newsFeedFragment);
    }

    /** Загружает картинки с предыдущего сеанса и отображает их */
    private void loadSavedImages() {
        SharedPreferences sPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        int count_images = sPref.getInt("count_images", 0);
        Log.d("count_images", count_images + "");
        if (count_images == 0)
            return;
        for (int i = 0; i < count_images; i++) {
            Image image = new Image();
            image.setName(sPref.getString("image_" + i + "_name", ""));
            image.setPath(sPref.getString("image_" + i + "_path", ""));
            image.setDate(new Date(sPref.getLong("image_" + i + "_date", 0)));
            ImageController.getListImages().add(image);
            if (i == 0)
                ImageController.dailyImage = image;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showImage();
                ImageController.newsFeedFragment.showAllImages();
            }
        });
    }

    private void saveImage(Image image) {
        Log.d("onDestroy", "start saving");
        SharedPreferences sPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("count_images", 1);
        ed.putString("image_0_name", image.getName());
        ed.putString("image_0_path", image.getPath());
        ed.putLong("image_0_date", image.getDate().getTime());
        ed.commit();
    }
}
