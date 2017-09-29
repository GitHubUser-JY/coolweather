package com.example.coolweatherl.gson;

import android.test.MoreAsserts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${LuoJY} on 2017/9/24.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

   @SerializedName("cond")
   public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
