// Usuario.java
package com.example.istyleapp;

public class Usuario {
    private String email;
    private String password;

    public Usuario() {
        // Constructor vacío requerido para Firestore
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}