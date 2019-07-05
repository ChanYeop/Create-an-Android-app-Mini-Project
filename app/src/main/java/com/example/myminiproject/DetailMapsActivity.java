package com.example.myminiproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.LocationTemplate;
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String  TAG="TAG";

    Realm realm;

    private GoogleMap mMap;
    List<Posts> po;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        //Log.d(TAG,String.valueOf(position));

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);

        RealmHelper helper = new RealmHelper(realm);

        po = helper.retrieve();

        ((TextView) findViewById(R.id.textView3)).setText(po.get(position).getTitle());
        ((TextView) findViewById(R.id.textView6)).setText(po.get(position).getSnippet());
        ((ImageView) findViewById(R.id.imageView)).setImageURI(Uri.parse(po.get(position).getImg()));

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmHelper helper = new RealmHelper(realm);
                helper.delete(position);

                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationTemplate params = LocationTemplate.newBuilder(getAddress(Double.valueOf(po.get(position).getLat()),Double.valueOf(po.get(position).getLng())),
                        ContentObject.newBuilder(po.get(position).getTitle(),
                                "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                                LinkObject.newBuilder()
                                        .setWebUrl("https://developers.kakao.com")
                                        .setMobileWebUrl("https://developers.kakao.com")
                                        .build())
                                .setDescrption(po.get(position).getSnippet())
                                .build())
                        .setAddressTitle(po.get(position).getTitle())
                        .build();

                Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                serverCallbackArgs.put("user_id", "${current_user_id}");
                serverCallbackArgs.put("product_id", "${shared_product_id}");

                KakaoLinkService.getInstance().sendDefault(getApplicationContext(), params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                    }
                });
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Log.d(TAG,String.valueOf(position));
        Log.d(TAG,String.valueOf(po.get(position).getLng()));

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(Double.valueOf(po.get(position).getLng()), Double.valueOf(po.get(position).getLat()));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(po.get(position).getLat()),Double.valueOf(po.get(position).getLng()))).title(po.get(position).getTitle()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(po.get(position).getLat()),Double.valueOf(po.get(position).getLng())),13));
    }

    public String getAddress(double lat, double lng){
        String address = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address>list = null;

        try{
            list = geocoder.getFromLocation(lat,lng,1);

        }catch(IOException e){
            e.printStackTrace();
        }

        if(list == null){
            Log.e("getAddress", " 주소 데이터 얻기 실패");
            return null;
        }
        if(list.size() > 0){
            Address addr = list.get(0);
            String ad[] = {"","","","",""};

            if(addr.getCountryName() != null){
                ad[0]=addr.getCountryName();
            }
            if(addr.getAdminArea() != null){
                ad[1]=addr.getAdminArea();
            }
            if(addr.getLocality() != null){
                ad[2]=addr.getLocality();
            }
            if(addr.getThoroughfare() != null){
                ad[3]=addr.getThoroughfare();
            }
            if(addr.getFeatureName() != null){
                ad[4]=addr.getFeatureName();
            }

            address = ad[0] + " " + ad[1]+ " "  + ad[2]+ " "+ad[3]+ " "+ad[4];
        }
        return address;
    }
}
