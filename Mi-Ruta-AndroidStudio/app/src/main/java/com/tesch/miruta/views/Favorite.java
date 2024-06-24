package com.tesch.miruta.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tesch.miruta.R;
import com.tesch.miruta.auth.Login;
import com.tesch.miruta.utils.LoginResult;
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.RetrofitProfileInitialization;
import com.tesch.miruta.views.favorite.RouteAdapter;
import com.tesch.miruta.views.favorite.utilities.utilities;
import com.tesch.miruta.views.favorite.entities.route;
import com.tesch.miruta.views.favorite.ConexionSQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, chatbot, map, about, logout, profile, fav;
    TextView name, email;
    Dialog dialog;
    Button btnDialogCancel, btnDialogLogout;
    TextView asset;
    ListView lvPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<route> listaUsuarios;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }

    @Override
    protected void onStart() {
        super.onStart();
        views();
        startConnection();
        getData();
        changeAppearance();
        menuDrawerOptions();
        showDialog();
    }

    //Inicia conexion
    private void startConnection() {
        conn = new ConexionSQLiteHelper(this, "bd_ruta", null, 1);

        lvPersonas = findViewById(R.id.lvPersonas);

        consultarListaRutas();
        obtenerLista();

        RouteAdapter adapter = new RouteAdapter(this, listaUsuarios, conn, getSupportFragmentManager(), findViewById(android.R.id.content));
        // Pasar referencia de la actividad Favorite
        lvPersonas.setAdapter(adapter);

        lvPersonas.setOnItemClickListener((adapterView, view, pos, l) -> {
            String informacion = "ID: " + listaUsuarios.get(pos).getId() + "\n"
                    + "id_ruta: " + listaUsuarios.get(pos).getId_ruta() + "\n"
                    + "nombre: " + listaUsuarios.get(pos).getNombre() + "\n"
                    + "origen: " + listaUsuarios.get(pos).getOrigen() + "\n"
                    + "destino: " + listaUsuarios.get(pos).getDestino() + "\n";

            StyleableToast.makeText(getApplicationContext(), informacion,
                    Toast.LENGTH_LONG, R.style.favorite_show).show();

        });
    }

    private void consultarListaRutas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        listaUsuarios = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + utilities.TABLA_RUTA, null);

        while (cursor.moveToNext()) {
            route route = new route();
            route.setId(cursor.getInt(0));
            route.setId_ruta(cursor.getString(1));
            route.setNombre(cursor.getString(2));
            route.setOrigen(cursor.getString(3));
            route.setDestino(cursor.getString(4));
            listaUsuarios.add(route);
        }
        cursor.close();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<>();

        for (route user : listaUsuarios) {
            listaInformacion.add(user.getId() + " - " + user.getNombre() + " - " + user.getOrigen());
        }
    }

    private void views() {
        about = findViewById(R.id.about);
        asset = findViewById(R.id.text_fav);
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        chatbot = findViewById(R.id.chatbot);
        map = findViewById(R.id.map);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.nav_name);
        email = findViewById(R.id.nav_email);
        fav = findViewById(R.id.fav);
        profile = findViewById(R.id.profile);
        lvPersonas = findViewById(R.id.lvPersonas);
    }

    private void changeAppearance() {
        Drawable customDrawable = getResources().getDrawable(R.drawable.baseline_favorite_white);
        asset.setCompoundDrawablesWithIntrinsicBounds(customDrawable, null, null, null);
        asset.setTextColor(getResources().getColor(android.R.color.white));
        Drawable customBackground = getResources().getDrawable(R.drawable.custom_rounded_red_background);
        fav.setBackground(customBackground);
    }

    private void menuDrawerOptions() {
        // Open the menu
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
                redirectActivity(Favorite.this, MainActivity.class);
            }
        });

        // Redirect to ChatbotActivity
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Favorite.this, ChatbotActivity.class);
            }
        });

        // Redirect to MapActivity
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Favorite.this, MapActivity.class);
            }
        });

        // Recreate the activity
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Favorite.this, AboutActivity.class);
            }
        });

        //Dirige a profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Favorite.this, Profile.class);
            }
        });
        //
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
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

    private void showDialog() {
        dialog = new Dialog(Favorite.this);
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
                Intent intent = new Intent(Favorite.this, Login.class);
                startActivity(intent);

                // Finalizar MainActivity
                finish();
                dialog.dismiss();
            }
        });
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
                    StyleableToast.makeText(getApplicationContext(), "Error en la respuesta: " + response.message(),
                            Toast.LENGTH_LONG, R.style.error).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Handle connection error here
                StyleableToast.makeText(getApplicationContext(), "Error en la respuesta: " + t.getMessage(),
                        Toast.LENGTH_LONG, R.style.error).show();
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

    //public static void redirectActivity(Activity activity, Class secondActivity) {
        //Intent intent = new Intent(activity, secondActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //activity.startActivity(intent);
        //activity.finish();
    //}

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