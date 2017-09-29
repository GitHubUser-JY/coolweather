package com.example.coolweatherl.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${LuoJY} on 2017/9/24.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String cityId;


    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }

}
