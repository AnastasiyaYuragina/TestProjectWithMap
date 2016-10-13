package com.anastasiyayuragina.testproject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by anastasiyayuragina on 10/13/16.
 *
 */

public class RetrofitSingleton {
    private static RetrofitSingleton instance;
    private Retrofit retrofit;

    public static RetrofitSingleton getInstance() {
        RetrofitSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (RetrofitSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RetrofitSingleton();
                }
            }
        }
        return localInstance;
    }

    private RetrofitSingleton() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.worldbank.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
