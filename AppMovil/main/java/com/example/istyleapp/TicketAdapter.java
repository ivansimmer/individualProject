package com.example.istyleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> products;

    public TicketAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        double totalPricePerProduct = product.getPrecio() * product.getCantidad();

        holder.tvProductName.setText(product.getNombre());
        holder.tvUnitPrice.setText(String.format("€%.2f", product.getPrecio()));
        holder.tvQuantity.setText(String.valueOf(product.getCantidad()));
        holder.tvTotalPricePerProduct.setText(String.format("€%.2f", totalPricePerProduct));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvUnitPrice, tvQuantity, tvTotalPricePerProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPricePerProduct = itemView.findViewById(R.id.tvTotalPricePerProduct);
        }
    }
}
