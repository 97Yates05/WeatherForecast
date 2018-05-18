package com.example.xiaochenbing.weatherforecast.net;


import com.example.xiaochenbing.weatherforecast.util.HttpClientUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyHttp {
    public final String BASE_URL = "https://www.sojson.com/open/api/weather/json.shtml?city=";


    public void getInfo(String city_name, MyCallBack callBack) {
        OkHttpClient client = HttpClientUtil.getClient();
        Request request = new Request.Builder().url(BASE_URL + city_name).build();
        client.newCall(request).enqueue(callBack);

    }
}
