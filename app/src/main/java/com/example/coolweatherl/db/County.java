package com.example.coolweatherl.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ${LuoJY} on 2017/9/16.
 */

public class County extends DataSupport{
    private int  id;
    private String countryName;
    private String  weatherId ;
    public  int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
