package com.example.istyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    ImageButton buttonAtras;
    RecyclerView rvCartItems;
    CartAdapter adapter;
    Button buttonPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        buttonAtras = findViewById(R.id.imageButtonAtras);
        buttonPago = findViewById(R.id.buttonPago);

        rvCartItems = findViewById(R.id.rvCartItems);
        adapter = new CartAdapter(this, Cart.getInstance().getCartItems());

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonAtras.setOnClickListener(view -> {

            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
        });

        buttonPago.setOnClickListener(view -> {

            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);
        });
    }
}