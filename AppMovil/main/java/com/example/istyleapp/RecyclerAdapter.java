// ProductRecyclerAdapter.java
package com.example.istyleapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private final ArrayList<Product> products;
    private final String baseUrl = "http://10.0.2.2/proyecto_ism/imgProducts/";

    public RecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    // ViewHolder para reciclar las vistas
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView precio;
        TextView categoria;
        ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNomProduct);
            precio = itemView.findViewById(R.id.tvPrecioProducto);
            categoria = itemView.findViewById(R.id.tvCategoria);
            imagen = itemView.findViewById(R.id.imgProduct);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.nombre.setText(product.getNombre());
        holder.categoria.setText(product.getCategoria());
        holder.precio.setText(String.format("€%.2f", product.getPrecio()));

        String imageUrl = baseUrl + product.getImagenPath() + ".png";

        // Cargar la imagen desde la URL usando Glide
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.istyle) // Imagen por defecto si la imagen aún no se ha cargado
                .error(R.drawable.istyle)      // Imagen por defecto en caso de error
                .into(holder.imagen);

        // Click del Producto
        holder.itemView.setOnClickListener(v -> {
            Intent intentProdDetail = new Intent(context, ProdDetailActivity.class);
            intentProdDetail.putExtra("PRODUCT_NAME", product.getNombre());
            intentProdDetail.putExtra("PRODUCT_CATEGORY", product.getCategoria());
            intentProdDetail.putExtra("PRODUCT_PRICE", product.getPrecio());
            intentProdDetail.putExtra("PRODUCT_IMAGE", product.getImagenPath());
            context.startActivity(intentProdDetail);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}