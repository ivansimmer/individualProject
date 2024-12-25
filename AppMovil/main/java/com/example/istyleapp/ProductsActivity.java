// ProductsActivity.java
package com.example.istyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    ImageButton buttonAtras;
    RecyclerAdapter adapter;
    RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products);

        buttonAtras = findViewById(R.id.imageButtonAtras);
        rvProducts = findViewById(R.id.rvProducts);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonAtras.setOnClickListener(view -> {

            Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        obtenerProductos();
    }

    private void obtenerProductos() {
        // Este método realiza la llamada al servicio y actualiza el ListView
        new Thread(() -> {
            String xmlResponse = ApiClient.obtenerProductosXML();
            if (xmlResponse != null) {
                ArrayList<Product> products = XMLParser.parseProductosXML(xmlResponse);

                runOnUiThread(() -> {
                    adapter = new RecyclerAdapter(ProductsActivity.this, products);
                    rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
                    rvProducts.setAdapter(adapter);

                });
            } else {
                runOnUiThread(() -> Toast.makeText(ProductsActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}