package wairoadc.godiscover.view.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.GalleryGridAdapter;
import wairoadc.godiscover.controller.TrackController;
import wairoadc.godiscover.model.Resource;
import wairoadc.godiscover.utilities.UtilsGrid;
import wairoadc.godiscover.view.models.AppConstantGrid;

/**
 * Created by Lucas on 5/02/2015.
 */
public class PicturesFragment extends Fragment {

    public static final String IMAGE_LIST = "imageList";


    private UtilsGrid utils;
    private List<Resource> imageResources = new ArrayList<Resource>();
    private GalleryGridAdapter adapter;
    private GridView gridView;
    private int columnWidth, galleryMode;

    public static PicturesFragment newInstance(List<Resource> imageResources) {
        Bundle args = new Bundle();


        args.putParcelableArrayList(IMAGE_LIST, (ArrayList<Resource>) imageResources);
        PicturesFragment fragment = new PicturesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResources = getArguments().getParcelableArrayList(IMAGE_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pictures, container, false);
        gridView = (GridView) view;

        utils = new UtilsGrid(getActivity());

        InitilizeGridLayout();
        List<String> imagePaths = new ArrayList<>();
        for(Resource resource : imageResources) {
            imagePaths.add(resource.getPath());
        }

        adapter = new GalleryGridAdapter(getActivity(), imagePaths,columnWidth, galleryMode);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstantGrid.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstantGrid.NUM_OF_COLUMNS + 1) * padding)) / AppConstantGrid.NUM_OF_COLUMNS);

        gridView.setNumColumns(AppConstantGrid.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
}