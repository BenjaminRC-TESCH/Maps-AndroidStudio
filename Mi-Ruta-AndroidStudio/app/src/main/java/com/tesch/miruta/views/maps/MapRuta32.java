package com.tesch.miruta.views.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.tesch.miruta.R;
import com.tesch.miruta.auth.Login;
import com.tesch.miruta.utils.LoginResult;
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.RetrofitProfileInitialization;
import com.tesch.miruta.views.AboutActivity;
import com.tesch.miruta.views.ChatbotActivity;
import com.tesch.miruta.views.Favorite;
import com.tesch.miruta.views.MainActivity;
import com.tesch.miruta.views.MapActivity;
import com.tesch.miruta.views.Profile;
import com.tesch.miruta.views.favorite.ConexionSQLiteHelper;
import com.tesch.miruta.views.favorite.utilities.utilities;

import java.util.HashMap;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRuta32 extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, chatbot, map, about, logout, profile, fav;
    TextView name, email;
    TextView asset;
    Dialog dialog;
    Button btnDialogCancel, btnDialogLogout, btnCloseFragment;
    ImageButton buttonBack, likeRuta2, likeRuta3, likeRuta4, likeRuta5;
    CardView ruta2, ruta3, ruta4, ruta5;
    SearchView searchRoute;
    private String opcionBoton;
    private String campo_id_ruta;
    private String nombre;
    private String origen;
    private String destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_ruta32);
        views();
        setupSearch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
        changeAppearance();
        menuDrawerOptions();
        showDialog();
        cardEvent();
    }

    private void setupSearch() {
        searchRoute.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCards(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCards(newText);
                return true;
            }
        });
    }

    private void filterCards(String query) {
        query = query.toLowerCase();
        CardView[] cards = {ruta2, ruta3, ruta4, ruta5};
        String[] descriptions = {
                "chalco a san martín cuautlalpan",
                "chalco a santa maría",
                "chalco a iturbide",
                "chalco a el llano"
        };

        for (int i = 0; i < cards.length; i++) {
            if (descriptions[i].toLowerCase().contains(query)) {
                cards[i].setVisibility(View.VISIBLE);
            } else {
                cards[i].setVisibility(View.GONE);
            }
        }
    }

    private void cardEvent(){

        ruta2.setOnClickListener(new View.OnClickListener() { //chalco san martin
            @Override
            public void onClick(View v) {
                opcionBoton = "ruta2";
                showMap();
            }
        });

        ruta3.setOnClickListener(new View.OnClickListener() {//chalco santa maria
            @Override
            public void onClick(View v) {
                opcionBoton = "ruta3";
                showMap();
            }
        });

        ruta4.setOnClickListener(new View.OnClickListener() {//chalco huexoculco
            @Override
            public void onClick(View v) {
                opcionBoton = "ruta4";
                showMap();
            }
        });

        ruta5.setOnClickListener(new View.OnClickListener() {//chalco el llano
            @Override
            public void onClick(View v) {
                opcionBoton = "ruta5";
                showMap();
            }
        });

        btnCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map_container))).commit();

                findViewById(R.id.map_container).setVisibility(View.GONE);
                findViewById(R.id.map_container_f).setVisibility(View.GONE);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(back);
            }
        });

        likeRuta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campo_id_ruta = "ruta2";
                nombre = "Ruta 36: Chalco - San Martín Cuautlalpan";
                origen = "Chalco";
                destino = "San Martín Cuautlalpan";
                likeRuta(campo_id_ruta, nombre, origen, destino);
            }
        });

        likeRuta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campo_id_ruta = "ruta3";
                nombre = "Ruta 36: Chalco - Santa María";
                origen = "Chalco";
                destino = "Santa María";
                likeRuta(campo_id_ruta, nombre, origen, destino);
            }
        });

        likeRuta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campo_id_ruta = "ruta3";
                nombre = "Ruta 36: Chalco - Iturbide";
                origen = "Chalco";
                destino = "Iturbide";
                likeRuta(campo_id_ruta, nombre, origen, destino);
            }
        });

        likeRuta5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campo_id_ruta = "ruta3";
                nombre = "Ruta 36: Chalco - El llano";
                origen = "Chalco";
                destino = "El llano";
                likeRuta(campo_id_ruta, nombre, origen, destino);
            }
        });
    }

    public void likeRuta(String campo_id_ruta, String nombre, String origen, String destino) {
        try {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_ruta", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            // Consulta para verificar si ya existe un registro con el mismo origen y destino
            String query = "SELECT * FROM " + utilities.TABLA_RUTA + " WHERE " + utilities.CAMPO_ORIGEN + "=? AND " + utilities.CAMPO_DESTINO + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{origen, destino});

            if (cursor.getCount() > 0) {
                // Si ya existe, mostrar mensaje y no insertar
                StyleableToast.makeText(getApplicationContext(), "Esta ruta ya esta en favoritos",
                        Toast.LENGTH_LONG, R.style.error).show();
            } else {
                // Si no existe, insertar el nuevo registro
                ContentValues values = new ContentValues();
                values.put(utilities.CAMPO_ID_RUTA, campo_id_ruta);
                values.put(utilities.CAMPO_NOMBRE, nombre);
                values.put(utilities.CAMPO_ORIGEN, origen);
                values.put(utilities.CAMPO_DESTINO, destino);

                Long idResultante = db.insert(utilities.TABLA_RUTA, null, values); // Asegúrate de que el segundo parámetro es null

                StyleableToast.makeText(getApplicationContext(), "Ruta añadida a favoritos",
                        Toast.LENGTH_LONG, R.style.success).show();
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void showMap() {
        findViewById(R.id.map_container).setVisibility(View.VISIBLE);
        findViewById(R.id.map_container_f).setVisibility(View.VISIBLE);
        MapsFragment mapsFragment = MapsFragment.newIntance(opcionBoton);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.map_container, mapsFragment)
                .commit();
    }

    private void views() {
        map = findViewById(R.id.map);
        asset = findViewById(R.id.text_map);
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        chatbot = findViewById(R.id.chatbot);
        map = findViewById(R.id.map);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.nav_name);
        email = findViewById(R.id.nav_email);
        profile = findViewById(R.id.profile);
        fav = findViewById(R.id.fav);
        ruta2 = findViewById(R.id.ruta2);
        ruta3 = findViewById(R.id.ruta3);
        ruta4 = findViewById(R.id.ruta4);
        ruta5 = findViewById(R.id.ruta5);
        searchRoute = findViewById(R.id.searchRoute);
        buttonBack = findViewById(R.id.buttonBack);
        btnCloseFragment = findViewById(R.id.btnCloseFragment);
        likeRuta2 = findViewById(R.id.likeRuta2);
        likeRuta3 = findViewById(R.id.likeRuta3);
        likeRuta4 = findViewById(R.id.likeRuta4);
        likeRuta5 = findViewById(R.id.likeRuta5);
    }

    private void menuDrawerOptions() {
        // Open the drawer
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        // Redirect to MainActivity
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapRuta32.this, MainActivity.class);
            }
        });

        // Redirect to ChatbotActivity
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapRuta32.this, ChatbotActivity.class);
            }
        });

        // Recreate the activity
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        // Redirect to AboutActivity
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapRuta32.this, AboutActivity.class);
            }
        });

        //Dirige a profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapRuta32.this, Profile.class);
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapRuta32.this, Favorite.class);
            }
        });

        // Show logout dialog
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void changeAppearance() {
        Drawable customDrawable = getResources().getDrawable(R.drawable.baseline_map_white);
        asset.setCompoundDrawablesWithIntrinsicBounds(customDrawable, null, null, null);
        asset.setTextColor(getResources().getColor(android.R.color.white));
        Drawable customBackground = getResources().getDrawable(R.drawable.custom_rounded_red_background);
        map.setBackground(customBackground);
    }

    private void getData() {

        RetrofitInterface apiService = RetrofitProfileInitialization.getClient().create(RetrofitInterface.class);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String id = preferences.getString("idUsuario", "");

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);

        Call<LoginResult> call = apiService.executeProfile(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    LoginResult loginResult = response.body();
                    if (loginResult != null) {
                        String userName = loginResult.getName();
                        String userEmail = loginResult.getEmail();
                        String userDirection = loginResult.getDirection();
                        String userPhone = loginResult.getPhone();

                        name.setText(userName);
                        email.setText(userEmail);
                    }
                } else {
                    // Handle error response here
                    Toast.makeText(MapRuta32.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Handle connection error here
                Toast.makeText(MapRuta32.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        dialog = new Dialog(MapRuta32.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);
        btnDialogLogout = dialog.findViewById(R.id.btnDialogLogout);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDialogLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar los datos de SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Navegar a LoginActivity
                Intent intent = new Intent(MapRuta32.this, Login.class);
                startActivity(intent);

                // Finalizar MainActivity
                finish();
                dialog.dismiss();
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        activity.startActivity(intent);
        // No finalizar la actividad actual, para permitir la navegación hacia atrás
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}