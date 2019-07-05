package com.example.myminiproject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    private  RealmResults<Posts> containers;

    Realm realm;

    public RealmHelper(Realm realm){
        this.realm=realm;
    }

    public void save(final Posts posts){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Posts co = realm.copyToRealm(posts);
            }
        });

    }

    public List<Posts> retrieve() {
        ArrayList<Posts> ContainerNames = new ArrayList<>();
        containers = realm.where(Posts.class).findAll();

        for (Posts co : containers) {
            ContainerNames.add(new Posts(co.getTitle(),co.getSnippet(),co.getLat(),co.getLng(),co.getImg()));
        }

        return ContainerNames;
    }

    public void delete(final int position){
        containers = realm.where(Posts.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Posts po = containers.get(position);
                po.deleteFromRealm();
            }
        });
    }


}
