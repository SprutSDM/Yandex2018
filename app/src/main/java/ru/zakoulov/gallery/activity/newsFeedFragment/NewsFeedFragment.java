package ru.zakoulov.gallery.activity.newsFeedFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.Image;
import ru.zakoulov.gallery.imageController.ImageController;
import ru.zakoulov.gallery.imageController.tasks.TaskResponseImages;

/**
 * Created by Илья on 25.04.2018.
 */
public class NewsFeedFragment extends Fragment implements TaskResponseImages {
    RvaNewsFeed adapter;
    RecyclerView recyclerView;
    int numberOfColumns = 2;
    boolean isVisible = false;
    View view;

    public static NewsFeedFragment newInstance() {
        NewsFeedFragment fragment = new NewsFeedFragment();
        if (ImageController.newsFeedFragment == null)
            ImageController.newsFeedFragment = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news_feed, container, false);
            recyclerView = view.findViewById(R.id.recyclerViewNewsFeed);
        }
        return view;
    }

    /** Отображает картинки на экране */
    public void showAllImages() {
        isVisible = true;
        adapter = new RvaNewsFeed(view.getContext(), this);
        LinearLayoutManager layoutManager = new GridLayoutManager(view.getContext(), numberOfColumns);
        view.findViewById(R.id.progressBar).setVisibility(ProgressBar.INVISIBLE);
        recyclerView.setVisibility(RecyclerView.VISIBLE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public RvaNewsFeed getAdapter() {
        return adapter;
    }

    /** Возвращает true, если картинки отображены на экране */
    public boolean isShow() {
        return isVisible;
    }

    @Override
    /** Сохраняет загруженные картинки и уведомляет adapter об необходимости обновить содержимое */
    public void responseImagesDownload(List<Image> images) {
        if (images == null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Нет интернет соединения!", Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        ImageController.getListImages().addAll(images);
        if (getActivity() == null)
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /* Если окно не видимо, то подгружаем адаптер и показываем всё */
                if (!isShow())
                    showAllImages();
                else /* Иначе надо показать изменения */
                    getAdapter().notifyDataSetChanged();
            }
        });
        ImageController.isLoading = false;
    }
}
