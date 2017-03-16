package com.example.yww.rxandretrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.http.HttpMethod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
private static final String TAG ="";
    @BindView(R.id.click_me_BTN)
    Button clickMeBTN;
    @BindView(R.id.result_TV)
    TextView resultTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }
private static String ToastInternetMessage = "网络连接成功";
    @Override
    protected void onStart() {
        super.onStart();
       boolean getInternetB = IsInternetConnected.isNetworkAvalible(MainActivity.this);
        if (getInternetB = true){Toast.makeText(MainActivity.this,ToastInternetMessage,Toast.LENGTH_SHORT).show();}

    }

    @OnClick({R.id.click_me_BTN, R.id.result_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_me_BTN:
                getMovie();
                break;
            case R.id.result_TV:
                break;
        }
    }

    private Subscriber<MovieEntity> subscriber;

    //进行网络请求
    private void getMovie(){
       /* String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);*/
       /* Call<MovieEntity> call = movieService.getTopMovie(0, 10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                resultTV.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                resultTV.setText(t.getMessage());
            }
        });*/

       /*movieService.getTopMovie(0,10)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<MovieEntity>() {
                   @Override
                   public void onCompleted() {
                       Toast.makeText(MainActivity.this,"Get Top Movie Completed !",Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onError(Throwable e) {
                       resultTV.setText(e.getMessage());
                   }

                   @Override
                   public void onNext(MovieEntity movieEntity) {
                       resultTV.setText(movieEntity.toString());
                   }
               });*/

       subscriber = new Subscriber<MovieEntity>() {
           @Override
           public void onCompleted() {
               Toast.makeText(MainActivity.this,"Get Top Movie Completed!",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(Throwable e) {
               resultTV.setText(e.getMessage());
           }

           @Override
           public void onNext(MovieEntity movieEntity) {
               resultTV.setText(movieEntity.toString());
           }
       };
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }
}
