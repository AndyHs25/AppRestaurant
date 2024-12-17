package com.idat.apprestaurant.Adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.idat.apprestaurant.EditarActivity;
import com.idat.apprestaurant.Entity.client;
import com.idat.apprestaurant.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
    private List<client> clientes;
    private Context context;

    public ClienteAdapter(List<client> clientes, Context context) {
        this.clientes = clientes;
        this.context = context;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        client cliente = clientes.get(position);
        holder.viewDni.setText(cliente.getDni());
        holder.viewNombre.setText(cliente.getNombre());
        holder.viewTelefono.setText(cliente.getTelefono());
        holder.viewCorreo.setText(cliente.getCorreo());

        holder.btnEditar.setOnClickListener(v -> {

            Intent intent = new Intent(context, EditarActivity.class);
            intent.putExtra("cliente", cliente);
            context.startActivity(intent);

        });

        holder.btnEliminar.setOnClickListener(v -> {
            deleteClient(cliente.getDni(), position);
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView viewDni, viewNombre, viewTelefono, viewCorreo;
        Button btnEditar, btnEliminar;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            viewDni = itemView.findViewById(R.id.viewDni);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);

        }
    }

    private void deleteClient(String dni, int position) {
        String url = "http://192.168.1.43/restaurant/deleteClient.php?dni=" + dni;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success")) {
                    clientes.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, clientes.size());
                    Toast.makeText(context, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(context, "Error al eliminar cliente", Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
