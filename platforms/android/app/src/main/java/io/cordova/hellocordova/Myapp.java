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
                new CdpTrackConfiguration("exampleProjectId", "exampleUrlScheme")
                        .setDataCollectionServerHost("http://myhost.com/")
                        .setDataSourceId("exampleSourceId")
                        .setChannel("XXX应用商店")
                        .setDebugEnabled(BuildConfig.DEBUG));
    }
}
