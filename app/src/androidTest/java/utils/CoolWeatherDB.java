package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import db.CoolWeatherOpenHelper;
import model.City;
import model.Province;

/**
 * Created by zs on 2015/9/12.
 */
public class CoolWeatherDB {
    /**
     * name of my database
     */
    public static final String DB_NAME = "cool_weather";

    /**
     * version of database
     */
    public static final int VERSION = 1;
    private SQLiteDatabase database;
    private static CoolWeatherDB WEATHER_DB;

    /**
     * private constructor
     */
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper helper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        database = helper.getWritableDatabase();
    }

    public synchronized CoolWeatherDB getInstance(Context context) {
        if (WEATHER_DB == null) {
            WEATHER_DB = new CoolWeatherDB(context);
        }
        return WEATHER_DB;
    }

    /**
     * save province
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            database.insert("Province", null, values);
        }
    }

    /**
     * save city
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            database.insert("City", null, values);
        }
    }
}
