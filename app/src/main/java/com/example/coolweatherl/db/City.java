package com.example.coolweatherl.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ${LuoJY} on 2017/9/16.
 */

public class City extends DataSupport {
    private int  id;
    private String cityName;        //记录市的名字
    private  int cityCode;          //记录市的代号
    public  int provinceId;         //记录当前市所属的省

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
