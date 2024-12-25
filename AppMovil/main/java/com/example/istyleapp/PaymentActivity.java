package com.example.istyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    RecyclerView rvTicket;
    TextView tvTotalPrice;
    TicketAdapter adapter;
    ArrayList<Product> cartItems;
    ImageButton buttonAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvTicket = findViewById(R.id.rvTicket);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        buttonAtras = findViewById(R.id.imageButtonAtras);

        cartItems = Cart.getInstance().getCartItems();

        adapter = new TicketAdapter(this, cartItems);
        rvTicket.setLayoutManager(new LinearLayoutManager(this));
        rvTicket.setAdapter(adapter);

        double total = 0.0;
        for (Product product : cartItems) {
            total += product.getPrecio() * product.getCantidad();
        }

        tvTotalPrice.setText(String.format("Total price: €%.2f", total));

        buttonAtras.setOnClickListener(view -> {

            Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
}