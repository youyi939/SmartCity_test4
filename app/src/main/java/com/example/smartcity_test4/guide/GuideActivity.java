package com.example.smartcity_test4.guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.smartcity_test4.MainActivity;
import com.example.smartcity_test4.R;
import com.example.smartcity_test4.ui.home.adapter.MyAdapter;
import com.example.smartcity_test4.ui.home.pojo.Img;
import com.example.smartcity_test4.util.KenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {


    @BindView(R.id.viewpager_guide)
    ViewPager viewPager;
    private List<Img> imgList = new ArrayList<>();

    @BindView(R.id.linear_guide)
    LinearLayout linearLayout;

    private SharedPreferences.Editor editor;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        editor = getSharedPreferences("data",0).edit();


        button = new Button(this);
        button.setText("进入首页");
        button.setWidth(10000);
        button.setBackgroundResource(R.color.design_default_color_secondary);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("login?",false);
                editor.commit();
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        getLunbo();

    }

    public void getLunbo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/userinfo/rotation/lists?pageNum=1&pageSize=10&type=47");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String imgUrl = "http://124.93.196.45:10002" + object.getString("imgUrl");
                        String type = object.getString("type");
                        int id = object.getInt("id");
                        String sort = object.getString("sort");
                        imgList.add(new Img(id, imgUrl, type, sort));
                    }
                    handler.sendEmptyMessage(1);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    GuideAdapter myAdapter = new GuideAdapter(imgList,getApplicationContext(), R.layout.item_lunbo,handler);
                    viewPager.setAdapter(myAdapter);
                    break;
                case 2:
                    linearLayout.addView(button);
                    break;
            }
        }
    };


}

