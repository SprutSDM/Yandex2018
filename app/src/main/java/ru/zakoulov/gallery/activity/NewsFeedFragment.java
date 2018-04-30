package ru.zakoulov.gallery.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import ru.zakoulov.gallery.R;
import ru.zakoulov.gallery.imageController.ImageController;

/**
 * Created by Илья on 25.04.2018.
 */
public class NewsFeedFragment extends Fragment {
    ImageController imageController;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    int numberOfColumns = 2;
    boolean isVisible = false;
    View view;

    public static NewsFeedFragment newInstance() {
        NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
        return newsFeedFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment", "create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setContentView(R.layout.fragment_news_feed);
        view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        imageController = new ImageController(this);
        return view;
    }

    public void showAllImages() {
        isVisible = true;
        adapter = new RecyclerViewAdapter(view.getContext(), imageController);
        LinearLayoutManager layoutManager = new GridLayoutManager(view.getContext(), numberOfColumns);
        view.findViewById(R.id.progressBar).setVisibility(ProgressBar.INVISIBLE);
        recyclerView.setVisibility(RecyclerView.VISIBLE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public boolean isShow() {
        return isVisible;
    }

    @Override
    public void onDestroy() {
        Log.d("fragment", "destroy");
    }
}
