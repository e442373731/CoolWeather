package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zs.coolweather.R;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.County;
import model.Province;
import utils.CoolWeatherDB;
import utils.HttpCallbackListener;
import utils.HttpUtil;
import utils.ParseDataUtil;

/**
 * Created by zs on 2015/9/13.
 */
public class ChooseAreaActivity extends Activity {
    private static final int PROVINCE_LEVEL = 0;
    private static final int CITY_LEVEL = 1;
    private static final int COUNTY_LEVEL = 2;
    private int currentLevel;
    private Province selectedProvince;
    private City selectedCity;

    private ProgressDialog dialog;
    private TextView showTitle;
    private ListView showListView;
    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<>();
    private CoolWeatherDB db;

    private List<Province> provinces;
    private List<City> cities;
    private List<County> counties;

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        db = db.getInstance(this);
        showTitle = (TextView) findViewById(R.id.title_text);
        showListView = (ListView) findViewById(R.id.show_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datalist);
        showListView.setAdapter(adapter);
        showListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == PROVINCE_LEVEL) {
                    selectedProvince = provinces.get(position);
                    queryCities();
                } else if (currentLevel == CITY_LEVEL) {
                    selectedCity = cities.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces();
    }

    /**
     * query province and redraw list
     */
    private void queryProvinces() {
        provinces = db.loadProvinces();
        if (provinces.size() > 0) {
            datalist.clear();
            for (Province province : provinces) {
                datalist.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            showListView.setSelection(0);
            showTitle.setText("China");
            currentLevel = PROVINCE_LEVEL;
        } else {
            queryFromServer(null, "province");
        }
    }

    /**
     * query city and redraw list
     */
    private void queryCities() {
        cities = db.loadCities(selectedProvince.getId());
        if (cities.size() > 0) {
            datalist.clear();
            for (City city : cities) {
                datalist.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            showListView.setSelection(0);
            showTitle.setText(selectedProvince.getProvinceName());
            currentLevel = CITY_LEVEL;
        } else {
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    /**
     * query county and redraw list
     */
    private void queryCounties() {
        counties = db.loadCounties(selectedCity.getId());
        if (counties.size() > 0) {
            datalist.clear();
            for (County county : counties) {
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            showListView.setSelection(0);
            showTitle.setText(selectedCity.getCityName());
            currentLevel = COUNTY_LEVEL;
        } else {
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }

    /**
     * query form server
     */
    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        //query from web through httpUtil
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = ParseDataUtil.handleProvincesResponse(db, response);
                } else if ("city".equals(type)) {
                    result = ParseDataUtil.handleCityResponse(db, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = ParseDataUtil.handleCountyResponse(db, response, selectedCity.getId());
                }

                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                closeProgressDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChooseAreaActivity.this, "load filed....", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * init progress dialog
     */
    private void showProgressDialog() {
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("loading.....");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    /**
     * close progress dialog
     */
    private void closeProgressDialog() {
        if(dialog != null){
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed(){
        if(currentLevel == CITY_LEVEL){
            queryProvinces();
        }else if(currentLevel == COUNTY_LEVEL){
            queryCities();
        }
    }
}
