package com.example.smartmotobox;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordPage extends AppCompatActivity {

    EditText emailRecovery;
    TextView tvLoginPage;
    Button resetPassBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        mAuth = FirebaseAuth.getInstance();
        resetPassBtn = (Button) findViewById(R.id.forgotPass_btn);
        tvLoginPage = (TextView) findViewById(R.id.loginText);
        emailRecovery = findViewById(R.id.emailRecovery_text);

        tvLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPasswordPage.this, loginPage.class);
                startActivity(intent);
                finish();
            }
        });
        
        emailRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    
        



    }

    private void changePassword() {
        Intent intent = new Intent(forgotPasswordPage.this, loginPage.class);

        String email = emailRecovery.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(forgotPasswordPage.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(forgotPasswordPage.this, "Failure to send email! ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }
}