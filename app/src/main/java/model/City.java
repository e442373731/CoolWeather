package model;

/**
 * Created by zs on 2015/9/12.
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public int getId(){
        return id;
    }

    public String getCityName(){
        return cityName;
    }

    public String getCityCode(){
        return cityCode;
    }

    public int getProvinceId(){
        return provinceId;
    }

    public void setId(int _id){
        id = _id;
    }

    public void setCityName(String name){
        cityName = name;
    }

    public void setCityCode(String code){
        cityCode = code;
    }

    public void setProvinceId(int id){
        provinceId = id;
    }
}
