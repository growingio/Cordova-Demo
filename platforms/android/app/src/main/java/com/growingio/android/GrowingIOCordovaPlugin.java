package com.growingio.android;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growingio.android.sdk.track.GrowingTracker;
import com.growingio.android.sdk.track.log.Logger;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denghuaxin@growing.io on 2017/6/22.
 */

public class GrowingIOCordovaPlugin extends CordovaPlugin {
    private final static String TAG = "GIO.CordovaPlugin";
    private String trackPageName = null;

    private enum Action {
        TRACKCUSTOMEVENT("trackCustomEvent"),
        SETLOGINUSERATTRIBUTES("setLoginUserAttributes"),
        SETVISITORATTRIBUTES("setVisitorAttributes"),
        SETCONVERSIONVARIABLES("setConversionVariables"),
        SETLOGINUSERID("setLoginUserId"),
        CLEANLOGINUSERID("cleanLoginUserId"),
        SETLOCATION("setLocation"),
        CLEANLOCATION("cleanLocation"),
        SETDATACOLLECTIONENABLED("setDataCollectionEnabled"),
        GETDEVICEID("getDeviceId"),
        ONACTIVITYNEWINTENT("onActivityNewIntent");

        private final String name;
        private static final Map<String, Action> lookup = new HashMap<String, Action>();

        static {
            for (Action a : Action.values()) {
                lookup.put(a.getName(), a);
            }
        }

