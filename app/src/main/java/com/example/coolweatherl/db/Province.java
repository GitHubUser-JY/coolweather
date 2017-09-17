package com.example.coolweatherl.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ${LuoJY} on 2017/9/16.
 * 数据库表的结构
 *
 * LitePal中的每个实类都要继承DataSupport
 */

public class Province extends DataSupport {
    private int id;                 //键值
    private String provinceName;  //记录省的名字
    private int provinceCode;       //记录省的代号
    public int getId(){
        return id;

    }
    public void setId(int id){
        this.id=id;
    }
    public String getProvinceName(){
        return provinceName;
    }
    public void setProvinceName(String provinceName){
        this.provinceName=provinceName;
    }
    public int getProvinceCode(){
        return provinceCode;
    }
    public void setProvinceCode(int provinceCode){
        this.provinceCode=provinceCode;
    }
}
