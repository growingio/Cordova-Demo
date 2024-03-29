//
//  GrowingIOCordovaPlugin.h
//  Growing
//
//  Created by nicodeng on 2017/7/26.
//  Copyright © 2017年 GrowingIO. All rights reserved.
//

#import <Cordova/CDV.h>

@interface GrowingIOCordovaPlugin : CDVPlugin

- (void)setUserId:(CDVInvokedUrlCommand*)command;
- (void)clearUserId:(CDVInvokedUrlCommand*)command;
- (void)track:(CDVInvokedUrlCommand*)command;
- (void)setEvar:(CDVInvokedUrlCommand*)command;
- (void)setPeopleVariable:(CDVInvokedUrlCommand*)command;
- (void)setVisitor:(CDVInvokedUrlCommand*)command;
@end

