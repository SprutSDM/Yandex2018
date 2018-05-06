package ru.zakoulov.gallery.activity.dailyImage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
                .load(imageController.dailyImage.getFullPath())
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
            return;
        }
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
        // Загрузка других картинок
        ImageController.downloadImages(ImageController.newsFeedFragment);
    }
}
