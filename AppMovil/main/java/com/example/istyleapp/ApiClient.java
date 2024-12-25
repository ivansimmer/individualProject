//ApiClient.java
package com.example.istyleapp;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    // URL base de ls API
    private static final String BASE_URL = "http://10.0.2.2/proyecto_ism/";

    // Método para obtener el servicio Retrofit
    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL) // La URL base
                .addConverterFactory(ScalarsConverterFactory.create()) // Para convertir la respuesta a String (en este caso XML)
                .build();
    }

    // Método para obtener los productos usando Retrofit y ApiService
    public static String obtenerProductosXML() {
        ApiService apiService = getRetrofitInstance().create(ApiService.class);

        try {
            // Hacer la llamada para obtener todos los productos
            retrofit2.Response<ResponseBody> response = apiService.getProductos().execute();
            if (response.isSuccessful()) {
                return response.body().string(); // Devuelve el XML como String
            } else {
                return null;  // Si la respuesta no es exitosa, devuelve null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // En caso de error, devuelve null
        }
    }

    // Método para obtener los productos con descuento usando Retrofit y ApiService
    public static String obtenerProductosDescuentoXML() {
        ApiService apiService = getRetrofitInstance().create(ApiService.class);

        try {
            // Hacer la llamada para obtener todos los productos con descuento
            retrofit2.Response<ResponseBody> response = apiService.getProductosConDescuento().execute();
            if (response.isSuccessful()) {
                return response.body().string(); // Devuelve el XML como String
            } else {
                return null;  // Si la respuesta no es exitosa, devuelve null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // En caso de error, devuelve null
        }
    }

}
