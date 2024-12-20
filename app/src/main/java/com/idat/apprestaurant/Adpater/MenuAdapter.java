package com.idat.apprestaurant.Adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idat.apprestaurant.DetalleActivity;
import com.idat.apprestaurant.Entity.menu;
import com.idat.apprestaurant.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<menu> menus;
    private Context context;

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView viewNameMenu, viewTipoMenu;
        ImageView viewImageMenu;
        CardView recCard;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNameMenu = itemView.findViewById(R.id.viewNameMenu);
            viewTipoMenu = itemView.findViewById(R.id.viewTipoMenu);
            viewImageMenu = itemView.findViewById(R.id.viewImageMenu);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }

    public MenuAdapter(List<menu> menus, Context context) {
        this.menus = menus;
        this.context = context;
    }

    // Método para actualizar la lista de datos
    public void setSearchList(List<menu> menuSearchList) {
        this.menus = menuSearchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        menu menu = menus.get(position);
        holder.viewNameMenu.setText(menu.getNombre());
        holder.viewTipoMenu.setText(menu.getTipo());
        holder.viewImageMenu.setImageResource(menu.getImagen());

        // Configurar el clic en el card
        holder.recCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleActivity.class);
            intent.putExtra("Nombre", menu.getNombre());
            intent.putExtra("Desc", menu.getDescripcion());
            intent.putExtra("Tipo", menu.getTipo());
            intent.putExtra("Precio", menu.getPrecio());
            intent.putExtra("Image", menu.getImagen());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

}
