// ProductAdapter.java
package com.example.istyleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, ArrayList<Product> products) {

        super(context, 0, products);
    }

    private static class ViewHolder {

        TextView nombre;
        TextView precio;
        TextView categoria;
        ImageView imagen;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        TextView tvNomProduct = convertView.findViewById(R.id.tvNomProduct);
        TextView tvPrecioProducto = convertView.findViewById(R.id.tvPrecioProducto);
        TextView tvCategoria = convertView.findViewById(R.id.tvCategoria);
        ImageView imgProducto = convertView.findViewById(R.id.imgProduct);

        if (product != null) {
            // Asignar nombre y precio
            tvNomProduct.setText(product.getNombre());
            tvCategoria.setText(product.getCategoria());
            tvPrecioProducto.setText(String.format("€%.2f", product.getPrecio()));

            // Cargar la imagen desde la URL
            Glide.with(getContext())
                    .load("http://10.0.2.2/proyecto_ism/imgProducts/" + product.getImagenPath() + ".png")
                    .placeholder(R.drawable.istyle) // Imagen por defecto mientras carga
                    .error(R.drawable.istyle) // Imagen por defecto en caso de error
                    .into(imgProducto);
        }

        return convertView;
    }
}