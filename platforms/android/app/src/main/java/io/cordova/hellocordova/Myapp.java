package io.cordova.hellocordova;

import android.app.Application;
import com.growingio.android.sdk.track.CdpTrackConfiguration;
import com.growingio.android.sdk.track.GrowingTracker;

/**
 * author WangYing
 * time   2018/03/2018/3/12:下午3:05
 * email  wangying@growingio.com
 */

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GrowingTracker.startWithConfiguration(this,
                new CdpTrackConfiguration("91eaf9b283361032", "growing.36e253577d20c8c5")
                        .setDataCollectionServerHost("http://106.75.54.179")
                        .setDataSourceId("82ba2ba733a3a641")
                        .setChannel("XXX应用商店")
                        .setDebugEnabled(BuildConfig.DEBUG));
    }
}
