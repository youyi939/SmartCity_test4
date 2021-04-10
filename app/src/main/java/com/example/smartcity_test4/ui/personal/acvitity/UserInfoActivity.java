package com.example.smartcity_test4.ui.personal.acvitity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcity_test4.R;
import com.example.smartcity_test4.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.img_personal)
    ImageView imageView;
    @BindView(R.id.nike_personal)
    EditText nike;
    @BindView(R.id.idCard)
    TextView idCard;
    @BindView(R.id.mail)
    RadioButton mail;
    @BindView(R.id.fmail)
    RadioButton fmail;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.number)
    EditText number;



    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();
        getUser();
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
                    String email1 = object.getString("email");
                    String phonenumber = object.getString("phonenumber");
                    String idcard = object.getString("idCard");
                    int sex = object.getInt("sex");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(UserInfoActivity.this).load(url).into(imageView);
                            nike.setText(nikeName);
                            email.setText(email1);
                            number.setText(phonenumber);
                            int length = idcard.length();
                            String msg1 = idcard.substring(0,2);
                            String msg2 = idcard.substring(length-4,length);
                            idCard.setText(msg1+"***********"+msg2);
                            if (sex ==1){
                                mail.setChecked(true);
                                fmail.setChecked(false);
                            }else {
                                mail.setChecked(false);
                                fmail.setChecked(true);
                            }
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick(R.id.change)
    public void change(){
        if (TextUtils.isEmpty(nike.getText()) || TextUtils.isEmpty(email.getText())||TextUtils.isEmpty(number.getText())){
            Toast.makeText(UserInfoActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String nike1 = nike.getText().toString();
                        String phoneNumer = number.getText().toString();
                        String email1 = email.getText().toString();
                        int userId = sharedPreferences.getInt("userId",1);
                        int sex = 1;
                        if (fmail.isChecked()){
                            sex = 0;
                        }
                        String token = sharedPreferences.getString("token","k");
                        String url = "http://124.93.196.45:10002/system/user/updata?userId="+userId+"&nickName="+nike1+"&email="+email1+"&phonenumber="+phoneNumer+"&sex="+sex;
                        String json = KenUtil.Post(url,token,"");
                        JSONObject jsonObject = new JSONObject(json);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

}