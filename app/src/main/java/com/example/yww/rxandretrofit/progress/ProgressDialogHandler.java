package com.example.yww.rxandretrofit.progress;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Cherry123 on 2017/3/17.
 */

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int  DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog progressDialog;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable){
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }
    //初始化ProgressDialog
    private void initProgressDialog(){
        //创建ProgressDialog实例
        if (progressDialog == null){progressDialog = new ProgressDialog(context);}
        progressDialog.setCancelable(cancelable);

        //设置取消ProgressDialog的监听
        if (cancelable){
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mProgressCancelListener.onCancelProgress();
                }
            });
        }
        //将progressDialog显示出来
        if (!progressDialog.isShowing()){progressDialog.show();}
    }
//创建私有的ProgressDialog取消方法
    private void dismissProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }



    public void handleMessage(int num){
        switch (num){
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }

    }
    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
