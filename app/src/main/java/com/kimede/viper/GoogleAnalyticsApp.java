package com.kimede.viper;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kimede.viper.Models.Configure;
import com.kimede.viper.interfaces.CanalService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GoogleAnalyticsApp extends Application {
    public List<Configure> configure;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public void getIdBanners() {
        new Retrofit.Builder().baseUrl(getResources().getString(R.string.baseurl)).
                addConverterFactory(GsonConverterFactory.create()).build().
                create(CanalService.class).
                GetConfigure().enqueue(new Callback<List<Configure>>() {
            @Override
            public void onResponse(Call<List<Configure>> call, Response<List<Configure>> response) {
                configure = (List) response.body();
            }

            @Override
            public void onFailure(Call<List<Configure>> call, Throwable t) {

            }
        });
    }

    public void onCreate() {
        super.onCreate();
        getIdBanners();
        Fresco.initialize(this);


    }

    public void onTerminate() {
        super.onTerminate();
    }
}
