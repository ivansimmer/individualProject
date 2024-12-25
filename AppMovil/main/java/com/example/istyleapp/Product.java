// Product.java
package com.example.istyleapp;

import java.util.Objects;

public class Product {
    private String nombre;
    private Double precio;
    private String imagenPath;
    private String categoria;
    private int cantidad;

    public Product(String nombre, double precio, String categoria, String imagenPath) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.imagenPath = imagenPath;
        this.cantidad = 1;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Comparación por referencia
        if (o == null || getClass() != o.getClass()) return false; // Verificar clase
        Product product = (Product) o;
        return nombre.equals(product.nombre) && categoria.equals(product.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, categoria); // Generar un hash basado en los atributos clave
    }
}
