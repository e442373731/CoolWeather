package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import db.CoolWeatherOpenHelper;
import model.City;
import model.County;
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

    /**
     * save county
     */
    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            database.insert("County",null,values);
        }
    }

    /**
     * read province from database
     */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<>();
        Cursor cursor = database.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * read city from database
     */
    public List<City> loadCities(){
        List<City> list = new ArrayList<>();
        Cursor cursor = database.query("City",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * read county from database
     */
    public List<County> loadCounties(){
        List<County> list = new ArrayList<>();
        Cursor cursor = database.query("County",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
