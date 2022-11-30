package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afinal.entities.Cuenta;
import com.example.afinal.factories.RetrofitFactory;
import com.example.afinal.services.CuentaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuentaActivity extends AppCompatActivity {
    private EditText etNombre;
    private EditText etSaldoInicial;
    private Button btnGuardar;
    private Button btnListar;
    private Button btnIrMovimieto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        etNombre = findViewById(R.id.etNombre);
        etSaldoInicial = findViewById(R.id.etSaldoInicial);
        btnGuardar =  findViewById(R.id.btnGuardar);
        btnListar =  findViewById(R.id.btnListar);
        btnIrMovimieto =  findViewById(R.id.btnIrMovimiento);

        btnIrMovimieto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MovimientosActivity.class);
                startActivity(intent);
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListarCuentaActivity.class);
                startActivity(intent);
            }
        });

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cuenta cuenta = new Cuenta();

                cuenta.nombre = etNombre.getText().toString();
                cuenta.saldoInicial = Double.valueOf(etSaldoInicial.getText().toString());

                Retrofit retrofitCreate = new Retrofit.Builder()
                        .baseUrl("https://6356fead9243cf412f919b57.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                CuentaService servise = retrofitCreate.create(CuentaService.class);
                servise.create(cuenta).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("MAIN_APP","Response: "+response.code());
                        Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });







            }
        });

    }
}