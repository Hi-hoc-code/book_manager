package com.example.readingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.readingbook.fragment.StaffFragment;
import com.example.readingbook.fragment.ManagerFragment;
import com.example.readingbook.fragment.BookFragment;
import com.example.readingbook.fragment.Customer_Fragment;
import com.example.readingbook.fragment.Home_Fragment;
import com.example.readingbook.fragment.KindBookFragment;
import com.example.readingbook.fragment.PhieuMuon_Fragment;
import com.example.readingbook.fragment.Setting_Fragment;
import com.example.readingbook.fragment.Thong_Ke_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView navigationView;
    BottomNavigationView bottom_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setup DraerToggle
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open,R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        // gọi các id của drawer_navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
//                fragment = new Home_Fragment();
                if (item.getItemId()==R.id.home_fragment){
                    toolbar.setTitle("Trang chủ");
                    fragment = new Home_Fragment();
                }

                if(item.getItemId()==R.id.staff_manager){
                    toolbar.setTitle("Quản lí nhân viên");
                    fragment = new StaffFragment();
                } else if (item.getItemId()==R.id.customer_manager) {
                    toolbar.setTitle("Quản lí khách hàng");
                    fragment = new Customer_Fragment();
                }else if (item.getItemId()==R.id.kind_book_manager) {
                    toolbar.setTitle("Quản lí loại sách");
                    fragment = new KindBookFragment();
                }else if (item.getItemId()==R.id.book_manager) {
                    toolbar.setTitle("Quản lí sách");
                    fragment = new BookFragment();
                }else if (item.getItemId()==R.id.phieu_muon_manager) {
                    toolbar.setTitle("Quản lí phiếu mượn");
                    fragment = new PhieuMuon_Fragment();
                }else if (item.getItemId()==R.id.statistical_manager) {
                    toolbar.setTitle("Quản lí thống kê");
                    fragment = new Thong_Ke_Fragment();
                }else if(item.getItemId()==R.id.setting){
                    toolbar.setTitle("Cài đặt");
                    fragment = new Setting_Fragment();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .addToBackStack(null)
                            .commit();
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Home_Fragment();
                if (item.getItemId()==R.id.home_fragment){
                    toolbar.setTitle("Trang chủ");
                    fragment = new Home_Fragment();
                }else if(item.getItemId()==R.id.quanLi){
                    toolbar.setTitle("Quản lí");
                    fragment= new ManagerFragment();
                }else if(item.getItemId()== R.id.thongKe){
                    toolbar.setTitle("Thống kê");
                    fragment = new Thong_Ke_Fragment();
                }else if(item.getItemId() == R.id.setting_bottom){
                    toolbar.setTitle("Cài đặt");
                    fragment = new Setting_Fragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout,fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        });

    }


    private void addControls() {
        toolbar =  findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);
        bottom_navigation = findViewById(R.id.bottom_navigation);
    }
}