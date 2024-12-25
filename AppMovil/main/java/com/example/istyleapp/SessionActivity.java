// SessionActivity.java
package com.example.istyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SessionActivity extends AppCompatActivity {

    Button buttonRegister;
    Button buttonLogin;
    ImageButton buttonCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_session);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCerrar = findViewById(R.id.imageButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonCerrar.setOnClickListener(view -> {
            Intent intent = new Intent(SessionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        buttonRegister.setOnClickListener(view -> {
            Intent intent = new Intent(SessionActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> {
            Log.d("SessionActivity", "Login button clicked");
            Intent intent = new Intent(SessionActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
}