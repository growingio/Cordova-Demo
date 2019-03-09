package io.cordova.hellocordova;

import android.app.Application;
import android.util.Log;

import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;

/**
 * author WangYing
 * time   2018/03/2018/3/12:下午3:05
 * email  wangying@growingio.com
 */

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GrowingIO.startWithConfiguration(this, new Configuration()
                .setChannel("XXX应用商店")
                .setDebugMode(true)
                .setTestMode(true));
    }
}
