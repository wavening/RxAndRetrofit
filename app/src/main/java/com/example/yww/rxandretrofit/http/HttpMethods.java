package com.example.yww.rxandretrofit.http;

import com.example.yww.rxandretrofit.entity.MovieEntity;
import com.example.yww.rxandretrofit.entity.MovieService;
import com.example.yww.rxandretrofit.entity.Subject;
import com.example.yww.rxandretrofit.gsonResponse.ResponseConvertFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Cherry123 on 2017/3/16.
 */

public class HttpMethods {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private MovieService movieService;
    //构造方法私有
    private HttpMethods(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientbuilder = new OkHttpClient.Builder();
        httpClientbuilder.connectTimeout(DEFAULT_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientbuilder.build())
       // 对http请求结果进行统一的预处理 GosnResponseBodyConvert
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .baseUrl(BASE_URL)
                .build();
        movieService = retrofit.create(MovieService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }
    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovie(Subscriber<List<Subject>> subscriber, int start, int count){
//        movieService.getTopMovie(start, count)
//                .map(new HttpResultFunc<List<Subject>>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        Observable observable = movieService.getTopMovie(start,count)
                .map(new HttpResultFunc<List<Subject>>());

        toSubscribe(observable,subscriber);
    }

    private <T> void toSubscribe(Observable<T> observable,
                                 Subscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * public void getTopMovie(Subscriber<List<Subject>> subscriber,int start,int count){
        movieService.getTopMovie(0,10)
                .flatMap(new Func1<HttpResult<List<Subject>>, Observable<List<Subject>>>() {
                    @Override
                    public Observable<List<Subject>> call(HttpResult<List<Subject>> httpResult) {
                        return flatResult(httpResult);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }*/
    /**
     * 用来统一处理Http的ResultCode
     * @param result   Http请求返回的数据，用过HttpResult进行了封装
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     * @return
     */
    /**
    static <T> Observable<T> flatResult(final HttpResult<T> result) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                if (result.getCount() == 0) {
                    subscriber.onError(new ApiException(ApiException.USER_NOE_EXIST));
                } else{
                    subscriber.onNext(result.getSubjects());
                }

                subscriber.onCompleted();
            }
        });
    }
    */
    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T>{

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCount() == 0) {
                throw new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }
   /**
   private class HttpResultFunc<T> implements Func1<HttpResult<T>, T>{

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCount() == 0) {
                throw new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }*/

}
