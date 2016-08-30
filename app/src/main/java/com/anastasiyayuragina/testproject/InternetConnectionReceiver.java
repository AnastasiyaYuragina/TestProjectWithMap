package com.anastasiyayuragina.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by anastasiyayuragina on 8/29/16.
 *
 */
public class InternetConnectionReceiver extends BroadcastReceiver {
    private String TAG = "MyLogs";
    private InternetConnectionObserver observer = new InternetConnectionObserver();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: enter");

        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting();
        }

        if(isConnected) {
            observer.dataChanged();
        } else {
            Toast.makeText(context, "no internet", Toast.LENGTH_SHORT).show();
        }
    }

}
