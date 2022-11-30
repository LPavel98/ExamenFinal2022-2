package com.example.afinal.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.ListarCuentaActivity;
import com.example.afinal.R;
import com.example.afinal.entities.Cuenta;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuentaAdapter extends RecyclerView.Adapter{
    List<Cuenta> data;
    public  CuentaAdapter(List<Cuenta> data){
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_cuenta, parent, false);
        return new CuentaViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cuenta cuenta = data.get(position);


        TextView tvNombre = holder.itemView.findViewById(R.id.tvNombre);
        TextView tvSaldoInicial= holder.itemView.findViewById(R.id.tvSaldoInicial);




        tvNombre.setText(cuenta.nombre);
        tvSaldoInicial.setText(String.valueOf(cuenta.saldoInicial));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), ListarCuentaActivity.class);
                intent.putExtra("DATA", new Gson().toJson(cuenta));

                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CuentaViewHolder extends RecyclerView.ViewHolder{

        public CuentaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
