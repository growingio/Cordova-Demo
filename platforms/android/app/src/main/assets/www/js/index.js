/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var app = {


    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
        document.addEventListener('resume', this.onDeviceResume, false);

    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        app.initGrowingIO();


    },

    onDeviceResume: function() {
        // app.initGrowingIO();
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);



    },

    initGrowingIO: function() {
      // GrowingIO 初始化
      try {
        var gio = window.cordova.require('cordova-plugin-growingio.GrowingIO');
        var onSucc = function(msg) {
          alert(msg);
        };
        var onFail = function(msg) {
          alert(msg);
        };

        document.getElementById("track1").addEventListener("click", function() {
            gio.trackCustomEvent("HelloCordova", onSucc, onFail);
              
        });

        document.getElementById("track2").addEventListener("click", function() {

            gio.trackCustomEvent("HelloCordova", {'prodId1':'pid111', 'prodId2':'pid222'}, onSucc, onFail);
        });

        document.getElementById("track3").addEventListener("click", function() {

            gio.trackCustomEvent("HelloCordova", null,"itemKey", "itemId", onSucc, onFail);
        });

        document.getElementById("track4").addEventListener("click", function() {

            gio.trackCustomEvent("HelloCordova", {'prodId1':'pid111', 'prodId2':'pid222'}, "itemKey", "itemId", onSucc, onFail);
        });

        document.getElementById("setLoginUserAttributes").addEventListener("click", function() {

            gio.setLoginUserAttributes( {'item1':'pid111', 'item2':'pid222'}, onSucc, onFail);
        });

        document.getElementById("setLoginUserId").addEventListener("click", function() {

            gio.setLoginUserId('中文中文', onSucc, onFail);
        });

        document.getElementById("cleanLoginUserId").addEventListener("click", function() {

            gio.cleanLoginUserId(onSucc, onFail);
        });

        document.getElementById("setLocation").addEventListener("click", function() {

            gio.setLocation('22.22','33.33', onSucc, onFail);
        });

        document.getElementById("cleanLocation").addEventListener("click", function() {

            gio.cleanLocation(onSucc, onFail);
        });

        document.getElementById("setDataCollectionEnabled").addEventListener("click", function() {

            gio.setDataCollectionEnabled(true, onSucc, onFail);
        });

        document.getElementById("getDeviceId").addEventListener("click", function() {

            gio.getDeviceId(onSucc, onFail);
        });

      } catch(err) {
        handleException(err);
      }
    }


};

app.initialize();