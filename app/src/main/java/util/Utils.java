package util;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }

    public  static  void  finish ( Activity activity ) {
        activity.finish();
    }
    
}
