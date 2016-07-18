package com.centaline.turman.findhouse.net;

import com.centaline.turman.findhouse.BuildConfig;
import com.centaline.turman.findhouse.utils.secret.SHA1;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
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

    private static Retrofit commonClient = null;
    private static Retrofit rongClouldClient = null;

    private NetWorkManager(){}

    public static Retrofit getCommonClient(){
        if (commonClient == null) {
            commonClient = new Retrofit.Builder()
                    .baseUrl(NetContents.BASE_HOST)
                    .client(getHttpClient(null))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return commonClient;
    }

    public static Retrofit getRongCloudClient(){
        Map<String, String> headers = new HashMap<>();
        headers.put("App-Key", BuildConfig.RONG_CLOUD_KEY);
        String nonce = (int)(Math.random()*1000000000)+"";
        headers.put("Nonce",nonce);
        String timestamp = System.currentTimeMillis()+"";
        headers.put("Timestamp",timestamp);
        headers.put("Signature", SHA1.encode(BuildConfig.RONG_CLOUD_SECRET+nonce+timestamp));

        if (rongClouldClient == null) {
            rongClouldClient = new Retrofit.Builder()
                    .baseUrl(NetContents.RONG_CLOUD_HOST)
                    .client(getHttpClient(headers))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rongClouldClient;
    }

    public static OkHttpClient getHttpClient(final Map<String,String> headers){
        //okhttp log intercepter
        Interceptor okhttpLogInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                //add headers
                if (headers != null && headers.size() > 0) {
                    Request.Builder requestBuild = request.newBuilder();
                    for (String key:headers.keySet()){
                        requestBuild.addHeader(key,headers.get(key));
                    }
                    request = requestBuild.build();
                }

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

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(okhttpLogInterceptor)
                .build();
    }

}
