package com.example.smartcity_test4.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.smartcity_test4.R;
import com.example.smartcity_test4.ui.home.adapter.ItemAdapter;
import com.example.smartcity_test4.ui.home.adapter.LabelAdapter;
import com.example.smartcity_test4.ui.home.adapter.MyAdapter;
import com.example.smartcity_test4.ui.home.adapter.ServiceAdapter;
import com.example.smartcity_test4.ui.home.pojo.Img;
import com.example.smartcity_test4.ui.home.pojo.Item;
import com.example.smartcity_test4.ui.home.pojo.ItemData;
import com.example.smartcity_test4.ui.home.pojo.Item_service;
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
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Unbinder unbinder;

    private ViewPager viewPager_home;
    private List<Img> imgList = new ArrayList<>();


    @BindView(R.id.listXinwen_home)
    ListView listXinwen_home;
    @BindView(R.id.recyclerLabel_home)
    RecyclerView recyclerLabel_home;
    private List<Item> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;

    @BindView(R.id.recyclerService_home)
    RecyclerView recyclerService_home;
    private List<Item_service> serviceList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        viewPager_home = root.findViewById(R.id.viewPager_home);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLunbo();
        getService();
        getItem();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (imgList.size() > 0) {
            handler.sendEmptyMessage(1);
        }
        if (serviceList.size() > 0) {
            handler.sendEmptyMessage(3);
            handler.sendEmptyMessage(4);
        }
    }

    public void getItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/system/dict/data/type/press_category");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int dictCode = object.getInt("dictCode");
                        String dictLabel = object.getString("dictLabel");
                        List<ItemData> dataList = new ArrayList<>();

                        String url = "http://124.93.196.45:10002/press/press/list?pageNum=1&pageSize=10&pressCategory=" + dictCode;
                        String json1 = KenUtil.Get(url);
                        JSONObject jsonObject1 = new JSONObject(json1);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("rows");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject object1 = jsonArray1.getJSONObject(j);
                            String title = object1.getString("title");
                            String content = object1.getString("content");
                            String imgUrl = "http://124.93.196.45:10002" + object1.getString("imgUrl");
                            int viewsNumber = object1.getInt("viewsNumber");
                            String createTime = object1.getString("createTime");
                            dataList.add(new ItemData(title, content, imgUrl, viewsNumber,createTime));
                        }
                        itemList.add(new Item(dictCode, dictLabel, dataList));
                    }
                    handler.sendEmptyMessage(4);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
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
                    serviceList.add(new Item_service(0, "更多服务", "R.drawable.ic_launcher_background"));
                    handler.sendEmptyMessage(3);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getLunbo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/userinfo/rotation/list?pageNum=1&pageSize=10&type=45");
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
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(2);
                        }
                    }, 0, 2000);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    /**
     * 1:轮播加载完毕
     * 2：轮播定时器
     * 3:service加载完毕
     * 4:新闻加载完毕
     * 5:点击label
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    MyAdapter myAdapter = new MyAdapter(imgList, getContext(), R.layout.item_lunbo);
                    viewPager_home.setAdapter(myAdapter);
                    break;
                case 2:
                    int i = viewPager_home.getCurrentItem();
                    viewPager_home.setCurrentItem(i + 1);
                    break;
                case 3:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
                    ServiceAdapter serviceAdapter = new ServiceAdapter(serviceList, R.layout.item_service);
                    recyclerService_home.setLayoutManager(staggeredGridLayoutManager);
                    recyclerService_home.setAdapter(serviceAdapter);
                    break;
                case 4:
                    Log.i("Ken", "handleMessage: " + itemList.size());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    LabelAdapter labelAdapter = new LabelAdapter(itemList,R.layout.item_label,handler);
                    recyclerLabel_home.setAdapter(labelAdapter);
                    recyclerLabel_home.setLayoutManager(linearLayoutManager);

                    itemAdapter = new ItemAdapter(getContext(),R.layout.item_itemdata,itemList.get(0).getDataList());
                    listXinwen_home.setAdapter(itemAdapter);
                    break;
                case 5:
                    int position = (int)msg.obj;
                    itemAdapter = new ItemAdapter(getContext(),R.layout.item_itemdata,itemList.get(position).getDataList());
                    listXinwen_home.setAdapter(itemAdapter);
                    break;
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}