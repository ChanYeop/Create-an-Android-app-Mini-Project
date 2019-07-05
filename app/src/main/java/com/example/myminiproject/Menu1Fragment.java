package com.example.myminiproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Menu1Fragment extends Fragment {

    Realm realm;
    List<Posts> co;
    ListView lv;
    ListAdapter adapter;
    AdapterView.OnItemClickListener mCallback=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AdapterView.OnItemClickListener){
            mCallback = (AdapterView.OnItemClickListener)context; //mCall에 click에 대한 이벤트 처리
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu1, container, false);
        Realm.init(getContext());
        co=new ArrayList<>();

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);

        RealmHelper helper = new RealmHelper(realm);

        co = helper.retrieve();

        lv = (ListView) view.findViewById(R.id.listview);
        adapter = new ListAdapter(getActivity(),co);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(mCallback);




        return view;
    }
}