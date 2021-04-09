package com.example.smartcity_test4.ui.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.smartcity_test4.R;
import com.example.smartcity_test4.ui.home.adapter.ItemAdapter;
import com.example.smartcity_test4.ui.home.adapter.LabelAdapter;
import com.example.smartcity_test4.ui.home.adapter.MyAdapter;
import com.example.smartcity_test4.ui.home.adapter.ServiceAdapter;
import com.example.smartcity_test4.ui.home.pojo.Item_service;
import com.example.smartcity_test4.util.KenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllServiceActivity extends AppCompatActivity {

    @BindView(R.id.recyclerService_all)
    RecyclerView recyclerService_all;
    private List<Item_service >serviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service);
        ButterKnife.bind(this);
        getService();
    }

    public void getService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/service/service/list");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = object.getInt("id");
                        String serviceName = object.getString("serviceName");
                        String url = "http://124.93.196.45:10002" + object.getString("imgUrl");
                        serviceList.add(new Item_service(id, serviceName, url));
                    }
                    handler.sendEmptyMessage(3);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 3:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
                    ServiceAdapter serviceAdapter = new ServiceAdapter(serviceList, R.layout.item_service_all);
                    recyclerService_all.setLayoutManager(staggeredGridLayoutManager);
                    recyclerService_all.setAdapter(serviceAdapter);
                    break;

            }
        }
    };




}