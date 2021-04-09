package com.example.smartcity_test4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartcity_test4.guide.GuideActivity;
import com.example.smartcity_test4.ui.XinWen.XinWenFragment;
import com.example.smartcity_test4.ui.dashboard.DashboardFragment;
import com.example.smartcity_test4.ui.home.HomeFragment;
import com.example.smartcity_test4.ui.notifications.NotificationsFragment;
import com.example.smartcity_test4.ui.personal.PersonalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment = new HomeFragment();
    private XinWenFragment xinWenFragment = new XinWenFragment();
    private DashboardFragment dashboardFragment = new DashboardFragment();
    private NotificationsFragment notificationsFragment = new NotificationsFragment();
    private PersonalFragment personalFragment = new PersonalFragment();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        replace(homeFragment);
        navView.setSelectedItemId(R.id.navigation_home);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        replace(homeFragment);
                        break;
                    case R.id.navigation_personal:
                        replace(personalFragment);
                        break;
                    case R.id.navigation_dashboard:
                        replace(dashboardFragment);
                        break;
                    case R.id.navigation_xinwen:
                        replace(xinWenFragment);
                        break;
                    case R.id.navigation_notifications:
                        replace(notificationsFragment);
                        break;
                }
                return true;
            }
        });

        if (sharedPreferences.getBoolean("login?",true)){
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
        }

    }

    public void replace(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }

}