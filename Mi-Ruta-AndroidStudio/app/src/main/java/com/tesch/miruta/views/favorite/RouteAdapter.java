package com.tesch.miruta.views.favorite;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.tesch.miruta.R;
import com.tesch.miruta.views.Favorite;
import com.tesch.miruta.views.favorite.entities.route;
import com.tesch.miruta.views.favorite.utilities.utilities;
import com.tesch.miruta.views.maps.MapsFragment;

import java.util.ArrayList;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

public class RouteAdapter extends ArrayAdapter<route> {
    private Context context;
    private ArrayList<route> routes;
    private ConexionSQLiteHelper conn;
    private Dialog dialog;
    private FragmentManager fragmentManager;
    private View favoriteView;

    public RouteAdapter(Context context, ArrayList<route> routes, ConexionSQLiteHelper conn, FragmentManager fragmentManager, View favoriteView) {
        super(context, 0, routes);
        this.context = context;
        this.routes = routes;
        this.conn = conn;
        this.fragmentManager = fragmentManager;
        this.favoriteView = favoriteView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_item_route, parent, false);
        }

        route currentRoute = getItem(position);
        TextView tvRouteInfo = convertView.findViewById(R.id.tvRouteInfo);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        ImageButton btnShowUpdate = convertView.findViewById(R.id.btnShowUpdate);
        TextView tvOrigen = convertView.findViewById(R.id.tvOrigen);
        TextView tvDestino = convertView.findViewById(R.id.tvDestino);

        assert currentRoute != null;
        tvRouteInfo.setText(currentRoute.getNombre());
        tvOrigen.setText("Origen: " + currentRoute.getOrigen());
        tvDestino.setText("Destino: " + currentRoute.getDestino());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(currentRoute, position);
            }
        });

        btnShowUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(currentRoute, tvRouteInfo);
            }
        });

        // Añade el OnClickListener al convertView para mostrar el toast
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_ruta = currentRoute.getId_ruta();
                showMap(id_ruta);
                Toast.makeText(getContext(), id_ruta, Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private void confirmDelete(route currentRoute, int position) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog_confirm_delete);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        Button btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);
        Button btnDialogConfirm = dialog.findViewById(R.id.btnDialogConfirm);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRoute(currentRoute);
                routes.remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void confirmUpdate(route currentRoute, String newName, TextView tvRouteInfo) {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog_confirm_update);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        Button btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);
        Button btnDialogConfirm = dialog.findViewById(R.id.btnDialogConfirm);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRoute(currentRoute, newName);
                currentRoute.setNombre(newName);
                tvRouteInfo.setText(newName); // Solo establecer el nuevo nombre
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showUpdateDialog(route currentRoute, TextView tvRouteInfo) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_update_route);
        dialog.setCancelable(false);

        EditText etUpdateName = dialog.findViewById(R.id.etUpdateName);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        Button btnCancelUpdate = dialog.findViewById(R.id.btnCancelUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etUpdateName.getText().toString().trim();
                if (!newName.isEmpty()) {
                    dialog.dismiss();
                    confirmUpdate(currentRoute, newName, tvRouteInfo);
                } else {
                    StyleableToast.makeText(context, "Ingresa el nuevo nombre",
                            Toast.LENGTH_LONG, R.style.error).show();
                }
            }
        });

        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deleteRoute(route route) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] params = {String.valueOf(route.getId())};
        db.delete(utilities.TABLA_RUTA, utilities.CAMPO_ID + "=?", params);

        StyleableToast.makeText(context, "Ruta eliminada",
                Toast.LENGTH_LONG, R.style.success).show();
        db.close();
    }

    private void updateRoute(route route, String newName) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] params = {String.valueOf(route.getId())};
        ContentValues values = new ContentValues();
        values.put(utilities.CAMPO_NOMBRE, newName);
        db.update(utilities.TABLA_RUTA, values, utilities.CAMPO_ID + "=?", params);
        StyleableToast.makeText(context, "Ruta actualizada",
                Toast.LENGTH_LONG, R.style.success).show();
        db.close();
    }

    private void showMap(String id_ruta) {
        favoriteView.findViewById(R.id.map_container).setVisibility(View.VISIBLE);
        favoriteView.findViewById(R.id.map_container_f).setVisibility(View.VISIBLE);
        MapsFragment mapsFragment = MapsFragment.newIntance(id_ruta);
        fragmentManager.beginTransaction()
                .replace(R.id.map_container, mapsFragment)
                .commit();

        // Obtener el botón de cerrar y establecer el OnClickListener
        Button btnCloseFragment = favoriteView.findViewById(R.id.btnCloseFragment);
        btnCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.map_container)))
                        .commit();

                favoriteView.findViewById(R.id.map_container).setVisibility(View.GONE);
                favoriteView.findViewById(R.id.map_container_f).setVisibility(View.GONE);
            }
        });
    }


}