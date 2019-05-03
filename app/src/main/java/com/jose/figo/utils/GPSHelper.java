package com.jose.figo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class GPSHelper {
    private static final String TAG = GPSHelper.class.getSimpleName();
    public GPSHelper(){

    }

    public boolean isRequestPermission(Activity activity, int code_request_location){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                if(activity.shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION)){
                    activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            code_request_location);
                    //Log.d(TAG,"IS NECESSARY REQUEST FOR PERMISSION");
                    return true;
                }else{
                    //Log.d(TAG,"IS NECESSARY REQUEST FOR PERMISSION");
                    activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            code_request_location);
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}
