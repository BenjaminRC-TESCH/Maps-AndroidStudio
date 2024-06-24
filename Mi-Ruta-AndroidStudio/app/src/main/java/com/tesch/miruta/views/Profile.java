package com.tesch.miruta.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.tesch.miruta.utils.URL;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {
    Button btnDialogCancel, btnDialogLogout;
    Dialog dialog;
    TextView asset;
    ImageView menu;
    DrawerLayout drawerLayout;
    LinearLayout home, chatbot, map, about, logout, profile, fav;
    CardView profile_preview, update_profile_view;
    TextView name, email, password, direction, phone, name_profile, nav_name, nav_email;
    Button changeProfile, updateProfile, cancel;
    String id;
    EditText nameedt, emailedt, passwordedt, directionedt, phoneedt;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        retrofitConfigure();
    }

    @Override
    protected void onStart() {
        super.onStart();

        views();
        loadLayouts();
        getDataNav();
        menuDrawerOptions();
        showDialog();

    }

    private void loadLayouts() {
        //activity_profile_preview
        LinearLayout contenedor = findViewById(R.id.contenedor);
        LayoutInflater inflater = LayoutInflater.from(this);
        View profilePreview = inflater.inflate(R.layout.activity_profile_preview, contenedor, false);
        contenedor.addView(profilePreview);

        //activity_profile_preview
        name = profilePreview.findViewById(R.id.name_profile_pre);
        email = profilePreview.findViewById(R.id.email_profile_pre);
        password = profilePreview.findViewById(R.id.password_profile_pre);
        direction = profilePreview.findViewById(R.id.direction_profile_pre);
        phone = profilePreview.findViewById(R.id.phone_profile_pre);
        changeProfile = profilePreview.findViewById(R.id.changeProfile);

        name_profile = findViewById(R.id.name_profile);

        //Carga los datos en el layout preview
        getDataPreView();

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete container
                contenedor.removeView(profilePreview);

                //activity_profile_preview
                LinearLayout contenedorpos = findViewById(R.id.contenedor);
                LayoutInflater inflaterpos = LayoutInflater.from(Profile.this);
                View profilePosview = inflaterpos.inflate(R.layout.activity_profile_posview, contenedorpos, false);
                contenedorpos.addView(profilePosview);

                nameedt = profilePosview.findViewById(R.id.name_profile_edt);
                emailedt = profilePosview.findViewById(R.id.email_profile_edt);
                passwordedt = profilePosview.findViewById(R.id.password_profile_edt);
                directionedt = profilePosview.findViewById(R.id.direction_profile_edt);
                phoneedt = profilePosview.findViewById(R.id.phone_profile_edt);
                updateProfile = profilePosview.findViewById(R.id.updateProfile);
                cancel = profilePosview.findViewById(R.id.cancel);

                //Carga los datos en el layout posview
                getDataPosView();

                updateProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateData();
                        contenedorpos.removeView(profilePosview);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profile = new Intent(getApplicationContext(), Profile.class);
                        startActivity(profile);
                    }
                });
            }
        });
    }

    private void getDataPreView() {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        id = preferences.getString("idUsuario", "");

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);

        Call<LoginResult> call = retrofitInterface.executeProfile(map);

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

                        name_profile.setText(userName);

                        name.setText(userName);
                        email.setText(userEmail);
                        password.setText("**********"); // No se devuelve la contraseña en la respuesta
                        direction.setText(userDirection);
                        phone.setText(userPhone);
                    }
                } else {
                    // Maneja la respuesta de error aquí
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Maneja el error de conexión aquí
            }
        });
    }

    private void getDataPosView() {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        id = preferences.getString("idUsuario", "");

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);

        Call<LoginResult> call = retrofitInterface.executeProfile(map);

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
                        password.setText("**********"); // No se devuelve la contraseña en la respuesta
                        direction.setText(userDirection);
                        phone.setText(userPhone);

                        nameedt.setText(userName);
                        emailedt.setText(userEmail);
                        passwordedt.setText(""); // No se devuelve la contraseña en la respuesta
                        directionedt.setText(userDirection);
                        phoneedt.setText(userPhone);
                    }
                } else {
                    // Maneja la respuesta de error aquí
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Maneja el error de conexión aquí
            }
        });
    }

    private void updateData() {
        String nameInput = nameedt.getText().toString().trim();
        String emailInput = emailedt.getText().toString().trim();
        String passwordInput = passwordedt.getText().toString().trim();
        String directionInput = directionedt.getText().toString().trim();
        String phoneInput = phoneedt.getText().toString().trim();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", nameInput);
        map.put("email", emailInput);
        map.put("password", passwordInput);
        map.put("direction", directionInput);
        map.put("phone", phoneInput);

        Call<Void> call = retrofitInterface.executeUpdateProfile(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Perfil Actualizado", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Error al actualizar perfil", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void retrofitConfigure() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL.URL_NODE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    private void views() {

        profile_preview = findViewById(R.id.profile_preview);
        update_profile_view = findViewById(R.id.update_profile_view);

        home = findViewById(R.id.home);
        asset = findViewById(R.id.text_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        chatbot = findViewById(R.id.chatbot);
        map = findViewById(R.id.map);
        fav = findViewById(R.id.fav);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        nav_name = findViewById(R.id.nav_name);
        nav_email = findViewById(R.id.nav_email);

    }

    private void getDataNav() {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String id = preferences.getString("idUsuario", "");

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);

        Call<LoginResult> call = retrofitInterface.executeProfile(map);

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

                        nav_name.setText(userName);
                        nav_email.setText(userEmail);

                    }
                } else {
                    // Maneja la respuesta de error aquí
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Maneja el error de conexión aquí
            }
        });
    }

    private void menuDrawerOptions() {
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
                redirectActivity(Profile.this, MainActivity.class);
            }
        });

        //Re dirige al chatbotactivity
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile.this, ChatbotActivity.class);
                //Toast.makeText(getApplicationContext(), "chatbot", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige al mapactivity
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile.this, MapActivity.class);
                //Toast.makeText(getApplicationContext(), "map", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige al aboutactivity
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile.this, AboutActivity.class);
                //Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_LONG).show();
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Profile.this, Favorite.class);
            }
        });

        //Dirige a profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //public void redirectActivity(Activity activity, Class secondActivity) {
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

    private void showDialog() {
        dialog = new Dialog(Profile.this);
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
                Intent intent = new Intent(Profile.this, Login.class);
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
