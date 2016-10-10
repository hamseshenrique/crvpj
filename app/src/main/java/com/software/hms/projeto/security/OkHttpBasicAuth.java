package com.software.hms.projeto.security;


import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by root on 21/08/16.
 */
public class OkHttpBasicAuth {

    public static OkHttpClient createHead(final String token){

        final TokenService tokenService = new TokenService();

        final okhttp3.OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request original = chain.request();

                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                final String data = simpleDateFormat.format(new Date());

                final okhttp3.Request copy = original.newBuilder().build();
                String authSing = "";
                if(copy != null && copy.body()!= null){
                    final okio.Buffer buffer = new okio.Buffer();
                    copy.body().writeTo(buffer);
                    authSing = tokenService.criptSenha(buffer.readUtf8());
                }else{
                    authSing = tokenService.criptSenha("");
                }

                final StringBuilder toSing = new StringBuilder();
                toSing.append(original.method());
                toSing.append(authSing);
                toSing.append("application/json; charset=UTF-8");
                toSing.append(data);
                toSing.append(original.url().toString());

                final StringBuilder sing = new StringBuilder();
                sing.append(token).append(":").append(tokenService.criptSenha(toSing.toString()));

                okhttp3.Request request = original.newBuilder()
                        .header("Content-Type","application/json")
                        .header("Authorization",sing.toString().trim())
                        .header("Date",data)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        }).readTimeout(100000, TimeUnit.MILLISECONDS).build();

        return okHttpClient;
    }
}
