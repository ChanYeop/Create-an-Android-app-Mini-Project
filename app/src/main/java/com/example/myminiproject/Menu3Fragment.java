package com.example.myminiproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Menu3Fragment extends Fragment {
    Realm realm;
    imageAdapter mimageAdapter;
    List<Posts> co;
    Gallery g;
    // GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu3, container, false);
        Realm.init(getContext());
        co=new ArrayList<>();

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);

        RealmHelper helper = new RealmHelper(realm);

        co = helper.retrieve();

        mimageAdapter = new imageAdapter(getActivity(), co); // 앞에서 정의한 Image Adapter와 연결

        // adapterView
        g = (Gallery) view.findViewById(R.id.gallery1);
        g.setAdapter(mimageAdapter);

        final ImageView iv = (ImageView)view.findViewById(R.id.imageView2);

        g.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) { // 선택되었을 때 콜백메서드
                iv.setImageURI(Uri.parse(co.get(position).getImg()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        return view;
    }
}