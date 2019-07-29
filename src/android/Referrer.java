package com.github.byorty;

import org.apache.cordova.*;
import org.json.*;
import com.android.installreferrer.api.*;

public class Referrer extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("get")) {
            InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(this.cordova.getActivity().getApplicationContext()).build();
            referrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    if (responseCode == InstallReferrerResponse.OK) {
                        ReferrerDetails response = referrerClient.getInstallReferrer();
                        JSONObject result = new JSONObject();
                        result.put("referrer", response.getInstallReferrer());
                        result.put("clickTimestamp", response.getReferrerClickTimestampSeconds());
                        result.put("installBeginTimestamp", response.getInstallBeginTimestampSeconds());
                        callbackContext.suceess(result.toString());
                    } else {
                        JSONObject result = new JSONObject();
                        result.put("error", responseCode);
                        callbackContext.error(result.toString());
                    }

                    referrerClient.endConnection();
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {}
            });
            return true;
        } else {
            return false;
        }
    }
}