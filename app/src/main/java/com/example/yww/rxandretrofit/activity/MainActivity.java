package com.example.yww.rxandretrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yww.rxandretrofit.entity.Subject;
import com.example.yww.rxandretrofit.http.HttpMethods;
import com.example.yww.rxandretrofit.entity.IsInternetConnected;
import com.example.yww.rxandretrofit.entity.MovieEntity;
import com.example.yww.rxandretrofit.R;
import com.example.yww.rxandretrofit.http.HttpResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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

    private Subscriber subscriber;

    //进行网络请求
    private void getMovie(){
//        String baseUrl = "https://api.douban.com/v2/movie/";
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        MovieService movieService = retrofit.create(MovieService.class);
//        Call<MovieEntity> call = movieService.getTopMovie(0, 10);
//        call.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                resultTV.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                resultTV.setText(t.getMessage());
//            }
//        });

//        movieService.getTopMovie(0, 10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        resultTV.setText(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(MovieEntity movieEntity) {
//                        resultTV.setText(movieEntity.toString());
//                    }
//                });
        subscriber = new Subscriber<HttpResult<List<Subject>>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                resultTV.setText(e.getMessage());
            }

            @Override
            public void onNext(HttpResult<List<Subject>> movieEntity) {
                resultTV.setText(movieEntity.toString());
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }
}
