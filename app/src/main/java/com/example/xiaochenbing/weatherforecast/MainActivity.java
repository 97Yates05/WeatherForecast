package com.example.xiaochenbing.weatherforecast;

import android.Manifest;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiaochenbing.weatherforecast.db.WeatherDao;
import com.example.xiaochenbing.weatherforecast.enity.Weather;
import com.example.xiaochenbing.weatherforecast.net.MyCallBack;
import com.example.xiaochenbing.weatherforecast.net.MyHttp;
import com.example.xiaochenbing.weatherforecast.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.table.TableUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView city_name, current_date, current_temperature, notice,
            today, today_high, today_low, tomorrow, tomorrow_high, tomorrow_low,
            third_day, third_high, third_low, fourth_day, fourth_high, fourth_low,
            fifth_day, fifth_high, fifth_low, sunrise, sunset, wind_direction,
            wind_energy, air_quality, pm25, pm10, aqi, wet;
    private ImageView today_weather, tomorrow_weather, third_weather,
            fourth_weather, fifth_weather;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView scrollView;
    private EditText input_city_name;
    private Button search;
    private List<Weather> weatherList;
    private Weather weather = new Weather();
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();
        setTime();
    }


    private void loadNetData(String str) {                 //通过网络获得数据
        new MyHttp().getInfo(str, new MyCallBack() {
            @Override
            public void success(JSONObject result) {
                bindData(result);
            }

            @Override
            public void failure(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void bindData(final JSONObject result) {            //通过网络数据绑定视图
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    city_name.setText(result.getString("city"));
                    JSONObject jsonObject = result.getJSONObject("data");
                    current_temperature.setText(jsonObject.getString("wendu") + "°");
                    notice.setText(jsonObject.getString("ganmao"));
                    wet.setText(jsonObject.getString("shidu"));
                    pm25.setText(jsonObject.getString("pm25"));
                    pm10.setText(jsonObject.getString("pm10"));
                    air_quality.setText(jsonObject.getString("quality"));
                    Gson gson = GsonUtil.getGson();
                    weatherList = gson.fromJson(String.valueOf(jsonObject.getJSONArray("forecast")),
                            new TypeToken<List<Weather>>() {
                            }.getType());
                    //今天的天气数据绑定
                    weather = weatherList.get(0);
                    weather.setCity_name(result.getString("city"));
                    today_high.setText(weather.getHigh().substring(3));
                    today_low.setText(weather.getLow().substring(3));
                    if (weather.getType().equals("多云")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.cloudy).into(today_weather);
                    } else if (weather.getType().equals("小雨")||weather.getType().equals("中雨")
                            ||weather.getType().equals("大雨")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.rain).into(today_weather);
                    } else {
                        Glide.with(MainActivity.this).
                                load(R.drawable.sunny).into(today_weather);
                    }
                    sunrise.setText(weather.getSunrise());
                    sunset.setText(weather.getSunset());
                    wind_direction.setText(weather.getFx());
                    wind_energy.setText(weather.getFl());
                    aqi.setText(String.valueOf(weather.getAqi()));
                    //明天的数据绑定
                    weather = weatherList.get(1);
                    weather.setCity_name(result.getString("city"));
                    tomorrow_high.setText(weather.getHigh().substring(3));
                    tomorrow_low.setText(weather.getLow().substring(3));
                    if (weather.getType().equals("多云")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.cloudy).into(tomorrow_weather);
                    } else if (weather.getType().equals("小雨")||weather.getType().equals("中雨")
                            ||weather.getType().equals("大雨")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.rain).into(tomorrow_weather);
                    } else {
                        Glide.with(MainActivity.this).
                                load(R.drawable.sunny).into(tomorrow_weather);
                    }
                    //第三天的数据绑定
                    weather = weatherList.get(2);
                    weather.setCity_name(result.getString("city"));
                    third_high.setText(weather.getHigh().substring(3));
                    third_low.setText(weather.getLow().substring(3));
                    if (weather.getType().equals("多云")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.cloudy).into(third_weather);
                    } else if (weather.getType().equals("小雨")||weather.getType().equals("中雨")
                            ||weather.getType().equals("大雨")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.rain).into(third_weather);
                    } else {
                        Glide.with(MainActivity.this).
                                load(R.drawable.sunny).into(third_weather);
                    }
                    //第四天的数据绑定
                    weather = weatherList.get(3);
                    weather.setCity_name(result.getString("city"));
                    fourth_high.setText(weather.getHigh().substring(3));
                    fourth_low.setText(weather.getLow().substring(3));
                    if (weather.getType().equals("多云")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.cloudy).into(fourth_weather);
                    } else if (weather.getType().equals("小雨")||weather.getType().equals("中雨")
                            ||weather.getType().equals("大雨")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.rain).into(fourth_weather);
                    } else {
                        Glide.with(MainActivity.this).
                                load(R.drawable.sunny).into(fourth_weather);
                    }
                    //第五天的数据绑定
                    weather = weatherList.get(4);
                    weather.setCity_name(result.getString("city"));
                    fifth_high.setText(weather.getHigh().substring(3));
                    fifth_low.setText(weather.getLow().substring(3));
                    if (weather.getType().equals("多云")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.cloudy).into(fifth_weather);
                    } else if (weather.getType().equals("小雨")||weather.getType().equals("中雨")
                            ||weather.getType().equals("大雨")) {
                        Glide.with(MainActivity.this).
                                load(R.drawable.rain).into(fifth_weather);
                    } else {
                        Glide.with(MainActivity.this).
                                load(R.drawable.sunny).into(fifth_weather);
                    }
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "加载成功",
                            Toast.LENGTH_SHORT).show();
                    //将网络数据保存到本地数据库
                    new WeatherDao(MainActivity.this).addWeather(weatherList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void init_view() {
        city_name = findViewById(R.id.city_name);
        current_date = findViewById(R.id.current_date);
        current_temperature = findViewById(R.id.current_temperature);
        notice = findViewById(R.id.notice);
        today = findViewById(R.id.today);
        today_high = findViewById(R.id.today_high);
        today_low = findViewById(R.id.today_low);
        tomorrow = findViewById(R.id.tomorrow);
        tomorrow_high = findViewById(R.id.tomorrow_high);
        tomorrow_low = findViewById(R.id.tomorrow_low);
        third_day = findViewById(R.id.third_day);
        third_high = findViewById(R.id.third_high);
        third_low = findViewById(R.id.third_low);
        fourth_day = findViewById(R.id.fourth_day);
        fourth_high = findViewById(R.id.fourth_high);
        fourth_low = findViewById(R.id.fourth_low);
        fifth_day = findViewById(R.id.fifth_day);
        fifth_high = findViewById(R.id.fifth_high);
        fifth_low = findViewById(R.id.fifth_low);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        wind_direction = findViewById(R.id.wind_direction);
        wind_energy = findViewById(R.id.wind_energy);
        air_quality = findViewById(R.id.air_quality);
        pm25 = findViewById(R.id.pm);
        pm10 = findViewById(R.id.pm10);
        aqi = findViewById(R.id.aqi);
        wet = findViewById(R.id.wet);
        today_weather = findViewById(R.id.today_weather);
        tomorrow_weather = findViewById(R.id.tomorrow_weather);
        third_weather = findViewById(R.id.third_weather);
        fourth_weather = findViewById(R.id.fourth_weather);
        fifth_weather = findViewById(R.id.fifth_weather);
        input_city_name = findViewById(R.id.input_city_name);
        progressBar = new ProgressBar(MainActivity.this);
        scrollView = findViewById(R.id.my_scrollView);
        scrollView.setVisibility(View.GONE);
        refreshLayout = findViewById(R.id.fresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetData(String.valueOf(city_name.getText()));
                refreshLayout.setRefreshing(false);
            }
        });
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input_city_name.getText())) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadLoacalData(String.valueOf(input_city_name.getText()));
                    loadNetData(String.valueOf(input_city_name.getText()));
                } else {
                    Toast.makeText(MainActivity.this, "请输入字段以搜索",
                            Toast.LENGTH_SHORT).show();
                }
