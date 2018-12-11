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
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent('deviceready');
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);



    }


};


Alert.alert('Button has been pressed!');

var MOCKED_MOVIES_DATA = [
  {title: '标题', year: '2015'},
];

var trackObject = {title: '标题1', year: '201523'};
const track = () => {
    Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.track("ggl", trackObject)
};

const trackWithNumber = () => {
    Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.trackWithNumber("trackWithNumber",12.12,trackObject)
};


const page = () => {
    // Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.page("GGLPage")
};

const setPageVariable = () => {
    // Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.setPageVariable("GGLPage",trackObject)
};

const setEvar = () => {
    // Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.setEvar(trackObject)
};

const setPeopleVariable = () => {
    // Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.setPeopleVariable(trackObject)
};

const setUserId = () => {
    // Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.setUserId("ggl")
};

const clearUserId = () => {
    // Alert.alert('Button has been pressed!');
    NativeModules.GrowingIO.clearUserId()
};






app.initialize();