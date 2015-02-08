package wairoadc.godiscover.view.fragments;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.GalleryGridAdapter;
import wairoadc.godiscover.utilities.UtilsGrid;
import wairoadc.godiscover.view.models.AppConstantGrid;

/**
 * Created by Lucas on 5/02/2015.
 */
public class PicturesFragment extends Fragment {
    public static final String ARG_PAGE = "Picture",ARG_TYPE = "type";

    private int mPage;
    private UtilsGrid utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GalleryGridAdapter adapter;
    private GridView gridView;
    private int columnWidth,type;
    private Context context;

    public static PicturesFragment newInstance(int page,int type) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt(ARG_TYPE, type);
        PicturesFragment fragment = new PicturesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        type = getArguments().getInt(ARG_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pictures, container, false);
        gridView = (GridView) view;

        utils = new UtilsGrid(getActivity());

        InitilizeGridLayout();

        switch (type){
            case 0:
                Toast.makeText(getActivity(),"track",Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(getActivity(),"spot",Toast.LENGTH_LONG).show();
                break;
        }

        imagePaths = utils.getFilePaths();

        adapter = new GalleryGridAdapter(getActivity(),imagePaths,columnWidth,type);

        gridView.setAdapter(adapter);
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