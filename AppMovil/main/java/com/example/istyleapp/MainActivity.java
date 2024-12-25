// MainActivity.java
package com.example.istyleapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton buttonAvatar;
    ImageButton buttonProducts;
    ImageButton buttonCart;
    RecyclerAdapter adapter;
    RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserLoggedIn()) {
            redirectToSessionScreen();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initComponents();
        setupListeners();
        obtenerProductosConDescuento();
    }

    private void initComponents() {
        buttonAvatar = findViewById(R.id.imageButton3);
        buttonProducts = findViewById(R.id.imageButtonProd);
        buttonCart = findViewById(R.id.imageButtonCart);
        rvProducts = findViewById(R.id.rvProducts);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupListeners() {
        buttonAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        buttonProducts.setOnClickListener(view -> {
            Intent intentWishlist = new Intent(MainActivity.this, ProductsActivity.class);
            startActivity(intentWishlist);
        });

        buttonCart.setOnClickListener(view -> {
            Intent intentCart = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intentCart);
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return isLoggedIn && user != null && user.isEmailVerified();
    }

    private void redirectToSessionScreen() {
        Intent intent = new Intent(MainActivity.this, SessionActivity.class);
        startActivity(intent);
        finish();
    }

    private void obtenerProductosConDescuento() {
        // Este método realiza la llamada al servicio y actualiza el ListView
        new Thread(() -> {
            String xmlResponse = ApiClient.obtenerProductosDescuentoXML();
            if (xmlResponse != null) {
                ArrayList<Product> productsDescuento = XMLParser.parseProductosXML(xmlResponse);

                runOnUiThread(() -> {
                    adapter = new RecyclerAdapter(MainActivity.this, productsDescuento);
                    rvProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    rvProducts.setAdapter(adapter);

                });
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

}