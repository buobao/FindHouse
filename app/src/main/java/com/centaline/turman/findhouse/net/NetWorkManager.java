package com.centaline.turman.findhouse.net;

import com.centaline.turman.findhouse.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diaoqf on 2016/7/15.
 */
public class NetWorkManager {

    private static Retrofit client = null;

    private NetWorkManager(){}

    public static Retrofit getClient(){
        if (client == null) {
            //okhttp log intercepter
            Interceptor okhttpLogInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (BuildConfig.DEBUG) {
                        long t1 = System.nanoTime();
                        Response response = chain.proceed(request);
                        long t2 = System.nanoTime();

                        double time = (t2 - t1) / 1e6d;

                        String msg = "%s\nurl->" + request.url() + "\ntime->" + time + "\nheaders->" + request.headers() + "\nresponse code->" + response.code() + "\nresponse headers->" + response.headers() + "\nbody->" + response.body().string();
                        if (request.method().equals("GET")) {
                            Logger.i(msg, "GET");
                        } else if (request.method().equals("POST")) {
                            Logger.i(msg, "POST");
                        } else if (request.method().equals("PUT")) {
                            Logger.i(msg, "PUT");
                        } else if (request.method().equals("DELETE")) {
                            Logger.i(msg, "DELETE");
                        }
                    }
                    return chain.proceed(request);
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(20,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .addInterceptor(okhttpLogInterceptor)
                    .build();

            client = new Retrofit.Builder()
                    .baseUrl(NetContents.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return client;
    }

}
