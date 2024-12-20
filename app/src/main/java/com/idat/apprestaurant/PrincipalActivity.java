package com.idat.apprestaurant;

import static android.text.TextUtils.substring;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PrincipalActivity extends AppCompatActivity {

    Button btnIniciarSesion, btnRegistro, btnClientes;
    EditText txtDniSesion, txtNombreSesion;
    RequestQueue requestQueue;
    private static final String urlGetClient = "http://192.168.1.43/restaurant/consultClient.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnClientes = findViewById(R.id.btnClientes);
        txtDniSesion = findViewById(R.id.txtDniSesion);
        txtNombreSesion = findViewById(R.id.txtNombreSesion);

        requestQueue = Volley.newRequestQueue(this);

        btnRegistro.setOnClickListener(v -> {
            startActivity(new Intent(PrincipalActivity.this, MainActivity.class));
        });

        btnClientes.setOnClickListener(v -> {
            startActivity(new Intent(PrincipalActivity.this, ListadoActivity.class));
        });

        btnIniciarSesion.setOnClickListener(v -> {
            String dni = txtDniSesion.getText().toString();
            String nombre = txtNombreSesion.getText().toString();
            if (!nombre.isEmpty()) {
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
            }
            if (dni.isEmpty()) {
                txtDniSesion.setError("Ingrese su DNI");
                Toast.makeText(this, "El campo DNI está vacío", Toast.LENGTH_SHORT).show();
            } else if (dni.length() != 8 || !dni.matches("\\d+")) {
                txtDniSesion.setError("El DNI debe tener 8 dígitos numéricos");
                Toast.makeText(this, "El DNI debe tener 8 dígitos numéricos", Toast.LENGTH_SHORT).show();
            } else if (nombre.isEmpty()) {
                txtNombreSesion.setError("Ingrese su nombre");
                Toast.makeText(this, "El campo Nombre está vacío", Toast.LENGTH_SHORT).show();
            } else if (!nombre.matches("[a-zA-Z\\s]+")) {
                txtNombreSesion.setError("El nombre no debe contener números");
                Toast.makeText(this, "El nombre no debe contener números", Toast.LENGTH_SHORT).show();
            } else {
                verificarCliente(dni, nombre);
                txtDniSesion.setText("");
                txtNombreSesion.setText("");
            }
        });
    }

    private void verificarCliente(String dni, String nombre) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetClient + "?dni=" + dni + "&nombre=" + nombre,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Intent intent = new Intent(PrincipalActivity.this, MenuActivity.class);
                            intent.putExtra("dni", dni);
                            intent.putExtra("nombre", nombre);
                            startActivity(intent);
                        } else {
                            String message = jsonResponse.getString("message");
                            Toast.makeText(PrincipalActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(PrincipalActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(PrincipalActivity.this, "Error al verificar cliente", Toast.LENGTH_SHORT).show());

        requestQueue.add(stringRequest);
    }
}