package com.example.afinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.afinal.entities.Cuenta;
import com.example.afinal.entities.Image;
import com.example.afinal.entities.ImagenResponse;
import com.example.afinal.entities.Mvimiento;
import com.example.afinal.factories.RetrofitFactory;
import com.example.afinal.services.CuentaService;
import com.example.afinal.services.ImageService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovimientosActivity extends AppCompatActivity {
    Mvimiento mvimiento = new Mvimiento();
    private Spinner spTipo;
    private EditText etLongitud;
    private EditText etLatitud;
    private EditText etMotivo;
    private EditText etMonto;
    private EditText etURL;
    private Button btnTomar;
    private Button btnGuardarMovimiento;
    private ImageView imgMovimiento;
    Cuenta cuenta0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        spTipo = findViewById(R.id.spTipo);
        etLongitud = findViewById(R.id.etLongitud);
        etLatitud = findViewById(R.id.etLatitud);
        etMotivo = findViewById(R.id.etMotivo);
        etMonto = findViewById(R.id.etMonto);
        etURL = findViewById(R.id.etURL);
        imgMovimiento = findViewById(R.id.imgMovimiento);

        btnTomar = findViewById(R.id.btnTomar);
        btnGuardarMovimiento = findViewById(R.id.btnGuardarMovimiento);


        etLatitud.setText("-7.161550");
        etLongitud.setText("-78.513262");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_movimientos, android.R.layout.simple_spinner_dropdown_item);

        spTipo.setAdapter(adapter);

        Intent intent = getIntent();
        String dataJson = intent.getStringExtra("DATA");

        Log.i("MAIN_APP", new Gson().toJson(dataJson));

        btnGuardarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(dataJson != null){
                    cuenta0 = new Gson().fromJson(dataJson, Cuenta.class);
                    spTipo.getSelectedItem();

                        mvimiento.idCuenta = cuenta0.id;
                        mvimiento.motivo = etMotivo.getText().toString();
                        mvimiento.monto = Double.valueOf(etMonto.getText().toString()) ;
                        mvimiento.latitud = Double.valueOf(etLatitud.toString()) ;
                        mvimiento.longitud = Double.valueOf(etLongitud.toString()) ;
                        mvimiento.image = etURL.getText().toString();
                        mvimiento.tipo = spTipo.toString();




                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://6356fead9243cf412f919b57.mockapi.io/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CuentaService service = retrofit.create(CuentaService.class);
                        service.crearMvimiento(cuenta0.id, mvimiento).enqueue(new Callback<Mvimiento>() {
                            @Override
                            public void onResponse(Call<Mvimiento> call, Response<Mvimiento> response) {

                                Log.i("MAIN_APP","Response: "+response.code());


                                Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();




                            }

                            @Override
                            public void onFailure(Call<Mvimiento> call, Throwable t) {

                            }
                        });
                    }


                }

        });

        btnTomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    openCamara();

                }
                else{
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, 100);
                }

            }
        });




    }

    private void openCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1000);
    }



//.baseUrl("https://6356fead9243cf412f919b57.mockapi.io/")


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgMovimiento.setImageBitmap(imageBitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit retrofit = new RetrofitFactory(this)
                    .build("https://api.imgur.com/", "Client-ID 8bcc638875f89d9");

            ImageService imageService = retrofit.create(ImageService.class);
            Image image = new Image();
            image.image = encoded;
            imageService.enviarImage(image).enqueue(new Callback<ImagenResponse>() {
                @Override
                public void onResponse(Call<ImagenResponse> call, Response<ImagenResponse> response) {
                    ImagenResponse res = response.body();
                    etURL.setText(res.data.link);
                    guardarURL();
                }
                @Override
                public void onFailure(Call<ImagenResponse> call, Throwable t) {
                }
            });


        }

    }

    private void guardarURL() {
        Retrofit retrofit4 = new RetrofitFactory(MovimientosActivity.this)
                .build("https://api.imgur.com/", "Client-ID 8bcc638875f89d9");

        CuentaService s = retrofit4.create(CuentaService.class);

        s.create(new Cuenta()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}



