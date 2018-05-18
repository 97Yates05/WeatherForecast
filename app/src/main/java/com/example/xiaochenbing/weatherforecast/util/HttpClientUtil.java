package com.example.xiaochenbing.weatherforecast.util;

import okhttp3.OkHttpClient;

public class HttpClientUtil {
    public static final OkHttpClient client = new OkHttpClient();
    public static OkHttpClient getClient(){return client;}
}
