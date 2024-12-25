// ApiService.java
package com.example.istyleapp;  // Asegúrate de que esté en el paquete correcto

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    // Método para mostrar todos los productos desde consultaproductos.php
    @GET("consultaproductos.php")
    Call<ResponseBody> getProductos();

    // Método para mostrar todos los productos desde consultaproductos.php
    @GET("consultaproductosdescuento.php")
    Call<ResponseBody> getProductosConDescuento();

    // Método para mostrar todas las valoraciones desde consultavaloraciones.php
    @GET("consultavaloraciones.php")
    Call<ResponseBody> getValoraciones();

    @FormUrlEncoded
    @POST("insert_user.php")
    Call<ResponseBody> insertUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("fecha_registro") String fechaRegistro
    );

}