package com.example.xiaochenbing.weatherforecast.db;

import android.content.Context;

import com.example.xiaochenbing.weatherforecast.enity.Weather;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDao {
    private Dao<Weather, Integer> dao;

    public WeatherDao(Context context) {
        try {
            MyDBHelper dbHelper = MyDBHelper.getHelper(context);
            if (dao == null) {
                dao = dbHelper.getDao(Weather.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    不存在添加数据，存在则更新数据
     */
    public void addWeather(List<Weather> list) {
        for (Weather weather :
                list) {
            try {
                List<Weather> list1 = (dao.queryBuilder().where().eq("city_name", weather.getCity_name())
                        .and().eq("date", weather.getDate()).query());
                if (list1.size() == 0) {
                    dao.create(weather);
                } else {
                    weather.setId(list1.get(0).getId());
                    dao.update(weather);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void deleteWeather() {

    }


    /*
    查询数据
     */
    public List<Weather> queryWeather(String place_name) {
        List<Weather> list = new ArrayList<>();
        try {
            list = dao.queryForEq("city_name", place_name);     //通过地名查询所有数据
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
