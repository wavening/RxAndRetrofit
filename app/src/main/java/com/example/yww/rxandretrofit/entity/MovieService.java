package com.example.yww.rxandretrofit.entity;

import com.example.yww.rxandretrofit.http.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Cherry123 on 2017/3/16.
 */

public interface MovieService  {
    /*@GET("top250")
    Call<MovieEntity> getTopMovie (@Query("start")int start,@Query("count")int count);
*/
/*
    @GET("top250")
    Observable<MovieEntity> getTopMovie (@Query("start")int start, @Query("count")int count);*/

@GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start")int start,@Query("count")int count);
}
