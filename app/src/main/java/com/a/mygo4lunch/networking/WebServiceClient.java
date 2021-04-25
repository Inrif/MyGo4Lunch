package com.a.mygo4lunch.networking;

import com.a.mygo4lunch.tools.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {
        private final  static  String BaseUrl = Constant.PLACE_API_BASE_URL;
        private static WebServiceClient INSTANCE = null;

        private WebServiceClient() {
        }

        public static WebServiceClient getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new WebServiceClient();
            }
            return INSTANCE;
        }

        public Retrofit getClient() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            return retrofit;
        }

}
