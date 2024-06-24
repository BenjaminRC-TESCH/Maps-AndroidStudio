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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.tesch.miruta.R;
import com.tesch.miruta.auth.Login;
import com.tesch.miruta.utils.LoginResult;
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.RetrofitProfileInitialization;
import com.tesch.miruta.utils.URL;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, chatbot, map, about, logout, profile, fav;
    Button btnDialogCancel, btnDialogLogout, verMapas;
    Dialog dialog;
    TextView name, email;
    TextView asset;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        views();
        getData();
        changeAppearance();
        menuDrawerOptions();
        showDialog();

        verMapas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vermapas = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(vermapas);
            }
        });
    }

    private void views(){
        home = findViewById(R.id.home);
        asset = findViewById(R.id.text_home);
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        chatbot = findViewById(R.id.chatbot);
        map = findViewById(R.id.map);
        about = findViewById(R.id.about);
        fav = findViewById(R.id.fav);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        name = findViewById(R.id.nav_name);
        email = findViewById(R.id.nav_email);
        verMapas = findViewById(R.id.verMapas);
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
                    Toast.makeText(MainActivity.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Handle connection error here
                Toast.makeText(MainActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeAppearance() {
        Drawable customDrawable = getResources().getDrawable(R.drawable.baseline_home_white);
        asset.setCompoundDrawablesWithIntrinsicBounds(customDrawable, null, null, null);
        asset.setTextColor(getResources().getColor(android.R.color.white));
        Drawable customBackground = getResources().getDrawable(R.drawable.custom_rounded_red_background);
        home.setBackground(customBackground);
    }

    private void menuDrawerOptions(){
        //Abre el menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        //Re dirige al mainactivity
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        //Re dirige al chatbotactivity
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, ChatbotActivity.class);
                //Toast.makeText(getApplicationContext(), "chatbot", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige al mapactivity
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, MapActivity.class);
                //Toast.makeText(getApplicationContext(), "map", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige al aboutactivity
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, AboutActivity.class);
                //Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_LONG).show();
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, Favorite.class);
            }
        });

        //Dirige a profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, Profile.class);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        activity.startActivity(intent);
        // No finalizar la actividad actual, para permitir la navegación hacia atrás
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();  // Finaliza la actividad actual para evitar que quede en el back stack
    }

    private void showDialog(){
        dialog = new Dialog(MainActivity.this);
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
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

                // Finalizar MainActivity
                finish();

                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}