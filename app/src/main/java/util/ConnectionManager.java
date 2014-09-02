package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {

    public final static String PATH = "http://192.168.1.149:3000/api/v1/";

    private final static String LOGTAG = "ConnectionManager";

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }

}