# GrowingIO Cordova 插件集成文档

__内容__

1. [安装 SDK](#header-安装-SDK)
2. [Android: 配置 Manifest](#header-android-配置-manifest)
3. [启动采集](#header-启动采集)
4. [采集自定义事件](#header-采集自定义事件)
5. [高级功能](#header-高级功能)

>
>  __前提__
>
>  在使用 GrowingIO SDK 前，你需要先[注册一个账号](https://accounts.growingio.com/signup?utm_source=doc&utm_term=cordova)并且创建一个产品。
>

>
>  __前提__
>
>  `cordova-growing-plugin` 依赖 `Cordova` 版本 5.0.0 以上，目前暂时不支持低版本 Cordova。
>

<br/>

## 安装 SDK

1. 在你的项目主目录里安装 Cordova 插件。

    ```
    cordova plugin add cordova-growingio-plugin
    ```

### Android：安装 GrowingIO SDK

目前 GrowingIO SDK 只支持通过 Android Studio 开发环境来集成。

#### ANDROID/APP/BUILD.GRADLE

##### 获取 GrowingIO 项目ID 和 URL Scheme

1. 访问 [GrowingIO](https://www.growingio.com/portal)
2. 在项目概览页面可以获取到项目ID
3. 在应用管理页面可以获取到应用的 URL Scheme

##### 修改 build.gradle

1. 打开 `APP/build.gradle` 文件，并且加入以下代码

```
    android {
       defaultConfig {
          resValue("string", "growingio_project_id", "您的项目 ID")
          resValue("string", "growingio_url_scheme", "您的项目 URL Scheme")
       }
    }
    dependencies {
        compile 'com.growingio.android:vds-android-agent:track-2.6.0@aar'
    }
```

<br/>

## Android 配置 Manifest

### 获取 GrowingIO 项目ID 和 URL Scheme

1. 访问 [GrowingIO](https://www.growingio.com/portal)
2. 在项目概览页面可以获取到项目ID
3. 在应用管理页面可以获取到应用的 URL Scheme

### 修改 AndroidManifest.xml

1. 修改 AndroidManifest.xml，配置 Application 的 android:name

```
    <application android:name=".MyApp"
                 ...
                 >
    </application>
```

2. 在Application的onCreate函数添加初始化函数

```
    public class MyApp extends Application {
      public void onCreate() {
            GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .setChannel("XXX应用商店")
                .setRnMode(true) //必须配置
                .setDebugMode(true)); //打开调试Log;
      }
    }
```
    
>
> 网络访问权限
>
> 为了使用 GrowingIO 采集行为数据，你的应用程序需要具有网络访问权限和悬浮窗权限(悬浮窗权限时Debugger期间使用的)，所以你需要添加以下权限。<br/>
> `<uses-permission android:name="android.permission.INTERNET" />`<br/>
> `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
> `<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>`
>

<br/>

### iOS：安装 GrowingIO SDK

请确保您的 XCode 版本为 7.3 或者其后的版本 <br>
GrowingIO 支持两种 iOS SDK 安装方式：<br>

#### 使用 CocoaPods 管理依赖
1. 添加 `pod 'GrowingIO'` 到 Podfile 中；
2. 执行 `pod update`，不要用 `--no-repo-update` 选项；

#### 手动安装依赖

##### 1. 获取最新的SDK

访问[下载地址](http://assets.growingio.com/sdk/GrowingIO-iOS-SDK-v2.0.0.zip)获取最新的iOS SDK

##### 2. 导入SDK

按照以下步骤将SDK导入到您的项目中。

1. 解压 iOS SDK 压缩文件；
2. 添加 Growing.h 和 libGrowing.a 添加到您的 iOS 工程中；
__记得勾选 "Copy items if needed"__

##### 3. 添加依赖

在您的工程项目中添加依赖

|库名称 |说明 |
|-----------------------------|-----------------------|
|Foundation.framework |基础依赖库 |
|Security.framework |用于APP连接圈选页面SSL连接 |
|CoreTelephony.framework |用于读取运营商名称 |
|SystemConfiguration.framework |用于判断网络状态 |
|AdSupport.framework |用于来源管理激活匹配 |
|libicucore.tbd |用于APP连接圈选页面解析 |
|libsqlite3.tbd |存储日志 |
|CoreLocation.framework |用于读取地理位置信息（如果您的app有权限）|

提醒：

* 添加项目依赖库的位置在 项目设置target -> 选项卡General -> Linked Frameworks and Libraries

##### 4. 添加编译参数

在您的工程项目中添加编译参数

1. 找到 Linking 设置
2. 在 Other Linker Flags 中添加 -ObjC 参数，请注意大小写


提醒：

* Linking 设置位于 项目设置 target -> 选项卡 Build Settings，左上角选择 All。


##### 5. 添加URL Scheme

把 URL Scheme 添加到您的项目，以便我们唤醒您的程序，进行圈选。
URL Scheme 获取方式有两种
1. 添加新产品：登录官网 -> 点击新建，选择添加新产品 -> 选择添加 iOS 应用 -> 在第二段“添加 URL Scheme ”中标黄字体。
2. 现有产品：登录官网 -> 右上角点击用户头像 -> 点击“项目管理” -> 点击左侧的“应用管理” -> 找到对应产品的 URL Scheme。


URL Scheme 的格式是 growing.xxxxxxxxxxxxxxxx。

添加您的 URL Scheme（growing.xxxxxxxxxxxxxxxx）到项目中，URL Scheme 位于项目设置 target -> 选项卡 Info -> URL Types；


## 启动采集

当应用程序打开时，需要初始化 GrowingIO 会话并启动采集功能

### iOS

在 AppDelegate 中引入#import "Growing.h"并添加启动方法

```
- (BOOL)application:(UIApplication *)application
didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    ...
    // 启动GrowingIO
    [Growing startWithAccountId:@"您的项目ID"];

    // 其他配置 可根据需要开启
    // 开启Growing调试日志 可以开启日志
    // [Growing setEnableLog:YES];

    // 设置zone信息
    // [Growing setZone:@"xxx"];

    // 设置数据收集平台服务器地址
    // [Growing setTrackerHost:@"xxx"];

    // 设置数据查看平台服务器地址
    // [Growing setDataHost:@"xxx"];

    // 设置数据后台服务器地址
    // [Growing setGtaHost:@"xxx"];

    // 设置数据后台服务器地址
    // [Growing setHybridJSSDKUrlPrefix:@"xxx"];
}
```

## 采集自定义事件

初始化 GrowingIO 会话后，对于用户触发的行为，比如搜索、添加到购物车、购买等，我们可以很容易地通过一行代码采集到这些事件。

```
gio.track("purchase", 456, { item: '123' }, onSucc, onFail)
```

### 采集函数说明

#### track(eventId, number, eventLevelVariable, onSuccess, onFail)

采集自定义事件 `eventName`，该事件的属性信息属于事件级变量。

##### 参数说明

| 参数名 | 类型  | 是否必填 | 描述  |
| :-----: | :------: | :------: | :-----: |
| eventId | String  |  是  | 事件 Key |
| number | Number  |  否  |   |
| eventLevelVariable | Object | 否 | 事件自身属性 |
| onSuccess | Function | 否 | 发送成功后触发的回调函数 |
| onFail    | Function | 否 | 发送失败后触发的回调函数 |

<br/>

## 高级功能

除了最简单的 `track` 方法外，GrowingIO Cordova 还提供了一系列的方法来支持高级功能，具体如下。

### 关联注册用户

#### setUserId(userId, onSuccess, onFail)

把 GrowingIO 识别的访问用户跟应用自身的注册用户做关联，用以登录用户行为分析。

##### 参数说明

| 参数名 | 类型  | 是否必填 | 描述  |
| :-----: | :------: | :------: | :-----: |
| userId | String | 是 | 注册用户识别标识符 |
| onSuccess | Function | 否 | 关联成功后触发的回调函数 |
| onFail    | Function | 否 | 关联失败后触发的回调函数 |

##### 示例

```
gio.setUserId('123');
```

### 解决关联注册用户

#### clearUserId(onSuccess, onFail)

当访问用户跟注册用户关联后，之后触发的行为会绑定到该注册用户上。如果有需要解除绑定，比如用户退出登录后，可以通过该函数解决绑定。

##### 参数说明

| 参数名 | 类型  | 是否必填 | 描述  |
| :-----: | :------: | :------: | :-----: |
| onSuccess | Function | 否 | 关联成功后触发的回调函数 |
| onFail    | Function | 否 | 关联失败后触发的回调函数 |

##### 示例

```
gio.clearUserId();
```

### 设置用户级变量

#### setPeopleVariable(peopleVariables, onSuccess, onFail)

设置用户自身属性相关的属性信息，比如用户姓名、邮件地址、信用等级等。

##### 参数说明

| 参数名 | 类型  | 是否必填 | 描述  |
| :-----: | :------: | :------: | :-----: |
| peopleVariables | Object | 是  | 用户属性 |
| onSuccess | Function | 否 | 设置成功后触发的回调函数 |
| onFail    | Function | 否 | 设置失败后触发的回调函数 |

##### 示例

```
gio.setPeopleVariable({ name: '玎玎', email: 'dingding@growingio.com' })
```


### 设置访问级别变量

##### setVisitor(visitorVariables, onSuccess, onFail)
设置访问用户有关的属性

##### 参数说明

| 参数名           | 类型    | 是否必填 | 描述     |
| :----:           | :-----: | :------: | :----:   |
| visitorVariables | Object  | 是       | 用户属性 |
| onSuccess | Function | 否 | 设置成功后触发的回调函数 |
| onFail    | Function | 否 | 设置失败后触发的回调函数 |

##### 实例 
```
gio.setVisitor({'testversion': '开启日志'})
```

### 设置转化变量

#### setEvar(conversionVariables, onSuccess, onFail)

转化变量是一种非常强大的变量类型，主要是为了归因而用，比如访问渠道、站外搜索关键词、站内搜索关键词等等。在 GrowingIO 里面可以定制变量的归因方式和持久性范围。

##### 参数说明

| 参数名 | 类型  | 是否必填 | 描述  |
| :-----: | :------: | :------: | :-----: |
| conversionVariables | Object | 是  | 转化级属性 |
| onSuccess | Function | 否 | 设置成功后触发的回调函数 |
| onFail    | Function | 否 | 设置失败后触发的回调函数 |

##### 示例

```
gio.setEvar({ campaign: 'XXX' })
```
