package com.idat.apprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class PortadaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PortadaActivity.this, PrecargaActivity.class);
            startActivity(intent);
            finish();
        }, 3000);



    }
}
