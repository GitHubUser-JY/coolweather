package com.example.coolweatherl.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweatherl.R;
import com.example.coolweatherl.db.City;
import com.example.coolweatherl.db.County;
import com.example.coolweatherl.db.Province;
import com.example.coolweatherl.util.HttpUtil;
import com.example.coolweatherl.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ${LuoJY} on 2017/9/17.
 */

public class AreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE=0;
    public  static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList =new ArrayList<>();
    /*访问的地址*/
    private String GEN="http://guolin.tech/api/china/";
    /*省列表*/
    private List<Province> provinceList;
    /*市列表*/
    private List<City> cityList;
    /*县列表*/
    private List<County> countyList;
    /*选中的省份*/
    private  Province selectedProvince;
    /*选中的城市*/
    private  City  selectedCity;
    /*选中的级别*/
    private int currentLevel;


    /**功能：初始化View
     * onCreateView: Fragment的方法，重写它通过LayouInflater的inflate()
     * 将fragment加载进来
     * 获取控件实例、初始化ArrayAdapter的设置，成为ListView的适配器*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText=view.findViewById(R.id.title_text);
        backButton=view.findViewById(R.id.back_button);
        listView=view.findViewById(R.id.list_view);
        /**
         * 适配器：第一个参数是获取上下文即当前的Activity，第二参数是官方的TextView布局,表示的是我们数组中每一数据对应一个View,
         * 就是将每一条数据显示在View上。（可自己重写这个View，如果是Text.xml，调用的是后就是 R.layout.Text）.
         * 第三个参数是我们想要显示的数据，listView会根据这三个参数，遍历dataList里面的每一条数据，
         * 读出一条，显示到第二个参数对应的布局中，这样就形成了我们看到的listView*/
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }
/**
 * 注册监听事件*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(position);
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }
    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器查询
     */
    private void queryProvinces(){
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList= DataSupport.findAll(Province.class);//在数据库中查找数据，并把它们装入provinceList集合中
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            /*通知所附的观察者底层数据已被更改，
            并且反映数据集的任何视图都将刷新自身。*/
            adapter.notifyDataSetChanged();
            /*设置当前选择的选项，将第position个Item放在Listview的第一项
            * */
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }else {
            String address=GEN;
            queryFromSever(address,"province");
        }
    }
    /**
     *查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        /*litePal的方法：
        * where（）：里面两个都是字符串类型。“provinceid=”第一参数为条件参数，第二个参数
        * */
        cityList=DataSupport.where("provinceid=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city : cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else {
            int provinceCode= selectedProvince.getProvinceCode();
            String address=GEN+provinceCode;
            queryFromSever(address,"city");
        }
    }
    /*
    * 查询选中市内所有的县，先在数据库中查找数据，如果没有就去服务器查询*/

    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("cityid=?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size()>0){
            dataList.clear();
            for (County county:countyList){
                dataList.add(county.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            int cityCode =selectedCity.getCityCode();
            String address=GEN+provinceCode+"/"+cityCode;
            queryFromSever(address,"county");
        }
    }
    /*
    *根据传入的地址和类型服务器上查询省市县数据
    */
    private void queryFromSever(String address,final String  type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {  //此方法访问服务器后，回调数据会在onResponse中返回
            @Override
        public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                boolean result =false;
                if ("province".equals(type)){
                    result= Utility.handleProvinceResponse(responseText);
                }else if ("city".equals(type)){
                    result= Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handleCountryResponse(responseText,selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
        }
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"Download  Failure",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Downloading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if (progressDialog !=null){
            progressDialog.dismiss();
        }
    }
}
