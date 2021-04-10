package com.example.smartcity_test4.ui.personal;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcity_test4.MainActivity;
import com.example.smartcity_test4.R;
import com.example.smartcity_test4.ui.personal.acvitity.ChangeActivity;
import com.example.smartcity_test4.ui.personal.acvitity.FeedbackActivity;
import com.example.smartcity_test4.ui.personal.acvitity.LoginActivity;
import com.example.smartcity_test4.ui.personal.acvitity.OrderActivity;
import com.example.smartcity_test4.ui.personal.acvitity.UserInfoActivity;
import com.example.smartcity_test4.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonalFragment extends Fragment {

    private PersonalViewModel mViewModel;
    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Unbinder unbinder;

    @BindView(R.id.img_personal)
    ImageView imageView;
    @BindView(R.id.nike_personal)
    TextView nike;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.personal_fragment, container, false);
        unbinder = ButterKnife.bind(this,root);
        sharedPreferences = getActivity().getSharedPreferences("data",0);
        editor = getActivity().getSharedPreferences("data",0).edit();
        return root;
    }

    @OnClick(R.id.logout)
    public void logout(View view){
        editor.clear().commit();
        editor.putBoolean("login?",false);
        editor.commit();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }
    @OnClick({R.id.order,R.id.feedback,R.id.userinfo,R.id.change})
    public void other(View view){
        switch (view.getId()){
            case R.id.order:
                Intent intent = new Intent(getContext(), OrderActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.feedback:
                Intent intent1 = new Intent(getContext(), FeedbackActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.userinfo:
                Intent intent2 = new Intent(getContext(), UserInfoActivity.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.change:
                Intent intent3 = new Intent(getContext(), ChangeActivity.class);
                getActivity().startActivity(intent3);
                break;
        }
    }


    public void login(String username,String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username",username);
                    jsonObject.put("password",password);
                    String json = KenUtil.Post("http://124.93.196.45:10002/login","",jsonObject.toString());
                    JSONObject jsonObject1 = new JSONObject(json);
                    int code = jsonObject1.getInt("code");
                    if (code == 200){
                        String token = jsonObject1.getString("token");
                        editor.putString("token",token);
                        editor.commit();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"登陆失败",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                getActivity().startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void getUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = sharedPreferences.getString("token","k");
                try {
                    String json = KenUtil.Get_T("http://124.93.196.45:10002/getInfo",token);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject object = jsonObject.getJSONObject("user");
                    String nikeName = object.getString("nickName");
                    String url = "http://124.93.196.45:10002"+object.getString("avatar");
                    int userId = object.getInt("userId");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editor.putInt("userId",userId);
                            Glide.with(getContext()).load(url).into(imageView);
                            nike.setText(nikeName);
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonalViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();
        String password = sharedPreferences.getString("password","k");
        String username = sharedPreferences.getString("username","k");
        if (username.equals("k")||password.equals("k")){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            getActivity().startActivity(intent);
        }else {
            login(username,password);
            getUser();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}