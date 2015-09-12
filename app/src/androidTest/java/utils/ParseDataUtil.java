package utils;

import android.text.TextUtils;

import model.City;
import model.County;
import model.Province;

/**
 * Created by zs on 2015/9/12.
 */
public class ParseDataUtil {
    /**
     * parse province data
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB db, String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length > 0){
                for(String p :allProvinces){
                    Province province = new Province();
                    String[] array = p.split("\\|");
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    db.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * parse city data
     */
    public synchronized static boolean handleCityResponse(CoolWeatherDB db,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allcities = response.split(",");
            if(allcities != null && allcities.length > 0){
                for(String c : allcities){
                    City city = new City();
                    String[] array = c.split("\\|");
                    city.setProvinceId(provinceId);
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    db.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * parse county data
     */
    public synchronized static boolean handleCountyResponse(CoolWeatherDB db, String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] counties = response.split(",");
            if(counties != null && counties.length > 0){
                for(String c : counties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    db.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
