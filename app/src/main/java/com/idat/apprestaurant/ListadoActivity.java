package com.idat.apprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.idat.apprestaurant.Adpater.ClienteAdapter;
import com.idat.apprestaurant.Entity.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClienteAdapter clienteAdapter;
    private List<client> clientList;
    Button btnVolver3;
    SearchView searchCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        recyclerView = findViewById(R.id.listaClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientList = new ArrayList<>();
        clienteAdapter = new ClienteAdapter(clientList, this);
        recyclerView.setAdapter(clienteAdapter);
        searchCliente = findViewById(R.id.searchCliente);

        getAllClients();

        btnVolver3 = findViewById(R.id.btnVolver3);
        btnVolver3.setOnClickListener(v -> {
            Intent intent = new Intent(ListadoActivity.this, PrincipalActivity.class);
            startActivity(intent);
        });

        searchCliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    private void getAllClients() {
        String url = "http://192.168.1.43/restaurant/listClient.php";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("clients");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        client client = new client();
                        client.setDni(object.getString("dni"));
                        client.setNombre(object.getString("nombre"));
                        client.setApellido(object.getString("apellido"));
                        client.setSexo(object.getString("sexo"));
                        client.setDireccion(object.getString("direccion"));
                        client.setTelefono(object.getString("telefono"));
                        client.setCorreo(object.getString("correo"));
                        client.setEstado(object.getString("estado"));
                        clientList.add(client);
                    }
                    clienteAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void searchList(String query) {
        List<client> clients = new ArrayList<>();
        for (client client : clientList) {
            if (client.getNombre().toLowerCase().contains(query.toLowerCase())) {
                clients.add(client);
            }
        }

        if (clients.isEmpty()) {
            Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
        }
        clienteAdapter.setSearchList(clients);
    }
}