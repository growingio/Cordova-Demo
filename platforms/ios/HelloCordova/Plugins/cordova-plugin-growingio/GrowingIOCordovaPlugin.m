//
//  GrowingIOCordovaPlugin.m
//  Growing
//
//  Created by nicodeng on 2017/7/26.
//  Copyright © 2017年 GrowingIO. All rights reserved.
//

#import "GrowingIOCordovaPlugin.h"
#import "Growing.h"

@implementation GrowingIOCordovaPlugin

NS_INLINE NSString *GROWGetTimestampFromTimeInterval(NSTimeInterval timeInterval) {
    return [NSNumber numberWithUnsignedLongLong:timeInterval * 1000.0].stringValue;
}

NS_INLINE NSString *GROWGetTimestamp() {
    return GROWGetTimestampFromTimeInterval([[NSDate date] timeIntervalSince1970]);
}

- (void)setUserId:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"setUserId success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 1) {
        id userId = arguments[0];
        if ([userId isKindOfClass:[NSString class]]) {
            [self dispatchInMainThread:^{
                [Growing setUserId:userId];
            }];
        } else if ([userId isKindOfClass:[NSNumber class]]) {
            [self dispatchInMainThread:^{
                [Growing setUserId:((NSNumber *)userId).stringValue];
            }];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The argument userId must be string or number type"];
        }
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The arguments count must be 1"];
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)clearUserId:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"clearUserId success"];
    [self dispatchInMainThread: ^{
        [Growing clearUserId];
    }];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)track:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"track success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 3) {
        NSString *eventId = arguments[0];
        id argument1 = arguments[1];
        id argument2 = arguments[2];
        if ([argument1 isKindOfClass:[NSNumber class]]) {
            if ([argument2 isKindOfClass:[NSDictionary class]]) {
                [self dispatchInMainThread:^{
                    [Growing track:eventId withNumber:argument1 andVariable:argument2];
                }];
            } else {
                [self dispatchInMainThread:^{
                    [Growing track:eventId withNumber:argument1];
                }];
            }
            
        } else if ([argument1 isKindOfClass:[NSDictionary class]]) {
            [self dispatchInMainThread:^{
                [Growing track:eventId withVariable:argument1];
            }];
        } else {
            [Growing track:eventId];
        }
    } else {
        //arguments count 一定是3,除非修改了js
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"JS error"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
- (void)page:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult *pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"page success"];
    NSArray *arguments = command.arguments;
    if (arguments.count == 1) {
        NSString *pageName = arguments[0];
        [self dispatchInMainThread:^{
//            [Growing trackPageWithPageName:pageName pageTime:GROWGetTimestamp()];
        }];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"JS error"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)setEvar:(CDVInvokedUrlCommand*)command
{
    [self performPluginSelName:@"setEvar" command:command];
}

- (void)setPeopleVariable:(CDVInvokedUrlCommand*)command
{
    [self performPluginSelName:@"setPeopleVariable" command:command];
}

- (void)setPageVariable:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult *pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"setPageVariable success"];
    NSArray *arguments = command.arguments;
    if (arguments.count == 2) {
        NSString *pageName = arguments[0];
        NSDictionary *pageLevelVariables = arguments[1];
        [self dispatchInMainThread:^{
//            [Growing setPageVariable:pageLevelVariables toPage:pageName];
        }];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"JS error"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)performPluginSelName:(NSString *)selName command:(CDVInvokedUrlCommand *)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[NSString stringWithFormat:@"%@ success", selName]];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 1) {
        id variables = arguments[0];
        if ([variables isKindOfClass:[NSDictionary class]]) {
            [self dispatchInMainThread: ^{
                if ([selName isEqualToString:@"setEvar"]) {
                    [Growing setEvar:variables];
                } else if ([selName isEqualToString:@"setPeopleVariable"]) {
                    [Growing setPeopleVariable:variables];
                }
            }];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The Variables must be object type"];
        }
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"JS error"];
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)dispatchInMainThread:(void (^)(void))block
{
    if ([NSThread isMainThread]) {
        block();
    } else {
        dispatch_async(dispatch_get_main_queue(), block);
    }
}

@end
