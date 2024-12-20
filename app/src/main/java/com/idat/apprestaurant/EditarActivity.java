package com.idat.apprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.idat.apprestaurant.Entity.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditarActivity extends AppCompatActivity {

    TextView viewdni , viewnombre, viewcorreo,viewtelefono, viewsexo;


    Button btnVolver5;

    RequestQueue requestQueue;
    private static final String urlActualizar = "http://192.168.1.43/restaurant/updateClient.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);

        viewdni = findViewById(R.id.viewdni);
        viewnombre = findViewById(R.id.viewNombre);
        viewtelefono = findViewById(R.id.viewtelefono);
        viewcorreo = findViewById(R.id.viewcorreo);
        viewsexo = findViewById(R.id.viewsexo);
        btnVolver5 = findViewById(R.id.btnVolver5);


        btnVolver5.setOnClickListener(v -> {
            Intent intent = new Intent(EditarActivity.this, ListadoActivity.class);
            startActivity(intent);
        });

        client cliente = (client) getIntent().getSerializableExtra("cliente");
        if (cliente != null) {
            viewdni.setText(cliente.getDni());
            viewnombre.setText(cliente.getNombre() + " " + cliente.getApellido());
            viewtelefono.setText(cliente.getTelefono());
            viewcorreo.setText(cliente.getCorreo());
            viewsexo.setText(cliente.getSexo());
        }

        requestQueue = Volley.newRequestQueue(this);

    }

    private void actualizarCliente(String dni) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlActualizar,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.getString("message");
                        Toast.makeText(EditarActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditarActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(EditarActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("dni", dni);
                parametros.put("nombre", viewnombre.getText().toString());
                parametros.put("apellido","");
                parametros.put("sexo", viewsexo.getText().toString());
                parametros.put("telefono", viewtelefono.getText().toString());
                parametros.put("direccion", "Lima");
                parametros.put("correo", viewcorreo.getText().toString());
                parametros.put("estado", "Activo");
                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }




}