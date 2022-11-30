package com.example.afinal.services;

import com.example.afinal.entities.Cuenta;
import com.example.afinal.entities.Mvimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CuentaService {
    @GET("cuentas")
    Call<List<Cuenta>> listP();

    @POST("cuentas")
    Call<Void> create(@Body Cuenta cuenta);
    @POST("/cuentas/{id}/movimientos")
    Call<Mvimiento> crearMvimiento(@Path("id") int id, @Body Mvimiento mvimiento);

    //Actualizar
    @PUT("cuentas/{id}")
    Call<Void> update(@Body Cuenta cuenta, @Path("id") int id);

    @DELETE("cuentas/{id}")
    Call<Void> delete(@Path("id") int id);
}


