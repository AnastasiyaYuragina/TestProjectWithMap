package com.anastasiyayuragina.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by anastasiyayuragina on 8/29/16.
 *
 */
public class InternetConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting();
        }

        if(isConnected) {
            InternetConnectionObservable.getInstance().dataChanged();
        } else {
            Toast.makeText(context, "no internet", Toast.LENGTH_SHORT).show();
        }
    }
}
