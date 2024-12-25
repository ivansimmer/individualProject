package com.example.istyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProdDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prod_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvProductName = findViewById(R.id.tvProductName);
        TextView tvProductCategory = findViewById(R.id.tvProductCategory);
        TextView tvProductPrice = findViewById(R.id.tvProductPrice);
        ImageView imgProductImage = findViewById(R.id.imgProductImage);
        ImageButton buttonAtras = findViewById(R.id.imageButtonBack);
        Button buttonAddCart = findViewById(R.id.buttonCart);

        // Obtener datos del Intent
        String name = getIntent().getStringExtra("PRODUCT_NAME");
        String category = getIntent().getStringExtra("PRODUCT_CATEGORY");
        double price = getIntent().getDoubleExtra("PRODUCT_PRICE", 0.0);
        String imagePath = getIntent().getStringExtra("PRODUCT_IMAGE");

        // Asignar los datos a las vistas
        tvProductName.setText(name);
        tvProductCategory.setText(category);
        tvProductPrice.setText(String.format("€%.2f", price));

        // Cargar la imagen desde la URL
        Glide.with(this)
                .load("http://10.0.2.2/proyecto_ism/imgProducts/" + imagePath + ".png")
                .placeholder(R.drawable.istyle) // Imagen por defecto mientras carga
                .error(R.drawable.istyle) // Imagen por defecto en caso de error
                .into(imgProductImage);

        // Nuevo RecyclerView para las reseñas
        RecyclerView rvRatings = findViewById(R.id.rvRatings);
        rvRatings.setLayoutManager(new LinearLayoutManager(this));

        // Llama a la API para obtener las reseñas del producto
        String producto = getIntent().getStringExtra("PRODUCT_NAME"); // Nombre del producto para filtrar las reseñas
        new Thread(() -> {
            try {
                // Llamada a la API para obtener las valoraciones
                String xmlResenas = ApiClient.getRetrofitInstance()
                        .create(ApiService.class)
                        .getValoraciones()
                        .execute()
                        .body()
                        .string();

                // Analiza el XML para extraer las reseñas de este producto
                ArrayList<Review> reviews = XMLParser.parseReviewsXML(xmlResenas, producto);

                // Actualiza el RecyclerView en el hilo principal
                runOnUiThread(() -> {
                    ReviewAdapter adapter = new ReviewAdapter(reviews);
                    rvRatings.setAdapter(adapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        buttonAtras.setOnClickListener(view -> {

            Intent intent = new Intent(ProdDetailActivity.this, ProductsActivity.class);
            startActivity(intent);
        });

        buttonAddCart.setOnClickListener(view -> {
            Product product = new Product(name, price, category, imagePath);

            // Verificar si el producto ya está en el carrito
            if (Cart.getInstance().getCartItems().contains(product)) {
                Toast.makeText(ProdDetailActivity.this, "Este producto ya está en el carrito", Toast.LENGTH_SHORT).show();
            } else {
                Cart.getInstance().addItem(product);
                Toast.makeText(ProdDetailActivity.this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show();
            }
        });
    }
}