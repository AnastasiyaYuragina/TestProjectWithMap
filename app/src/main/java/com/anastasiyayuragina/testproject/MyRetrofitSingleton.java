package com.anastasiyayuragina.testproject;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by anastasiyayuragina on 8/1/16.
 *
 */
public class MyRetrofitSingleton {
    private static MyRetrofitSingleton instance;
    private Retrofit retrofit;

    public static MyRetrofitSingleton getInstance() {
        MyRetrofitSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (MyRetrofitSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MyRetrofitSingleton();
                }
            }
        }
        return localInstance;
    }

    private MyRetrofitSingleton() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.worldbank.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }


}
