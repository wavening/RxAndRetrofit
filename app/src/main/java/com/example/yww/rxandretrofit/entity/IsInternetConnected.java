package com.example.yww.rxandretrofit.entity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.example.yww.rxandretrofit.R;

import java.util.List;

/**
 * Created by Cherry123 on 2017/3/16.
 */

public class IsInternetConnected {

    /**
     * 一、判断网络情况
     * @param context 上下文
     * @return false 表示没有网络 true 表示有网络
     */
    public static boolean isNetworkAvalible(Context context){
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null){return false;}
        else{
            // 建立网络数组  
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null){
                for (int i = 0;i < net_info.length;i++){
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED)
                    {return true;}
                }
            }
        }
        return false;
    }

    // 如果没有网络，则弹出网络设置对话框
    public static void checkNetwork(final Activity activity){
        if (!isNetworkAvalible(activity)){
            TextView msg = new TextView(activity);
            msg.setText("当前没有可以使用的网络，请设置网络！");

            new AlertDialog.Builder(activity)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("网络状态提示")
                    .setView(msg)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS),0);
                                }
                            }).create();
            return;
        }
    }

    /**
     * 二、判断GPS是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context){
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = locationManager.getProviders(true);

        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     *  三、判断WIFI是否打开
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 四、判断是否是3G网络
     * @param context
     * @return
     */

    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 五、判断是wifi还是3g网络,用户的体现性在这里了，wifi就可以建议下载或者在线播放。
     * @param context
     * @return
     */

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
