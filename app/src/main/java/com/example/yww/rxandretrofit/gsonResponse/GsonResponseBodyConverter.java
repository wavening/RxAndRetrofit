package com.example.yww.rxandretrofit.gsonResponse;

import android.util.Log;

import com.example.yww.rxandretrofit.entity.Subject;
import com.example.yww.rxandretrofit.http.ApiException;
import com.example.yww.rxandretrofit.http.HttpResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Cherry123 on 2017/3/17.
 */

 public class GsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {

    private final Gson gson;

    private final Type type;
    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("Network", "response>>" + response);
        //httpResult 只解析result字段
        HttpResult httpResult = gson.fromJson(response,HttpResult.class);

        if (httpResult.getCount() == 0){throw new ApiException(100);}
        return gson.fromJson(response,type);
    }
}
