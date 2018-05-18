package com.example.xiaochenbing.weatherforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.xiaochenbing.weatherforecast.enity.Weather;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class MyDBHelper extends OrmLiteSqliteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ormlite_test.db";
    private static MyDBHelper instance;
    private Dao dao = null;


    private MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Weather.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Weather.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized MyDBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (MyDBHelper.class) {
                if (instance == null)
                    instance = new MyDBHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao<Weather,Integer> getDao() throws java.sql.SQLException {

        if (dao == null) {
            dao = super.getDao(Weather.class);
        }
        return dao;
    }
    @Override
    public void close()
    {
        super.close();
        dao = null;
    }
}
