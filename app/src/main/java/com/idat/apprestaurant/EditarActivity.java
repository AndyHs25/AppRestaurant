package com.idat.apprestaurant;

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

    TextView viewdni;
    EditText editNombre, editApellido,editTelefono, editCorreo ,editDireccion, editSexo,editEstado;

    Button btnActualizar;

    RequestQueue requestQueue;
    private static final String urlActualizar = "http://192.168.1.43/restaurant/updateClient.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);

        viewdni = findViewById(R.id.viewdni);
        editNombre = findViewById(R.id.editNombre);
        editApellido = findViewById(R.id.editApellido);
        editTelefono = findViewById(R.id.editTelefono);
        editCorreo = findViewById(R.id.editCorreo);
        editDireccion = findViewById(R.id.editDireccion);
        editSexo = findViewById(R.id.editSexo);
        editEstado = findViewById(R.id.editEstado);

        btnActualizar = findViewById(R.id.btnActualizar);

        client cliente = (client) getIntent().getSerializableExtra("cliente");
        if (cliente != null) {
            viewdni.setText(cliente.getDni());
            editNombre.setText(cliente.getNombre());
            editApellido.setText(cliente.getApellido());
            editTelefono.setText(cliente.getTelefono());
            editCorreo.setText(cliente.getCorreo());
            editDireccion.setText(cliente.getDireccion());
            editSexo.setText(cliente.getSexo());
            editEstado.setText(cliente.getEstado());
        }

        requestQueue = Volley.newRequestQueue(this);

        btnActualizar.setOnClickListener(v -> {
            if (validarCampos()) {
                actualizarCliente(cliente.getDni());
            }
        });

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
                parametros.put("nombre", editNombre.getText().toString());
                parametros.put("apellido", editApellido.getText().toString());
                parametros.put("sexo", editSexo.getText().toString());
                parametros.put("telefono", editTelefono.getText().toString());
                parametros.put("direccion", editDireccion.getText().toString());
                parametros.put("correo", editCorreo.getText().toString());
                parametros.put("estado", editEstado.getText().toString());
                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }

    private boolean validarCampos() {
        EditText[] campos = { editNombre, editApellido, editSexo, editDireccion, editTelefono, editCorreo};

        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.setError("Campo requerido");
                campo.requestFocus();
                return false;
            }
        }

        // Validar que el número de celular tenga 9 dígitos y empiece con 9
        String telefono = editTelefono.getText().toString();
        if (!telefono.matches("9\\d{8}")) {
            editTelefono.setError("El número de celular debe tener 9 dígitos y empezar con 9");
            editTelefono.requestFocus();
            return false;
        }

        // Validar que los nombres no contengan números
        String nombre = editNombre.getText().toString();
        String apellido = editApellido.getText().toString();
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            editNombre.setError("El nombre no debe contener números");
            editNombre.requestFocus();
            return false;
        }
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            editApellido.setError("El apellido no debe contener números");
            editApellido.requestFocus();
            return false;
        }

        return true;
    }


}