package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zs.coolweather.R;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.County;
import model.Province;

/**
 * Created by zs on 2015/9/13.
 */
public class ChooseAreaActivity extends Activity {
    private static final int PROVINCE_LEVEL = 0;
    private static final int CITY_LEVEL = 1;
    private static final int COUNTY_LEVEL = 2;
    private int currentLevel;

    private ProgressDialog dialog;
    private TextView showTitle;
    private ListView showListView;
    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<>();
    private SQLiteDatabase db;

    private List<Province> provinces;
    private List<City> cities;
    private List<County> counties;

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        showTitle = (TextView) findViewById(R.id.title_text);
        showListView = (ListView) findViewById(R.id.show_list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datalist);
        showListView.setAdapter(adapter);
        showListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == CITY_LEVEL) {

                } else if (currentLevel == COUNTY_LEVEL) {

                }
            }
        });
    }

    /**
     * query province and put data into datalist
     */
    private void queryProvinces() {
        
    }
}
