package com.example.smartmotobox;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerPage extends AppCompatActivity {

    EditText getEmail, getPassword;
    Button register_btn;
    TextView clickToLogin;

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();
        getEmail = findViewById(R.id.emailRegister_editText);
        getPassword = findViewById(R.id.passwordRegister_editText);
        register_btn = (Button) findViewById(R.id.register_btn);
        clickToLogin = findViewById(R.id.loginText);


        register_btn.setOnClickListener(view -> {
            PerformAuth();


        });

        clickToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(registerPage.this, loginPage.class);
            startActivity(intent);
            finish();
        });

    }

    private void PerformAuth() {
        String email = getEmail.getText().toString().trim();
        String password = getPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(registerPage.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(registerPage.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information




                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(registerPage.this, "User registered successfully. Please verify your email id.",
                                                Toast.LENGTH_SHORT).show();

                                        getEmail.setText("");
                                        getPassword.setText("");


                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(registerPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}