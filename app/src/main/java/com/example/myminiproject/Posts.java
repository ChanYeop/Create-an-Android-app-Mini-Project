package com.example.myminiproject;

import io.realm.RealmObject;

public class Posts extends RealmObject {
    private String title;
    private String snippet;
    private String lat;
    private  String lng;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Posts(){

    }

    public Posts(String _title, String _snippet, String _lat, String _lng, String _img){
        title = _title;
        snippet = _snippet;
        lat = _lat;
        lng = _lng;
        img = _img;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTitle(){
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
