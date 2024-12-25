package com.example.istyleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> cartItems;
    private final String baseUrl = "http://10.0.2.2/proyecto_ism/imgProducts/";

    public CartAdapter(Context context, ArrayList<Product> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = cartItems.get(position);

        holder.tvProductName.setText(product.getNombre());
        holder.tvProductPrice.setText(String.format("€%.2f", product.getPrecio()));
        holder.tvCantidad.setText(String.valueOf(product.getCantidad()));
        holder.tvCategoria.setText(product.getCategoria());

        String imageUrl = baseUrl + product.getImagenPath() + ".png";

        // Cargar la imagen desde la URL usando Glide
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.istyle) // Imagen por defecto si la imagen aún no se ha cargado
                .error(R.drawable.istyle)      // Imagen por defecto en caso de error
                .into(holder.imgProductImage);


        holder.buttonSumo.setOnClickListener(v -> {
            product.setCantidad(product.getCantidad() + 1);
            notifyItemChanged(position);
        });

        holder.buttonResto.setOnClickListener(v -> {
            if (product.getCantidad() > 1) {
                product.setCantidad(product.getCantidad() - 1);
            } else {
                cartItems.remove(position);
                Toast.makeText(context, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvCantidad, tvCategoria;
        Button buttonSumo, buttonResto;
        ImageView imgProductImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvCartItemName);
            tvProductPrice = itemView.findViewById(R.id.tvCartItemPrice);
            imgProductImage = itemView.findViewById(R.id.imgCartItemImage);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            buttonResto = itemView.findViewById(R.id.buttonResto);
            buttonSumo = itemView.findViewById(R.id.buttonSumo);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
        }
    }
}
