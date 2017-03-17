package com.example.yww.rxandretrofit.subscribers;

import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by Cherry123 on 2017/3/17.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private Context context;
    private SubscriberOnNextListener mSubscriberOnNextListener;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.context = context;
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }
}
