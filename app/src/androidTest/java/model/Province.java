package model;

/**
 * Created by zs on 2015/9/12.
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public int getId(){
        return id;
    }

    public String getProvinceName(){
        return provinceName;
    }

    public String getProvinceCode(){
        return provinceCode;
    }

    public void setId(int _id){
        id = _id;
    }

    public void setProvinceName(String name){
        provinceName = name;
    }

    public void setProvinceCode(String code){
        provinceCode = code;
    }
}
