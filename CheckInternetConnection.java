package com.bugchain.thaipbsnews.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by POSEIDON on 10/6/2558.
 */
public class CheckInternetConnection {

    public boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null){
                for(int i=0;i<info.length;i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
