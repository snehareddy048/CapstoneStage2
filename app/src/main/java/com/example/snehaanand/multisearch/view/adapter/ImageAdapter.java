package com.example.snehaanand.multisearch.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.snehaanand.multisearch.model.MovieTVPersonClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by snehaanandyeluguri on 9/6/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MovieTVPersonClass> thumbIds = new ArrayList();

    public ImageAdapter(Context c, ArrayList<MovieTVPersonClass> movieBitmaps) {
        context = c;
        thumbIds = movieBitmaps;
    }

    public ImageAdapter(Context c, ArrayList<String> list,ArrayList<MovieTVPersonClass> movieBitmaps) {
        context = c;
        thumbIds = movieBitmaps;
    }

    public int getCount() {
        return thumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(thumbIds.get(position).getDisplay_image()).into(imageView);
        return imageView;
    }

    public void setGridData(ArrayList<MovieTVPersonClass> MovieClassObjects) {
        this.thumbIds = MovieClassObjects;
        notifyDataSetChanged();
    }

}
