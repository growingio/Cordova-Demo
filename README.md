# Cordova-Demo  

####  主要示例 GrowingIO Cordova 埋点 SDK 的集成方式。 

### [Android 埋点 SDK 集成文档](https://docs.growingio.com/docs/sdk-integration/cordova-mai-dian-sdk)  

1. Demo 运行截图   
![image](https://github.com/growingio/Cordova-Demo/blob/master/screenshots/android.png)  

2. 运行
```
cordova run android 
```

3. 如果您想使用 mobile debugger 查看数据，您需要使用您的 UrlScheme 和 projectId 替换对应文件的内容；    
（1）platforms/android/app/build.gradle   
```
android {

    defaultConfig {
        ...
        
        //GrowingIO 配置， 请切换成您的账号下的 项目id 和 url scheme 哦， 别忘记还要配置 Manifest 中的 URLscheme。
        resValue("string", "growingio_project_id", "您的项目id")
        resValue("string", "growingio_url_scheme", "您的 URL scheme")

```
   
  (2) platforms/android/app/src/AndroidManifest.xml    
```
   <application android:hardwareAccelerated="true" android:icon="@mipmap/icon" android:label="@string/app_name" android:name=".Myapp" android:supportsRtl="true">
        <activity ... />
            
            <intent-filter>
                <data android:scheme="growing.您的 URL scheme" />
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
            </intent-filter>
        </activity>
```

### [iOS 埋点 SDK 集成文档](https://docs.growingio.com/docs/sdk-integration/cordova-mai-dian-sdk)  

1. 运行 iOS   
```
cordova run ios
```
2. 如果您想使用 mobile debugger 查看数据，您需要使用您的 UrlScheme 和 projectId 替换对应文件的内容
