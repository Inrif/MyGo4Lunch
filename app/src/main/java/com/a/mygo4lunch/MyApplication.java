package com.a.mygo4lunch;

import android.app.Application;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.a.mygo4lunch.networking.NearByApi;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Romuald HOUNSA on 21/02/2021.
 */
public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
    }



}
