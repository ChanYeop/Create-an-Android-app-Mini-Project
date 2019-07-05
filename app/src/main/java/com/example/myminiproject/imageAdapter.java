package com.example.myminiproject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.List;

public class imageAdapter extends BaseAdapter {

    int GalItemBg;
    private LayoutInflater inflater;
    private Activity context;
    //private java.util.List<Container> co ;
    private List<Posts> co;
    ImageView imageView;

    public imageAdapter(Activity _context, List<Posts> _co) {
        context = _context;
        co = _co;
    }


    @Override
    public int getCount() {
        return co.size();
    }

    @Override
    public Object getItem(int position) {
        return co.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.activity_image_adapter, parent, false);
            imageView = ((ImageView) convertView.findViewById(R.id.imageView3));
            imageView.setImageURI(Uri.parse(co.get(position).getImg()));
            imageView.setLayoutParams(new Gallery.LayoutParams(500,500));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }
        //return convertView;
        return  imageView;
    }

}