cordova.define("cordova-plugin-growingio.GrowingIO", function(require, exports, module) {
'use strict';

var exec = require('cordova/exec'),
  GrowingIO = {
  },
  errors = {
    invalid: function(paramName, value) {
      return 'invalid ' + paramName + ': ' + value;
    }
  };


    GrowingIO.trackCustomEvent = function(eventName, attributes, itemKey, itemId, onSuccess, onFail){
        if ((!eventName || typeof eventName != 'string') && typeof(onFail) === 'function') {
            return onFail(errors.invalid('eventName', eventName));
        }
        exec(onSuccess, onFail, 'GrowingIO', 'trackCustomEvent', [eventName, attributes, itemKey, itemId]);
    }

    GrowingIO.setLoginUserAttributes = function(attributes, onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'setLoginUserAttributes', [attributes]);
    }

    GrowingIO.setLoginUserId = function(userId, onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'setLoginUserId', [userId]);
    }

    GrowingIO.cleanLoginUserId = function(onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'cleanLoginUserId', []);
    }

    GrowingIO.setLocation = function(latitude, longitude, onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'setLocation', [latitude, longitude]);
    }

    GrowingIO.cleanLocation = function(onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'cleanLocation', []);
    }

    GrowingIO.setDataCollectionEnabled = function(enabled, onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'setDataCollectionEnabled', [enabled]);
    }

    GrowingIO.getDeviceId = function(onSuccess, onFail) {
        exec(onSuccess, onFail, 'GrowingIO', 'getDeviceId', []);
    }
    
module.exports = GrowingIO;

});
