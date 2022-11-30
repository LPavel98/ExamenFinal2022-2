package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.afinal.adapters.CuentaAdapter;
import com.example.afinal.entities.Cuenta;
import com.example.afinal.services.CuentaService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarCuentaActivity extends AppCompatActivity {
    private RecyclerView rvCuenta;
    private Button btnForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cuenta);


        rvCuenta = findViewById(R.id.rvCuenta);
        btnForm = findViewById(R.id.btnForm);
        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CuentaActivity.class);
                startActivity(intent);
            }
        });

        rvCuenta.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofitCreate = new Retrofit.Builder()
                .baseUrl("https://6356fead9243cf412f919b57.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CuentaService service = retrofitCreate.create(CuentaService.class);
        service.listP().enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                rvCuenta.setAdapter(new CuentaAdapter(response.body()));
                Log.i("MAIN_APP", "Response" + response.body().size());
            }

            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) {
                Log.e("MAIN_APP", t.toString());

            }
        });





    }
}