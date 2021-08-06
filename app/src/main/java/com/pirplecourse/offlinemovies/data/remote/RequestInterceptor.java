package com.pirplecourse.offlinemovies.data.remote;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
//* con esto interceptamos y concatenamos la apikey como parametro query
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key",ApiConstantes.API_LEY)
                .build();
        Request request = originalRequest.newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }
}
