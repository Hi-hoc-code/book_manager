package com.example.readingbook.log_in_out;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readingbook.R;
import com.example.readingbook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText edtEmail, edtPass, edtRePass, edtUserName;
    Button btnRegister;
    FirebaseAuth mAth;
    DatabaseReference userRef;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addControls();
        addEvents();
    }

    private void addEvents() {
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

                                    // Save additional user information to the database
                                    String userId = mAth.getCurrentUser().getUid();
                                    DatabaseReference currentUserRef = userRef.child(userId);

                                    // Create a User object and set its values
                                    User user = new User(username, email, pass);
                                    currentUserRef.setValue(user);

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
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }
}