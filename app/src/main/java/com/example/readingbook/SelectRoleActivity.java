package com.example.readingbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.readingbook.log_in_out.SignupActivity;

public class SelectRoleActivity extends AppCompatActivity {
    Button btnAdmin, btnStaff, btnUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRoleActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        btnAdmin = findViewById(R.id.btnManager);
        btnStaff = findViewById(R.id.btnStaff);
        btnUser = findViewById(R.id.btnUser);
    }
}