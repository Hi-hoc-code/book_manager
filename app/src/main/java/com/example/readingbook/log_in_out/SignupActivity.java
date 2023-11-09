package com.example.readingbook.log_in_out;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbook.R;
import com.example.readingbook.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {
    EditText edtEmail, edtPass, edtRePass, edtUserName;
    Button btnRegister;
    FirebaseAuth mAth;
    FirebaseFirestore dataBase;
    TextView btnToLoginFromSignUp;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnToLoginFromSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String rePass = edtRePass.getText().toString().trim();
                String username = edtUserName.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(rePass) || TextUtils.isEmpty(username)) {
                    Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(rePass)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user
                mAth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration success
                                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    // Add thông tin vào firebase

                                    String id = UUID.randomUUID().toString();
                                    Admin user = new Admin(id, username, email, pass);
                                    HashMap<String, Object> mapUser = user.convertHashMap();
                                    dataBase.collection("user").document(id).set(mapUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // Navigate to LoginActivity
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign-up fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void addControls() {
        mAth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEmailSignUp);
        edtPass = findViewById(R.id.edtPassSignUp);
        edtRePass = findViewById(R.id.edtRePassSignUp);
        edtUserName = findViewById(R.id.edtTenSignUp);
        btnRegister = findViewById(R.id.btnRegister);
        dataBase = FirebaseFirestore.getInstance();
        btnToLoginFromSignUp = findViewById(R.id.btnToLoginFromSignUp);
    }
}