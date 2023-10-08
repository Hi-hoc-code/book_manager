package com.example.readingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.readingbook.fragment.Book_Fragment;
import com.example.readingbook.fragment.Customer_Fragment;
import com.example.readingbook.fragment.Kind_of_book_Fragment;
import com.example.readingbook.fragment.PhieuMuon_Fragment;
import com.example.readingbook.fragment.Staff_Fragment;
import com.example.readingbook.fragment.Statiscal_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView navigationView;
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
                Fragment fragment;
                if(item.getItemId()==R.id.staff_manager){
                    toolbar.setTitle("Staff manager");
                    fragment = new Staff_Fragment();
                } else if (item.getItemId()==R.id.customer_manager) {
                    toolbar.setTitle("Customer manager");
                    fragment = new Customer_Fragment();
                }else if (item.getItemId()==R.id.kind_book_manager) {
                    toolbar.setTitle("Kind of book manager");
                    fragment = new Kind_of_book_Fragment();
                }else if (item.getItemId()==R.id.book_manager) {
                    toolbar.setTitle("Book manager");
                    fragment = new Book_Fragment();
                }else if (item.getItemId()==R.id.phieu_muon_manager) {
                    toolbar.setTitle("Loan slip manager");
                    fragment = new PhieuMuon_Fragment();
                }else if (item.getItemId()==R.id.statistical_manager) {
                    toolbar.setTitle("Statistical manager");
                    fragment = new Statiscal_Fragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
                return true;
            }
        });
    }

    private void addControls() {
        toolbar =  findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);
    }
}