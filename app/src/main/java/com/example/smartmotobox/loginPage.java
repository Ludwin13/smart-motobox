package com.example.smartmotobox;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class loginPage extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginButton;
    TextView registerPage, forgotPassword;
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
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.email_editText);
        passwordField = findViewById(R.id.password_editText);
        loginButton = (Button) findViewById(R.id.login_btn);
        registerPage = findViewById(R.id.registerPage);
        forgotPassword = findViewById(R.id.forgotPassword);

        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(loginPage.this, forgotPasswordPage.class);
            startActivity(intent);
            finish();
        });

        loginButton.setOnClickListener(view -> performLogin());

        registerPage.setOnClickListener(view -> {
            Intent intent = new Intent(loginPage.this, registerPage.class);
            startActivity(intent);
            finish();
        });


    }

    private void performLogin() {

        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(loginPage.this, "Both Fields Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(loginPage.this, "Email Field Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(loginPage.this, "Password Field Empty", Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if(Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(loginPage.this, "Please verify your email first.", Toast.LENGTH_SHORT).show();
                        }
                        // Sign in success, update UI with the signed-in user's information


                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(loginPage.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });


    }

}
