package com.idat.apprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.idat.apprestaurant.Adpater.MenuAdapter;
import com.idat.apprestaurant.Entity.menu;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<menu> menuList;
    MenuAdapter adapter;
    ViewPager2 imageSlider;
    menu menuData;
    SearchView searchView;
    Button btnVolver8;

    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerviewMenu);
        searchView = findViewById(R.id.searchMenu);
        searchView.clearFocus();
        btnVolver8 = findViewById(R.id.btnVolver8);
        arrancarSlider();

        btnVolver8.setOnClickListener(v -> {
            new AlertDialog.Builder(MenuActivity.this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Estás seguro de que deseas cerrar sesión y volver al inicio?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Code to log out and return to PrincipalActivity
                        Intent intent = new Intent(MenuActivity.this, PrincipalActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });


        //Configurar el RecyclerView

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MenuActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        menuList = new ArrayList<>();


        //Agregar datos al RecyclerView
        menuData = (new menu("FILETE", getString(R.string.filete), "Principal",(long)70.00, R.drawable.filete));
        menuList.add(menuData);
        menuData = (new menu("SALMON", getString(R.string.salmon), "Principal",(long)80.00, R.drawable.salmon));
        menuList.add(menuData);
        menuData = (new menu("RISOTTO", getString(R.string.risotto), "Principal",(long)65.00, R.drawable.risotto));
        menuList.add(menuData);
        menuData = (new menu("CARPACCIO", getString(R.string.carpaccio), "Principal",(long)35.00, R.drawable.carpaccio));
        menuList.add(menuData);
        menuData = (new menu("LANGOSTA", getString(R.string.langosta), "Principal",(long)45.00, R.drawable.langosta));
        menuList.add(menuData);
        menuData = (new menu("CORDERO", getString(R.string.cordero), "Principal",(long)90.00, R.drawable.cordero));
        menuList.add(menuData);
        menuData = (new menu("FOIE GRAS", getString(R.string.foiegras), "Principal",(long)60.00, R.drawable.foiegras));
        menuList.add(menuData);
        menuData = (new menu("MOUSSE", getString(R.string.mousse), "Postre",(long)35.00, R.drawable.mousse));
        menuList.add(menuData);
        menuData = (new menu("PANNA COTTA", getString(R.string.pannacotta), "Postre",(long)35.00, R.drawable.pannacotta));
        menuList.add(menuData);
        menuData = (new menu("TARTA", getString(R.string.tarta), "Postre",(long)35.00, R.drawable.tarta));
        menuList.add(menuData);
        menuData = (new menu("BISQUE", getString(R.string.bisque), "Sopa",(long)30.00, R.drawable.bisque));
        menuList.add(menuData);
        menuData = (new menu("MINESTRONE", getString(R.string.minestrone), "Sopa",(long)30.00, R.drawable.minestrone));
        menuList.add(menuData);


        adapter = new MenuAdapter(menuList, MenuActivity.this);
        recyclerView.setAdapter(adapter);

    }

    public void arrancarSlider(){
        imageSlider = findViewById(R.id.imageSlider);

        List<Integer> images = Arrays.asList(
                R.drawable.plato1,
                R.drawable.plato2,
                R.drawable.plato3
        );

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        imageSlider.setAdapter(adapter);
    }

    private void iniciarAutoSlider() {
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = imageSlider.getCurrentItem();
                int nextItem = (currentItem + 1) % imageSlider.getAdapter().getItemCount();
                imageSlider.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 3000); // Cambia la imagen cada 3 segundos
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sliderHandler.removeCallbacks(sliderRunnable);
    }


    private void searchList(String text) {
        List<menu> menuSearchList = new ArrayList<>();
        for (menu data : menuList) {
            if (data.getNombre().toLowerCase().contains(text.toLowerCase())) {
                menuSearchList.add(data);
            }
        }
        if (menuSearchList.isEmpty()) {
            Toast.makeText(this, "No se encontró el resultado", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(menuSearchList);
        }
    }
}