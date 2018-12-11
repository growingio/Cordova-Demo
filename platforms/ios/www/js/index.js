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
        alert('track');
    },

    onDeviceResume: function() {
        app.initGrowingIO();
        alert('track');
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
        gio.page('pagename');
        gio.setPageVariable('pagename', {"key":"value"});

        document.getElementById("track1").addEventListener("click", function() {
            alert('track');
            gio.track("purchase", onSucc, onFail);
        });

        document.getElementById("track2").addEventListener("click", function() {
            alert('track');
            gio.track("purchase", 234234.123123, onSucc, onFail);
        });

        document.getElementById("track3").addEventListener("click", function() {
            alert('track');
            gio.track('order', {'prodId':'pid121212'}, onSucc, onFail);
        });

        document.getElementById("track4").addEventListener("click", function() {
            alert('track');
            gio.track("purchase", 234234344.22, { item: '123' }, onSucc, onFail);
        });

        document.getElementById("trackWithNumber").addEventListener("click", function() {
            alert('trackWithNumber');

            gio.track("purchase", { item: '123' }, onSucc, onFail);
        });

        document.getElementById("setEvar").addEventListener("click", function() {
            alert('setEvar');
            gio.setEvar({'campaignId':'1234567890', 'campaignOwner':'李四'});

        });

        document.getElementById("page").addEventListener("click", function() {
            alert('page');
            gio.page('我的天啊');
        });

        document.getElementById("setPeopleVariable").addEventListener("click", function() {
            alert('setPeopleVariable');
            gio.setPeopleVariable({'VIPLevel':'Silver', 'gender':'male'});
        });

        document.getElementById("setPageVariable").addEventListener("click", function() {
            alert('setPageVariable');
            gio.setPageVariable('这是page',{'pageName': 'Home Page', 'author': 'Zhang San'})
        });

        document.getElementById("setUserId").addEventListener("click", function() {
            alert('setUserId');
            gio.setUserId('王颖颖');
        });

        document.getElementById("clearUserId").addEventListener("click", function() {
            alert('clearUserId');
            gio.clearUserId();
        });

      } catch(err) {
        handleException(err);
      }
    }


};



// var MOCKED_MOVIES_DATA = [
//   {title: '标题', year: '2015'},
// ];

// var trackObject = {title: '标题1', year: '201523'};
// const track = () => {
//     Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.track("ggl", trackObject)
// };

// const trackWithNumber = () => {
//     Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.trackWithNumber("trackWithNumber",12.12,trackObject)
// };


// const page = () => {
//     // Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.page("GGLPage")
// };

// const setPageVariable = () => {
//     // Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.setPageVariable("GGLPage",trackObject)
// };

// const setEvar = () => {
//     // Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.setEvar(trackObject)
// };

// const setPeopleVariable = () => {
//     // Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.setPeopleVariable(trackObject)
// };

// const setUserId = () => {
//     // Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.setUserId("ggl")
// };

// const clearUserId = () => {
//     // Alert.alert('Button has been pressed!');
//     NativeModules.GrowingIO.clearUserId()
// };






app.initialize();
