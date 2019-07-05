package com.example.myminiproject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity context;
    //private java.util.List<Container> co ;
    private List<Posts> co;

    public ListAdapter(Activity _context, List<Posts> _co) {
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
            convertView = inflater.inflate(R.layout.activity_list_adapter, parent, false);
            ((TextView) convertView.findViewById(R.id.textView5)).setText(co.get(position).getSnippet());
            ((TextView) convertView.findViewById(R.id.textView4)).setText(co.get(position).getTitle());
            ((ImageView) convertView.findViewById(R.id.imageView2)).setImageURI(Uri.parse(co.get(position).getImg()));
        }
        return convertView;
    }

}
