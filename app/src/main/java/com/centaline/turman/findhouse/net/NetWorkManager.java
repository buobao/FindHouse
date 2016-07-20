package com.centaline.turman.findhouse.net;

import com.centaline.turman.findhouse.BuildConfig;
import com.centaline.turman.findhouse.utils.secret.SHA1;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diaoqf on 2016/7/15.
 */
public class NetWorkManager {

    private static final int RONG_CLOUD_HEAD = 0;
    private static final int COMMON_HEAD = 1;

    private static Retrofit commonClient = null;
    private static Retrofit rongClouldClient = null;

    private NetWorkManager(){}

    public static Retrofit getCommonClient(){
        if (commonClient == null) {
            commonClient = new Retrofit.Builder()
                    .baseUrl(NetContents.BASE_HOST)
                    .client(getHttpClient(COMMON_HEAD))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return commonClient;
    }

    public static Retrofit getRongCloudClient(){


        if (rongClouldClient == null) {
            rongClouldClient = new Retrofit.Builder()
                    .baseUrl(NetContents.RONG_CLOUD_HOST)
                    .client(getHttpClient(RONG_CLOUD_HEAD))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rongClouldClient;
    }

    public static OkHttpClient getHttpClient(final int headType){
        //okhttp log intercepter
        Interceptor okhttpLogInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                //add headers
                request = addHeaders(headType, request);

                if (BuildConfig.DEBUG) {
                    long t1 = System.nanoTime();
                    Response response = chain.proceed(request);
                    long t2 = System.nanoTime();

                    double time = (t2 - t1) / 1e6d;

                    String msg = "%s\nurl->" + request.url()
                            + "\ntime->" + time
                            + "ms\nheaders->" + request.headers()
                            + "\nresponse code->" + response.code()
                            + "\nresponse headers->" + response.headers()
                            + "\nbody->" + response.body().string();

                    if (request.method().equals("GET")) {
                        Logger.i(msg, "GET");
                    } else if (request.method().equals("POST")) {
                        Request copyRequest = request.newBuilder().build();
                        if (copyRequest.body() instanceof FormBody) {
                            Buffer buffer = new Buffer();
                            copyRequest.body().writeTo(buffer);
                            Logger.i("request params:" + buffer.readUtf8());
                        }
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

    /**
     * 添加请求头
     * @param type 区分不同的API
     * @param request
     * @return
     */
    private static Request addHeaders(int type, Request request){
        Request.Builder requestBuild = request.newBuilder();
        switch (type) {
            case RONG_CLOUD_HEAD:
                String nonce = (int)(Math.random()*1000000000)+"";
                String timestamp = System.currentTimeMillis()+"";
                String signature = SHA1.encode(BuildConfig.RONG_CLOUD_SECRET+nonce+timestamp);
                requestBuild.addHeader("App-Key",BuildConfig.RONG_CLOUD_KEY)
                        .addHeader("Nonce",nonce)
                        .addHeader("Timestamp",timestamp)
                        .addHeader("Signature",signature);
                break;
        }

        return requestBuild.build();
    }

}
