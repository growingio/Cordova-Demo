//
//  GrowingIOCordovaPlugin.h
//  Growing
//
//  Created by nicodeng on 2017/7/26.
//  Copyright © 2017年 GrowingIO. All rights reserved.
//

#import <Cordova/CDV.h>

@interface GrowingIOCordovaPlugin : CDVPlugin

- (void)trackCustomEvent:(CDVInvokedUrlCommand*)command;
- (void)setLoginUserAttributes:(CDVInvokedUrlCommand*)command;
- (void)setLoginUserId:(CDVInvokedUrlCommand*)command;
- (void)cleanLoginUserId:(CDVInvokedUrlCommand*)command;
- (void)setLocation:(CDVInvokedUrlCommand*)command;
- (void)cleanLocation:(CDVInvokedUrlCommand*)command;
- (void)setDataCollectionEnabled:(CDVInvokedUrlCommand*)command;
- (NSString *)getDeviceId:(CDVInvokedUrlCommand*)command;
@end
