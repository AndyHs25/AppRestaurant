package com.idat.apprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    TextView viewNombreDetalle, viewDescripcionDetalle;
    RadioButton rbtPrecio1, rbtPrecio2, rbtBebida1, rbtBebida2, rbtAgregado1, rbtAgregado2;
    ImageView viewMenuDetalle;
    Button btnPedido, btnVolver;
    long total = 0, bebidaprecio = 0, agregadoprecio = 0, platoprecio = 0;
    String plato = "", bebida = "", agregado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);

        viewNombreDetalle = findViewById(R.id.viewNombreDetalle);
        viewDescripcionDetalle = findViewById(R.id.viewDescripcionDetalle);
        rbtPrecio1 = findViewById(R.id.rbtPrecio1);
        rbtPrecio2 = findViewById(R.id.rbtPrecio2);
        rbtBebida1 = findViewById(R.id.rbtBebida1);
        rbtBebida2 = findViewById(R.id.rbtBebida2);
        rbtAgregado1 = findViewById(R.id.rbtAgregado1);
        rbtAgregado2 = findViewById(R.id.rbtAgregado2);
        viewMenuDetalle = findViewById(R.id.viewMenuDetalle);

        btnPedido = findViewById(R.id.btnPedido);
        btnVolver = findViewById(R.id.btnVolver);

        btnPedido.setOnClickListener(v -> {
            updateTotal();
            Intent intent = new Intent(DetalleActivity.this, PagarActivity.class);
            intent.putExtra("Total", total);
            intent.putExtra("Plato", plato);
            intent.putExtra("PlatoPrecio", platoprecio);
            intent.putExtra("Bebida", bebida);
            intent.putExtra("BebidaPrecio", bebidaprecio);
            intent.putExtra("Agregado", agregado);
            intent.putExtra("AgregadoPrecio", agregadoprecio);
            startActivity(intent);

            rbtPrecio1.setChecked(false);
            rbtPrecio2.setChecked(false);
            rbtBebida1.setChecked(false);
            rbtBebida2.setChecked(false);
            rbtAgregado1.setChecked(false);
            rbtAgregado2.setChecked(false);

        });

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(DetalleActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        String nombre = getIntent().getStringExtra("Nombre");
        String descripcion = getIntent().getStringExtra("Desc");
        long precio = getIntent().getLongExtra("Precio", 0);
        int imagen = getIntent().getIntExtra("Image", 0);

        viewNombreDetalle.setText(nombre);
        viewDescripcionDetalle.setText(descripcion);
        viewMenuDetalle.setImageResource(imagen);
        rbtPrecio1.setText(String.format("S/. %.2f", (double) precio));
        rbtPrecio2.setText(String.format("S/. %.2f", (double) (precio + 30)));
    }

    private void updateTotal() {
        total = 0;
        platoprecio = 0;
        bebidaprecio = 0;
        agregadoprecio = 0;

        long precio = getIntent().getLongExtra("Precio", 0);

        if (rbtPrecio1.isChecked()) {
            platoprecio = precio;
            plato = getIntent().getStringExtra("Nombre");
        } else if (rbtPrecio2.isChecked()) {
            platoprecio = precio + 30;
            plato = getIntent().getStringExtra("Nombre");
        }
        total += platoprecio;

        if (rbtBebida1.isChecked()) {
            bebidaprecio = 10;
            bebida = "Chicha";
        } else if (rbtBebida2.isChecked()) {
            bebidaprecio = 25;
            bebida = "Vino";
        }
        total += bebidaprecio;

        if (rbtAgregado1.isChecked()) {
            agregadoprecio = 15;
            agregado = "Ensalada";
        } else if (rbtAgregado2.isChecked()) {
            agregadoprecio = 12;
            agregado = "Pan";
        }
        total += agregadoprecio;
    }
}