//
//  GrowingIOCordovaPlugin.m
//  Growing
//
//  Created by nicodeng on 2017/7/26.
//  Copyright © 2017年 GrowingIO. All rights reserved.
//

#import "GrowingIOCordovaPlugin.h"
#import "GrowingTracker.h"

@implementation GrowingIOCordovaPlugin

NS_INLINE NSString *GROWGetTimestampFromTimeInterval(NSTimeInterval timeInterval) {
    return [NSNumber numberWithUnsignedLongLong:timeInterval * 1000.0].stringValue;
}

NS_INLINE NSString *GROWGetTimestamp() {
    return GROWGetTimestampFromTimeInterval([[NSDate date] timeIntervalSince1970]);
}

//- (void)trackCustomEvent:(NSString *)eventName itemKey:(NSString *)itemKey itemId:(NSString *)itemId withAttributes:(NSDictionary <NSString *, NSString *> *)attributes;

- (void)trackCustomEvent:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"trackCustomEvent success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 4) {
        id eventName = arguments[0];
        id attributes = arguments[1];
        id itemKey = arguments[2];
        id itemId = arguments[3];
        
        if ([eventName isKindOfClass:[NSString class]]) {
            
            if ([itemKey isKindOfClass:[NSString class]] && [itemId isKindOfClass:[NSString class]]
                    && [attributes isKindOfClass:[NSDictionary class]]) {
                
                [[GrowingTracker sharedInstance]  trackCustomEvent:(NSString *)eventName itemKey:(NSString *)itemKey itemId:(NSString *)itemId withAttributes:(NSDictionary <NSString *, NSString *> *)attributes];
            }
            else if([itemKey isKindOfClass:[NSString class]] && [itemId isKindOfClass:[NSString class]]){

                [[GrowingTracker sharedInstance]  trackCustomEvent:(NSString *)eventName itemKey:(NSString *)itemKey itemId:(NSString *)itemId];

            }
            else if([attributes isKindOfClass:[NSDictionary class]]){
                [[GrowingTracker sharedInstance]  trackCustomEvent:(NSString *)eventName withAttributes:(NSDictionary <NSString *, NSString *> *)attributes];
            
            }
            else{
                [[GrowingTracker sharedInstance] trackCustomEvent:eventName];
            }
            
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The argument userId must be string type"];
        }

    } else {
        //arguments count 一定是4,除非修改了js
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"JS error"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setLoginUserAttributes:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"setLoginUserAttributes success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 1) {
        id attributes = arguments[0];
        if ([attributes isKindOfClass:[NSDictionary class]]) {
            [[GrowingTracker sharedInstance] setLoginUserAttributes:attributes];

        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The argument userId must be NSDictionary type"];
        }
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The arguments count must be 1"];
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setLoginUserId:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"setLoginUserId success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 1) {
        id userId = arguments[0];
        if ([userId isKindOfClass:[NSString class]]) {
            [[GrowingTracker sharedInstance] setLoginUserId:userId];

        } else if ([userId isKindOfClass:[NSNumber class]]) {
            [[GrowingTracker sharedInstance] setLoginUserId:((NSNumber *)userId).stringValue];

        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The argument userId must be string or number type"];
        }
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The arguments count must be 1"];
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

 
- (void)cleanLoginUserId:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"cleanLoginUserId success"];
    [[GrowingTracker sharedInstance] cleanLoginUserId];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
 
- (void)setLocation:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"setLocation success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 2) {
        id _latitude = arguments[0];
        id _longitude = arguments[1];
        
        if ([_latitude isKindOfClass:[NSNumber class]] && [_longitude isKindOfClass:[NSNumber class]]) {

            [[GrowingTracker sharedInstance] setLocation:[_latitude doubleValue] longitude:[_longitude doubleValue]];

        }
        else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The argument location must be double type"];
        }
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The arguments count must be 2"];
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

 
- (void)cleanLocation:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"cleanLocation success"];

    [[GrowingTracker sharedInstance] cleanLocation];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setDataCollectionEnabled:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"setDataCollectionEnabled success"];
    
    NSArray *arguments = command.arguments;
    if (arguments.count == 1) {
        id enabled = arguments[0];
        
        if ([enabled isKindOfClass:[NSNumber class]]) {
            [[GrowingTracker sharedInstance] setDataCollectionEnabled:(BOOL)enabled];
        }
        else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The argument location must be bool type"];
        }
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Argument error, The arguments count must be 1"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSString *)getDeviceId:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult * pluginResult = nil;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"getDeviceId success"];
    
    NSString* deviceId = [[GrowingTracker sharedInstance] getDeviceId];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    
    return deviceId;
}

@end
