package com.ssthouse.supermarketmanagement.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by ssthouse on 20/11/2016.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "v8C4cGcNIgHoPlxhxO4CBfco-gzGzoHsz", "KvQzo5VGUpjcrkd4XdN5IgHO");
    }
}
