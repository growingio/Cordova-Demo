/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

//
//  AppDelegate.m
//  HelloCordova
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "AppDelegate.h"
#import "MainViewController.h"
#import "GrowingIOCordovaPlugin.h"
#import "GrowingTrackConfiguration.h"
#import "GrowingTracker.h"

//static NSString *const kGrowingProjectId = @"0a1b4118dd954ec3bcc69da5138bdb96";

@implementation AppDelegate

- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    
    // Config GrowingIO
    // 参数需要从GrowingIO网站上，创建新应用，或从已知应用中获取
    // YourProjectId eg:0a1b4118dd954ec3bcc69da5138bdb96
    // YourServerHost eg:http://106.75.81.105:8080
    // YourDatasourceId eg: 11223344aabbcc
    GrowingTrackConfiguration *configuration = [GrowingTrackConfiguration configurationWithProjectId:@"91eaf9b283361032"];
    configuration.dataCollectionServerHost = @"http://106.75.54.179";
    configuration.dataSourceId = @"a6e379f4c6f56659";
    configuration.debugEnabled = YES;
    [GrowingTracker startWithConfiguration:configuration launchOptions:launchOptions];
    
    // 其他配置
    // 开启Growing调试日志可以开启日志
 //   [GrowingTracker setEnableLog:YES];
    
    self.viewController = [[MainViewController alloc] init];
    return [super application:application didFinishLaunchingWithOptions:launchOptions];
}

// url scheme跳转
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {

    return NO;
}

// universal Link执行
- (BOOL) application:(UIApplication *)application
continueUserActivity:(NSUserActivity *)userActivity
  restorationHandler:(void (^)(NSArray<id <UIUserActivityRestoring>> *_Nullable))restorationHandler {
    return YES;
}

@end
