package com.xiwang.jxw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xiwang.jxw.event.NetWorkEvent;
import com.xiwang.jxw.util.NetWorkUtil;
import com.xiwang.jxw.util.ToastUtil;

import de.greenrobot.event.EventBus;


/**
 * @author liangxg
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public static boolean connectionFla = true;
    public static String netType = "";
    /** Class of broadly defined "Wi-Fi" networks. */
    public static final String NETWORK_CLASS_WIFI = "Wi-Fi";
    /** Class of broadly defined "UNKNOWN" networks. */
    public static final String NETWORK_CLASS_UNKNOWN = "UNKNOWN";
    /** Class of broadly defined "2G" networks. */
    public static final String NETWORK_CLASS_2_G = "2G";
    /** Class of broadly defined "3G" networks. */
    public static final String NETWORK_CLASS_3_G = "3G";
    /** Class of broadly defined "4G" networks. */
    public static final String NETWORK_CLASS_4_G = "4G";



    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        netType=NetWorkUtil.getCurrentNetworkType();
       // ToastUtil.showToast(context,netType);
        if (activeNetInfo != null && activeNetInfo.isAvailable()) {
            connectionFla = true;
//            Intent autoLoginIntent = new Intent(Global.BROADCAST_RECOVERY_NETWORK_LOGIN);
//            context.sendBroadcast(autoLoginIntent);
            EventBus.getDefault().post(new NetWorkEvent());
        } else {
            connectionFla = false;
        }
    }
}