//                input_city_name.setText("");
            }
        });
    }

    private void loadLoacalData(String city) {
        String pattern = "\\d*";
        Pattern r = Pattern.compile(pattern);
        List<Weather> list = new WeatherDao(MainActivity.this).queryWeather(city);
        if (list.size() != 0) {
            for (Weather weather1 : list) {
                Matcher m = r.matcher(weather1.getDate());
                while (m.find()) {
                    if (TextUtils.equals(m.group(), String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))) {      //从本地加载当天数据
                        city_name.setText(weather1.getCity_name());
                        notice.setText(weather1.getNotice());
                        current_temperature.setText(weather1.getHigh().substring(3));
                        sunrise.setText(weather1.getSunrise());
                        sunset.setText(weather1.getSunset());
                        wind_direction.setText(weather1.getFx());
                        wind_energy.setText(weather1.getFl());
                        aqi.setText(String.valueOf(weather1.getAqi()));
                        scrollView.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }
        }
    }

    private void setTime() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
                "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String time = null;
        calendar = Calendar.getInstance();
        time = (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日," +
                weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        current_date.setText(time);
        today.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        tomorrow.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK)]);
        third_day.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK) + 1]);
        fourth_day.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK) + 2]);
        fifth_day.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK) + 3]);
    }
}
