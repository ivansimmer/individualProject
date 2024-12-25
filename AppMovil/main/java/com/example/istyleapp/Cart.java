package com.example.istyleapp;

import java.util.ArrayList;

// Hacer una clase de esta manera permite que los datos del carrito sean accesibles desde cualquier parte de la app

public class Cart {
    private static Cart instance;
    private ArrayList<Product> cartItems;

    private Cart() {
        cartItems = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(Product product) {
        cartItems.add(product);
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
