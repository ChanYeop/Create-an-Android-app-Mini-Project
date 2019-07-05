package com.example.myminiproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Menu2Fragment extends Fragment implements OnMapReadyCallback  {

    private static final String  TAG="TAG";
    private MapView mapView = null;
    Realm realm;

    public Menu2Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu2, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions markerOptions = new MarkerOptions();

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);
        RealmResults<Posts> co = realm.where(Posts.class).findAll();
        RealmHelper helper = new RealmHelper(realm);
        List<Posts> po = helper.retrieve();

        for(int i=0; i<po.size(); i++){
            Log.d(TAG,po.get(i).getLng());
            googleMap.addMarker(markerOptions
                    .title(po.get(i).getTitle())
                    .snippet(po.get(i).getSnippet())
                    .position(new LatLng(Double.valueOf(po.get(i).getLat()),Double.valueOf(po.get(i).getLng())))
                    .zIndex((float)i));
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getContext(),DetailMapsActivity.class);
                intent.putExtra("position",(int)marker.getZIndex());
                startActivity(intent);

                return false;
            }
        });

        //googleMap.addMarker(markerOptions);

        // googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

}
