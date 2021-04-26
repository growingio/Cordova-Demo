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



# cordova-growing-tracker 3.0

GrowingIO Tracker SDK 3.0, CDP版本

### Android

​	anroid模块 位于项目的 platforms/android 路径下，android集成需要在该路径下进行修改

- 添加依赖

  - 使用 gradle 依赖安装sdk

    在项目和 app 模块的 build.gradle 文件中添加 maven 地址

    ```groovy
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    ```

    在 app 模块中的 build.gradle 文件的 dependencies 路径下添加依赖

    ```groovy
    //GrowingIO 埋点 SDK
    implementation 'com.growingio.android:tracker-cdp:3.1.0-SNAPSHOT'
    ```



- 初始化SDK

  - ​	在继承自 Application 的类中初始化 sdk

    ```java
    @Override
    public void onCreate() {
        super.onCreate();
        GrowingTracker.startWithConfiguration(this,
                new CdpTrackConfiguration("官网的中您的项目ID", "官网的中您的相应APP的UrlScheme")
                        .setDataCollectionServerHost("您收集数据的服务端地址")
                        .setDataSourceId("官网的中您的相应APP的DataSourceId")
                        .setChannel("XXX应用商店")
                        .setDebugEnabled(BuildConfig.DEBUG));
    }
    ```



### IOS

​	ios模块 位于项目的 platforms/ios 路径下，ios 集成需要在该路径下进行修改

- 添加依赖
  - 使用 pods 安装sdk

    首先需要确认安装 CocoaPods，然后修改 ios 模块下 Pods/Podfile 文件，然后在项目下运行命令 pod update

    ```objective-c
    pod 'GrowingAnalytics-cdp/Tracker'
    ```

- 初始化sdk

  - 在项目的 AppDelegate.m 文件中进行修改

    ```objective-c
    GrowingTrackConfiguration *configuration = [GrowingTrackConfiguration configurationWithProjectId:@"官网的中您的项目ID"];
    configuration.dataCollectionServerHost = @"您收集数据的服务端地址";
    configuration.dataSourceId = @"官网的中您的相应APP的DataSourceId";
    // 开启调试
    configuration.debugEnabled = **YES**;
    [GrowingTracker startWithConfiguration:configuration launchOptions:launchOptions];
    ```

### Cordova 调用本地模块

```javascript
// 获取发布的 cordova-plugin-growingio.GrowingIO 模块
var gio = window.cordova.require('cordova-plugin-growingio.GrowingIO');
```



## API说明

### 1 设置登录用户标识

#### 1.1 `setLoginUserId(userId)`

设置登录用户标识。

#### 1.2 参数说明

| 参数名 | 类型   | 必填 | 默认值   | 说明             |
| ------ | ------ | ---- | -------- | ---------------- |
| userId | string | 是   | undefine | 设置登录用户标识 |

#### 1.3 代码示例

```
gio.setLoginUserId('loginUserId');
```

### 2 清除登录用户标识

#### 2.1 `cleanLoginUserId()`

清除登录用户标识。

#### 2.2 代码示例

```
gio.cleanLoginUserId();
```

### 3 设置坐标

#### 3.1 `setLocation(latitude, longitude)`

设置坐标。

#### 3.2 参数说明

| 参数名    | 类型   | 必填 | 默认值   | 说明     |
| --------- | ------ | ---- | -------- | -------- |
| latitude  | double | 是   | undefine | 设置纬度 |
| longitude | double | 是   | undefine | 设置经度 |

#### 3.3 代码示例

```
gio.setLocation(100.0, 100.0);
```

### 4 清除坐标

#### 4.1 `cleanLocation()`

清除坐标。

#### 4.2 代码示例

```javascript
gio.cleanLocation();
```

### 5 获取设备标识

#### 5.1 `getDeviceId()`

获取设备标识。

#### 5.2 代码示例

```javascript
gio.getDeviceId();
```

### 6 设置是否采集数据

#### 6.1 `setDataCollectionEnabled(enabled)`

是否采集数据。

#### 6.2 参数说明

| 参数名  | 类型    | 必填 | 默认值   | 说明         |
| ------- | ------- | ---- | -------- | ------------ |
| enabled | boolean | 是   | undefine | 是否采集数据 |

#### 6.3 代码示例

```javascript
gio.setDataCollectionEnabled(true);
```

### 7 设置登录用户属性

#### 7.1 `setLoginUserAttributes(attributes)`

设置登录用户属性。

#### 7.2 参数说明

| 参数名     | 类型   | 必填 | 默认值   | 说明         |
| ---------- | ------ | ---- | -------- | ------------ |
| attributes | object | 是   | undefine | 登录用户属性 |

#### 7.3 代码示例

```javascript
gio.setLoginUserAttributes({'item1':'pid111', 'item2':'pid222'});
```

### 8 自定义事件

#### 8.1 `trackCustomEvent(eventName, attributes, itemKey, itemId)`

自定义事件。

#### 8.2 参数说明

| 参数名     | 类型   | 必填 | 默认值   | 说明                     |
| ---------- | ------ | ---- | -------- | ------------------------ |
| eventName  | string | 是   | undefine | 事件名称                 |
| attributes | object | 否   | undefine | 事件属性                 |
| itemKey    | string | 否   | undefine | 物品模型唯一标识属性     |
| itemId     | string | 否   | undefine | 物品模型唯一标识属性的值 |

#### 8.3 代码示例

```javascript
// eventName 为必填参数
gio.trackCustomEvent("HelloCordova");

//可以填写eventName 和 attributes 作为参数
gio.trackCustomEvent("HelloCordova", {'prodId1':'pid111', 'prodId2':'pid222'});

//可以填写eventName、itemKey和itemId 作为参数，itemKey和itemId作为参数必须同时存在
gio.trackCustomEvent("HelloCordova", null,"itemKey", "itemId");

gio.trackCustomEvent("HelloCordova", {'prodId1':'pid111', 'prodId2':'pid222'}, "itemKey", "itemId");

```
