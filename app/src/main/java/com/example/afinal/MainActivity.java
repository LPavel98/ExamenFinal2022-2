package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnListar1;
    private Button btnGuardar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar1=findViewById(R.id.btnGuardar1);
        btnGuardar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CuentaActivity.class);
                startActivity(intent);
            }
        });

        btnListar1=findViewById(R.id.btnListar1);
        btnListar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),ListarCuentaActivity.class);
                startActivity(intent);

            }
        });


    }
}