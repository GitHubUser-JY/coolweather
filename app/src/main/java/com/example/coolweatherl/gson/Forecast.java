package com.example.coolweatherl.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${LuoJY} on 2017/9/24.
 */

public class Forecast {

    public String date;

    @SerializedName("cond")
    public More more;

    @SerializedName("tmp")
    public Temperature temperature;

    public class More{
        @SerializedName("txt_d")
        public String info;

    }

    public class Temperature{
        @SerializedName("max")
        public String maxTemperature;

        @SerializedName("min")
        public String minTemperature;
    }
}
