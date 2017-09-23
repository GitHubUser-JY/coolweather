package com.example.coolweatherl.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by ${LuoJY} on 2017/9/16.
 *
 * 功能: 和服务器交互
 *
 */

public class HttpUtil {
/*
* address ：出入请求地址，
* callback: 回调处理服务器响应
* */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){

        OkHttpClient client =new OkHttpClient();
        /*创建请求对象，把地址装入后创建*/
        Request request=new Request.Builder().url(address).build();
        /*用okhttpclient的newCall（）创建call对象，并调用call的enqueue方法发送请求
        * 并获取服务器返回的数据。
        * */
        client.newCall(request).enqueue(callback);
    }
}
