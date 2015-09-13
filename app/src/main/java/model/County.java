package model;

/**
 * Created by zs on 2015/9/12.
 */
public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;

    public int getId(){
        return id;
    }

    public String getCountyName(){
        return countyName;
    }

    public String getCountyCode(){
        return countyCode;
    }

    public int getCityId(){
        return cityId;
    }

    public void setId(int _id){
        id = _id;
    }

    public void setCountyName(String name){
        countyName = name;
    }

    public void setCountyCode(String code){
        countyCode = code;
    }

    public void setCityId(int id){
        cityId = id;
    }
}
