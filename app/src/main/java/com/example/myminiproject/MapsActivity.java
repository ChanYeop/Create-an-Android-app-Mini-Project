package com.example.myminiproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity context;
    private PickImageHelper ViewHelper;
    private EditText mName;
    private EditText mSnipp;
    private ImageButton imageButton;
    private LatLng mLatLng;
    MarkerOptions mOptions;
    Uri imageUri;

    Realm realm;
    List<Posts> co;
    ListView lv;
    ListAdapter adapter;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mOptions = new MarkerOptions();
                Double latitude = latLng.latitude; // 위도
                Double longitude = latLng.longitude;
                mOptions.snippet(mName.getText().toString()+ ", " + mSnipp.getText().toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                mMap.addMarker(mOptions);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageUri = ViewHelper.getPickImageResultUri(this, data);
            //context.grantUriPermission("com.android.systemui", imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageButton.setImageURI(imageUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Realm.init(context);


        mName = (EditText)findViewById(R.id.id_txt_input);
        mSnipp = (EditText)findViewById(R.id.id_txt_input2);
        co=new ArrayList<>();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.selectImage(MapsActivity.this);
            }
        });


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Posts c= new Posts(mName.getText().toString(),mSnipp.getText().toString(),String.valueOf(mOptions.getPosition().latitude),String.valueOf(mOptions.getPosition().longitude),imageUri.toString());
                c.setSnippet(mSnipp.getText().toString());
                c.setTitle(mName.getText().toString());
                c.setLat(String.valueOf(mOptions.getPosition().latitude));
                c.setLng(String.valueOf(mOptions.getPosition().longitude));
                c.setImg(imageUri.toString());

                RealmConfiguration config = new RealmConfiguration.Builder().build();
                realm = Realm.getInstance(config);

                RealmHelper helper = new RealmHelper(realm);
                helper.save(c);

                co= helper.retrieve();

                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
