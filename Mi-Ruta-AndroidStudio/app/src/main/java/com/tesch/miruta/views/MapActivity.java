package com.tesch.miruta.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.tesch.miruta.R;
import com.tesch.miruta.auth.Login;
import com.tesch.miruta.utils.LoginResult;
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.RetrofitProfileInitialization;
import com.tesch.miruta.views.maps.MapRuta32;
import com.tesch.miruta.views.maps.MapRuta36;
import com.tesch.miruta.views.maps.MapsFragment;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, chatbot, map, about, logout, profile, fav;
    TextView name, email;
    TextView asset;
    Dialog dialog;
    Button btnDialogCancel, btnDialogLogout, btnCloseFragment;
    CardView base1, base2;
    SearchView searchRoute;
    private String opcionBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
        CardView[] cards = {base1, base2};
        String[] descriptions = {
                "Ruta 32",
                "Ruta 36"
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
        base1.setOnClickListener(new View.OnClickListener() { //Chalco la loma
            @Override
            public void onClick(View v) {
                Intent ruta32 = new Intent(getApplicationContext(), MapRuta32.class);
                startActivity(ruta32);
            }
        });

        base2.setOnClickListener(new View.OnClickListener() { //chalco san martin
            @Override
            public void onClick(View v) {
                Intent ruta36 = new Intent(getApplicationContext(), MapRuta36.class);
                startActivity(ruta36);
            }
        });
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
        searchRoute = findViewById(R.id.searchRoute);

        base1 = findViewById(R.id.base1);
        base2 = findViewById(R.id.base2);

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
                redirectActivity(MapActivity.this, MainActivity.class);
            }
        });

        // Redirect to ChatbotActivity
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapActivity.this, ChatbotActivity.class);
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
                redirectActivity(MapActivity.this, AboutActivity.class);
            }
        });

        //Dirige a profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapActivity.this, Profile.class);
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapActivity.this, Favorite.class);
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
                    Toast.makeText(MapActivity.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Handle connection error here
                Toast.makeText(MapActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        dialog = new Dialog(MapActivity.this);
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
                Intent intent = new Intent(MapActivity.this, Login.class);
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
