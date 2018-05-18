package com.example.xiaochenbing.weatherforecast.net;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class MyCallBack implements Callback {
    public abstract void success(JSONObject result);

    public abstract void failure(String message);

    @Override
    public void onFailure(Call call, IOException e) {
        failure(e.toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.body() != null) {
            String responsedata = response.body().string();
            try {
                JSONObject object = new JSONObject(responsedata);
//                JSONObject object = new JSONObject("{\"date\":\"20180516\",\"message\":\"Success !\",\"status\":200,\"city\":\"北京\",\"count\":143,\"data\":{\"shidu\":\"87%\",\"pm25\":43.0,\"pm10\":75.0,\"quality\":\"良\",\"wendu\":\"22\",\"ganmao\":\"极少数敏感人群应减少户外活动\",\"yesterday\":{\"date\":\"15日星期二\",\"sunrise\":\"05:01\",\"high\":\"高温 30.0℃\",\"low\":\"低温 22.0℃\",\"sunset\":\"19:21\",\"aqi\":110.0,\"fx\":\"东南风\",\"fl\":\"<3级\",\"type\":\"小雨\",\"notice\":\"雨虽小，注意保暖别感冒\"},\"forecast\":[{\"date\":\"16日星期三\",\"sunrise\":\"05:00\",\"high\":\"高温 29.0℃\",\"low\":\"低温 21.0℃\",\"sunset\":\"19:22\",\"aqi\":75.0,\"fx\":\"东风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},{\"date\":\"17日星期四\",\"sunrise\":\"04:59\",\"high\":\"高温 25.0℃\",\"low\":\"低温 19.0℃\",\"sunset\":\"19:23\",\"aqi\":49.0,\"fx\":\"东北风\",\"fl\":\"<3级\",\"type\":\"小雨\",\"notice\":\"雨虽小，注意保暖别感冒\"},{\"date\":\"18日星期五\",\"sunrise\":\"04:58\",\"high\":\"高温 28.0℃\",\"low\":\"低温 15.0℃\",\"sunset\":\"19:24\",\"aqi\":60.0,\"fx\":\"南风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},{\"date\":\"19日星期六\",\"sunrise\":\"04:57\",\"high\":\"高温 29.0℃\",\"low\":\"低温 16.0℃\",\"sunset\":\"19:25\",\"aqi\":73.0,\"fx\":\"东南风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},{\"date\":\"20日星期日\",\"sunrise\":\"04:57\",\"high\":\"高温 26.0℃\",\"low\":\"低温 15.0℃\",\"sunset\":\"19:26\",\"aqi\":86.0,\"fx\":\"东北风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"}]}}");
                if (object.getString("message").equals("Success !")) {
                    success(object);
                }else {
                    failure(object.getString("message"));
                }
            } catch (JSONException e) {
                failure(e.toString());
            }
        }
    }
}
