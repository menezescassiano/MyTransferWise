package com.cassianomenezes.mytransferwise.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class RequestUtil {

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
