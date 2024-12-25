package com.example.istyleapp;

public class Review {
    private String email;
    private String descripcion;
    private String fecha;
    private float rating;

    public Review(String email, String descripcion, String fecha, float rating) {
        this.email = email;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.rating = rating;
    }

    public String getEmail() { return email; }
    public String getDescripcion() { return descripcion; }
    public String getFecha() { return fecha; }
    public float getRating() { return rating; }
}
