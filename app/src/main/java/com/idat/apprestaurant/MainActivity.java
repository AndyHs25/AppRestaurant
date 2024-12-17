package com.idat.apprestaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtDni, txtNombre, txtApellido, txtSexo, txtDireccion, txtTelefono, txtCorreo ;
    Button btnRegistrar, btnListar, btnActualizar, btnEliminar;

    RequestQueue requestQueue;
    private static final String urlservidor = "http://192.168.1.43/restaurant/insertClient.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtDni = findViewById(R.id.txtDni);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtSexo = findViewById(R.id.txtSexo);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);


        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);

        requestQueue = Volley.newRequestQueue(this);

        btnRegistrar.setOnClickListener(v -> registerClient());

        btnListar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListadoActivity.class);
            startActivity(intent);
        });
    }

    public void registerClient() {
        if (!validarCampos()) {
            Toast.makeText(this, "Complete los campos correctamente", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar datos al servidor
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlservidor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if (status.equals("success")) {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            } else {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("dni", txtDni.getText().toString());
                parametros.put("nombre", txtNombre.getText().toString());
                parametros.put("apellido", txtApellido.getText().toString());
                parametros.put("sexo", txtSexo.getText().toString());
                parametros.put("telefono", txtTelefono.getText().toString());
                parametros.put("direccion", txtDireccion.getText().toString());
                parametros.put("correo", txtCorreo.getText().toString());
                parametros.put("estado", "Activo");

                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }
    private boolean validarCampos() {
        EditText[] campos = {txtDni, txtNombre, txtApellido, txtSexo, txtDireccion, txtTelefono, txtCorreo};

        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.setError("Campo requerido");
                campo.requestFocus();
                return false;
            }
        }

        // Validar que el DNI tenga 8 dígitos y solo números
        String dni = txtDni.getText().toString();
        if (!dni.matches("\\d{8}")) {
            txtDni.setError("El DNI debe tener 8 dígitos y solo números");
            txtDni.requestFocus();
            return false;
        }

        // Validar que el número de celular tenga 9 dígitos y empiece con 9
        String telefono = txtTelefono.getText().toString();
        if (!telefono.matches("9\\d{8}")) {
            txtTelefono.setError("El número de celular debe tener 9 dígitos y empezar con 9");
            txtTelefono.requestFocus();
            return false;
        }

        // Validar que los nombres no contengan números
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            txtNombre.setError("El nombre no debe contener números");
            txtNombre.requestFocus();
            return false;
        }
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            txtApellido.setError("El apellido no debe contener números");
            txtApellido.requestFocus();
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtSexo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

}