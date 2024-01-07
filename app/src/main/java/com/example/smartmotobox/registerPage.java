package com.example.smartmotobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class registerPage extends AppCompatActivity {

    EditText getEmail, getPassword;
    Button register_btn;
    TextView clickToLogin;
    Boolean isConnectedto;

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

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
            isConnected();
            if (isConnectedto) {
                PerformAuth();
            } else {
                Toast.makeText(registerPage.this, "Not connected to the Internet", Toast.LENGTH_SHORT).show();
            }

        });

        clickToLogin.setOnClickListener(view -> {
                Intent intent = new Intent(registerPage.this, loginPage.class);
                startActivity(intent);
                finish();
        });

    }

    private void isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        isConnectedto = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){

                                Toast.makeText(registerPage.this, "User registered successfully. Please verify your email id.",
                                        Toast.LENGTH_SHORT).show();

                                getEmail.setText("");
                                getPassword.setText("");


                            }
                        });


                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(registerPage.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

}