        private Action(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Action get(String name) {
            return lookup.get(name);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {
        Action act = Action.get(action);
        if (act == null) {
            this.error(callbackContext, "undefine action: " + action);
            return false;
        }
        if (args == null || callbackContext == null) {
            callbackContext.error("argument error, JSONArray or CallbackContext can not be null!");
            return false;
        }
        switch (act) {
            case TRACKCUSTOMEVENT:
                if (trackCustomEvent(args, callbackContext)) {
                    callbackContext.success("Success track custom event");
                    return true;
                }
                return false;
            case SETLOGINUSERATTRIBUTES:
                if (setLoginUserAttributes(args, callbackContext)) {
                    callbackContext.success("Success set login user attributes");
                    return true;
                }
                return false;
            case SETLOGINUSERID:
                if (setLoginUserId(args, callbackContext)) {
                    callbackContext.success("Success set login user id");
                    return true;
                }
                return false;
            case CLEANLOGINUSERID:
                if (cleanLoginUserId(args, callbackContext)) {
                    callbackContext.success("Success clean login user id");
                    return true;
                }
                return false;
            case SETLOCATION:
                if (setLocation(args, callbackContext)) {
                    callbackContext.success("Success set location");
                    return true;
                }
                return false;
            case CLEANLOCATION:
                if (cleanLocation(args, callbackContext)) {
                    callbackContext.success("Success clean location");
                    return true;
                }
                return false;
            case SETDATACOLLECTIONENABLED:
                if (setDataCollectionEnabled(args, callbackContext)) {
                    callbackContext.success("Success set data collection enabled");
                    return true;
                }
                return false;
            case GETDEVICEID:
                if (getDeviceId(args, callbackContext)) {
                    callbackContext.success("Success get device id");
                    return true;
                }
                return false;
//            case ONACTIVITYNEWINTENT:
//                if(onActivityNewIntent(args, callbackContext)){
//                    callbackContext.success("Success on activity new intent");
//                    return true;
//                }
            // TODO: 2021/4/15
//                return false;

        }
        return false;
    }

    /**
     * track(String eventName)
     * track(String eventName, Map<String, String> attributes)
     * track(String eventName, Map<String,String itemKey, String itemId)
     * track(String eventName, Map<String, String> attributes, String itemKey, String itemId)
     */
    private boolean trackCustomEvent(JSONArray jsonArray, CallbackContext callbackContext) {
        try {

            // eventName 不允许为空
            if (jsonArray.opt(0) == null) {
                callbackContext.error("argument error, The argument can not be empty.");
                return false;
            }

            String eventName;
            HashMap<String, String> userAttributes = null;
            String itemKey = null;
            String itemId = null;

            // eventName 必须为 String 类型
            if (jsonArray.opt(0) instanceof String) {
                eventName = jsonArray.optString(0);

                if (jsonArray.opt(1) instanceof JSONObject) {
                    userAttributes = new Gson().fromJson(jsonArray.opt(1).toString(),
                            new TypeToken<HashMap<String, String>>() {
                            }.getType());
                }

                if (jsonArray.opt(2) instanceof String && jsonArray.opt(3) instanceof String) {
                    itemKey = jsonArray.optString(2);
                    itemId = jsonArray.optString(3);
                }

                if (userAttributes != null && !TextUtils.isEmpty(itemKey) && !TextUtils.isEmpty(itemId)) {
                    GrowingTracker.get().trackCustomEvent(eventName, userAttributes, itemKey, itemId);
                } else if (userAttributes != null) {
                    GrowingTracker.get().trackCustomEvent(eventName, userAttributes);
                } else if (!TextUtils.isEmpty(itemKey) && !TextUtils.isEmpty(itemId)) {
                    GrowingTracker.get().trackCustomEvent(eventName, itemKey, itemId);
                } else {
                    GrowingTracker.get().trackCustomEvent(eventName);
                }

                return true;
            } else {
                callbackContext.error("argument error, The argument is illegal type.");
                return false;
            }

        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean setLoginUserAttributes(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            if (jsonArray.opt(0) == null) {
                callbackContext.error("argument error, The argument can not be empty.");
                return false;
            }

            if (jsonArray.opt(0) instanceof JSONObject) {
                Map<String, String> userAttributes = new Gson().fromJson(jsonArray.opt(0).toString(), new TypeToken<HashMap<String, String>>() {
                }.getType());
                GrowingTracker.get().setLoginUserAttributes(userAttributes);

                return true;
            } else {
                callbackContext.error("argument error, The argument is illegal type.");
                return false;
            }

        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

//    private boolean setVisitorAttributes(JSONArray jsonArray, CallbackContext callbackContext) {
//        try {
//            if (jsonArray.opt(0) == null) {
//                callbackContext.error("argument error, The argument can not be empty.");
//                return false;
//            }
//
//            if(jsonArray.opt(0) instanceof JSONObject){
//                Map<String, String> visitorAttributes = (Map<String,String>) JSON.parseObject(jsonArray.opt(0).toString(),
//                        new TypeReference<HashMap<String,String>>(){});
//
//                GrowingTracker.get().setVisitorAttributes(visitorAttributes);
//
//                return true;
//            } else {
//                callbackContext.error("argument error, The argument is illegal type.");
//                return false;
//            }
//
//        } catch (Exception e) {
//            callbackContext.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }

//    private boolean setConversionVariables(JSONArray jsonArray, CallbackContext callbackContext) {
//        try {
//            if (jsonArray.opt(0) == null) {
//                callbackContext.error("argument error, The argument can not be empty.");
//                return false;
//            }
//
//            if(jsonArray.opt(0) instanceof JSONObject){
//                Map<String, String> conversionVariables = (Map<String,String>) JSON.parseObject(jsonArray.opt(0).toString(),
//                        new TypeReference<HashMap<String,String>>(){});
//
//                GrowingTracker.get().(conversionVariables);
//
//                return true;
//            } else {
//                callbackContext.error("argument error, The argument is illegal type.");
//                return false;
//            }
//
//        } catch (Exception e) {
//            callbackContext.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }

    private boolean setLoginUserId(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            if (jsonArray.opt(0) == null) {
                callbackContext.error("Argument error, The argument can not be empty.");
                return false;
            }
            String userId;
            if (jsonArray.opt(0) instanceof Number) {
                userId = String.valueOf(jsonArray.opt(0));
            } else if (jsonArray.opt(0) instanceof String) {
                userId = jsonArray.optString(0);
            } else {
                callbackContext.error("Argument error, The argument is illegal type.");
                return false;
            }
            GrowingTracker.get().setLoginUserId(userId);
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean cleanLoginUserId(JSONArray jsonArray, CallbackContext callbackContext) {
        GrowingTracker.get().cleanLoginUserId();
        return true;
    }

    private boolean setLocation(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            if (jsonArray.length() == 0) {
                callbackContext.error("argument error, The length of JSONArray can not be 0!");
                return false;
            }

            Object args0 = jsonArray.opt(0);
            Object args1 = jsonArray.opt(1);

            double latitude;
            double longitude;

            if (args0 instanceof Double && args1 instanceof Double) {
                latitude = (Double) args0;
                longitude = (Double) args1;
            } else if (args0 instanceof String && args1 instanceof String) {
                latitude = Double.parseDouble(String.valueOf(args0));
                longitude = Double.parseDouble(String.valueOf(args1));
            } else {
                callbackContext.error("argument error, The argument is illegal type.");
                return false;
            }
            GrowingTracker.get().setLocation(latitude, longitude);
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean cleanLocation(JSONArray jsonArray, CallbackContext callbackContext) {
        GrowingTracker.get().cleanLocation();
        return true;
    }

    private boolean setDataCollectionEnabled(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            if (jsonArray.length() == 0) {
                callbackContext.error("argument error, The length of JSONArray can not be 0!");
                return false;
            }

            if (jsonArray.opt(0) instanceof Boolean) {
                GrowingTracker.get().setDataCollectionEnabled((Boolean) jsonArray.opt(0));
            } else {
                callbackContext.error("argument error, The argument is illegal type.");
                return false;
            }

        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean getDeviceId(JSONArray jsonArray, CallbackContext callbackContext) {
        GrowingTracker.get().getDeviceId();
        return true;
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void error(CallbackContext callbackContext, String message) {
        Logger.e(TAG, message);
        callbackContext.error(message);
    }
}
