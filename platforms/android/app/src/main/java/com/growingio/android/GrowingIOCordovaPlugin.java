package com.growingio.android;

import android.text.TextUtils;

import com.growingio.android.sdk.collection.GrowingIO;
import com.growingio.android.sdk.utils.LogUtil;

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

public class GrowingIOCordovaPlugin extends CordovaPlugin{
    private final static String TAG = "GIO.CordovaPlugin";
    private String trackPageName = null;

    private enum Action {
        SETUSERID("setUserId"),
        CLEARUSERID("clearUserId"),
        TRACK("track"),
	SETVISITOR("setVisitor"),
        SETEVAR("setEvar"),
        SETPEOPLEVARIABLE("setPeopleVariable");

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
            callbackContext.error("Argment error, JSONArray or CallbackContext can not be null!");
            return false;
        }
        switch (act) {
            case SETUSERID:
                return setUserId(args, callbackContext);
            case CLEARUSERID:
                return cleanUserId(args, callbackContext);
            case TRACK:
                return track(args, callbackContext);
            case SETEVAR:
                return setEvar(args, callbackContext);
            case SETPEOPLEVARIABLE:
                return setPeopleVariable(args, callbackContext);
	    case SETVISITOR:
		return setVisitor(args, callbackContext);
        }
        return false;
    }



    private boolean setPeopleVariable(JSONArray jsonArray, CallbackContext callbackContext) {
        if (jsonArray.length() == 0) {
            callbackContext.error("Argment error, The length of JSONArray can not be 0!");
            return false;
        }
        try {
            if (jsonArray.get(0) != null && jsonArray.get(0) instanceof JSONObject) {
                GrowingIO.getInstance().setPeopleVariable((JSONObject) jsonArray.get(0));
            } else {
                callbackContext.error("The argment must be JSONObject!");
                return false;
            }
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

	private boolean setVisitor(JSONArray jsonArray, CallbackContext callbackContext) {
		        if (jsonArray.length() == 0) {
            callbackContext.error("Argment error, The length of JSONArray can not be 0!");
            return false;
        }
        try {
            if (jsonArray.get(0) != null && jsonArray.get(0) instanceof JSONObject) {
                GrowingIO.getInstance().setVisitor((JSONObject) jsonArray.get(0));
            } else {
                callbackContext.error("The argment must be JSONObject!");
                return false;
            }
        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return true;
	}

    private boolean setEvar(JSONArray jsonArray, CallbackContext callbackContext) {
        if (jsonArray.length() == 0) {
            callbackContext.error("Argment error, The length of JSONArray can not be 0!");
            return false;
        }
        try {
            if (jsonArray.get(0) != null && jsonArray.get(0) instanceof JSONObject) {
                GrowingIO.getInstance().setEvar((JSONObject) jsonArray.get(0));
            } else {
                callbackContext.error("The argment must be JSONObject!");
                return false;
            }
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    private boolean setUserId(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            if (jsonArray.opt(0) == null) {
                callbackContext.error("Argment error, The argment can not be empty.");
                return false;
            }
            String useid = null;
            if (jsonArray.opt(0) instanceof Number) {
                useid = String.valueOf(jsonArray.opt(0));
            } else if (jsonArray.opt(0) instanceof String) {
                useid = jsonArray.optString(0);
            } else {
                callbackContext.error("Argment error, The argment is illegal type.");
                return false;
            }
            GrowingIO.getInstance().setUserId(useid);
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean cleanUserId(JSONArray jsonArray, CallbackContext callbackContext) {
        GrowingIO.getInstance().clearUserId();
        return true;
    }

    private boolean track(JSONArray jsonArray, CallbackContext callbackContext) {
        try {
            if (jsonArray.length() == 0) {
                callbackContext.error("Argment error, The length of JSONArray can not be 0!");
                return false;
            }
			Object first = jsonArray.opt(0);
			Object second = jsonArray.opt(1);
			Object third = jsonArray.opt(2);
			if (first instanceof String){
				if (second instanceof Number){
					if (third instanceof JSONObject){
						GrowingIO.getInstance().track((String)first, (Number)second, (JSONObject) third);
					}else{
						GrowingIO.getInstance().track((String)first, (Number)second);
					}
				}else if (second instanceof JSONObject){
					GrowingIO.getInstance().track((String)first, (JSONObject)second);
				}else{
					GrowingIO.getInstance().track((String)first);
				}		
				return true;
			}
            callbackContext.error("Argment error, The length of JSONArray can not be 0!");
            return false;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
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
        LogUtil.e(TAG, message);
        callbackContext.error(message);
    }
}
