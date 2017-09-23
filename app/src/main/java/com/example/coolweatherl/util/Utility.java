package com.example.coolweatherl.util;

import android.text.TextUtils;

import com.example.coolweatherl.db.City;
import com.example.coolweatherl.db.County;
import com.example.coolweatherl.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by ${LuoJY} on 2017/9/17.
 * 解析处理数据
 */

public class Utility {
    /*
    * 解析和处理服务器返回的省级数据
    * */
    public static boolean handleProvinceResponse(String response){
        /*TextUtils.isEmpty(response) 判断是否为空，为空返回true */
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces= new JSONArray(response);//把服务器返回的数组传入Json数组中
                for (int i=0;i<allProvinces.length();i++){       //遍历数组
                    /*在数组中获取的元素都是一个对象，把取出的元素装入provinceObject.
                    * 每一个对象都有一个ID,NAME,等数据
                    * */
                    JSONObject provinceObject =allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();    //把获取的数据存入数据库中
                }
                return  true;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return false;
    }

    /*解析处理市级的数据
    * */
    public static boolean handleCityResponse(String response,int provinceId){

        if (!TextUtils.isEmpty(response)){
              try {
                      JSONArray  allCities = new JSONArray(response);
                         for (int i=0;i<allCities.length();i++){
                         JSONObject  cityObject= allCities.getJSONObject(i);
                            City   city= new City();
                            city.setCityName(cityObject.getString("name"));
                            city.setCityCode(cityObject.getInt("id"));
                            city.setProvinceId(provinceId);
                            city.save();
                          }
                          return true;
              }catch (JSONException e){
                     e.printStackTrace();
              }
        }
        return false;

    }
    /*
    * 解析和处理服务器返回的县级城市数据
    * */
    public static boolean handleCountryResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
                try {
                    JSONArray allCountries =new JSONArray(response);
                        for (int i=0;i<allCountries.length();i++){
                            JSONObject countryObject=allCountries.getJSONObject(i);
                            County county=new County();
                            county.setCountryName(countryObject.getString("name"));
                            county.setWeatherId(countryObject.getString("weather_id"));
                            county.setCityId(cityId);
                            county.save();
                        }
                    return true;
                }catch (JSONException e){
                    e.printStackTrace();
                }

        }
        return false;
    }
}
