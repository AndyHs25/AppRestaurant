package com.idat.apprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PagarActivity extends AppCompatActivity {

    Button btnPagar,btnVolver2;
    TextView txtPagoNombre, txtPrecioPlato, txtPagoBebida, txtPrecioBebida, txtPagoAgregado, txtPrecioAgregado, txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagar);

        txtPagoNombre = findViewById(R.id.txtPagoNombre);
        txtPrecioPlato = findViewById(R.id.txtPrecioPlato);
        txtPagoBebida = findViewById(R.id.txtPagoBebida);
        txtPrecioBebida = findViewById(R.id.txtPrecioBebida);
        txtPagoAgregado = findViewById(R.id.txtPagoAgregado);
        txtPrecioAgregado = findViewById(R.id.txtPrecioAgregado);
        txtTotal = findViewById(R.id.txtTotal);
        btnPagar = findViewById(R.id.btnPagar);
        btnVolver2 = findViewById(R.id.btnVolver2);

        Intent intent = getIntent();
        long total = intent.getLongExtra("Total", 0);
        String plato = intent.getStringExtra("Plato");
        long platoprecio = intent.getLongExtra("PlatoPrecio", 0);
        String bebida = intent.getStringExtra("Bebida");
        long bebidaprecio = intent.getLongExtra("BebidaPrecio", 0);
        String agregado = intent.getStringExtra("Agregado");
        long agregadoprecio = intent.getLongExtra("AgregadoPrecio", 0);

        txtPagoNombre.setText(plato);
        txtPrecioPlato.setText(String.format("S/. %.2f", (double) platoprecio));
        txtPagoBebida.setText(bebida);
        txtPrecioBebida.setText(String.format("S/. %.2f", (double) bebidaprecio));
        txtPagoAgregado.setText(agregado);
        txtPrecioAgregado.setText(String.format("S/. %.2f", (double) agregadoprecio));
        txtTotal.setText(String.format("S/. %.2f", (double) total));

        btnPagar.setOnClickListener(v -> {
            new AlertDialog.Builder(PagarActivity.this)
                    .setTitle("Confirmación de Pago")
                    .setMessage("¿Desea confirmar el pago?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        new Handler().postDelayed(() -> {
                            Intent intent1 = new Intent(PagarActivity.this, MenuActivity.class);
                            startActivity(intent1);
                            finish();
                        }, 1500); // 3 seconds delay
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });


        btnVolver2.setOnClickListener(v -> {
            Intent intent1 = new Intent(PagarActivity.this, MenuActivity.class);
            startActivity(intent1);
        });


    }
}