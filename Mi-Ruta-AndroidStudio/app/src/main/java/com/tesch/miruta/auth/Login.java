package com.tesch.miruta.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tesch.miruta.R;
import com.tesch.miruta.utils.LoginResult;
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.URL;
import com.tesch.miruta.views.MainActivity;

import java.util.HashMap;
import java.util.regex.Pattern;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private TextView signupText;
    private EditText email, password;
    private Button login;
    private RetrofitInterface retrofitInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.URL_NODE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Initialize views
        signupText = findViewById(R.id.signupText);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);

        // Set click listeners
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginUser();

                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loginUser() {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailInput) || !isValidEmail(emailInput)) {
            StyleableToast.makeText(getApplicationContext(), "Introduce un correo valido",
                    Toast.LENGTH_LONG, R.style.error).show();
            return;
        }else if(TextUtils.isEmpty(passwordInput)){
            StyleableToast.makeText(getApplicationContext(), "Introduce una contraseña valida",
                    Toast.LENGTH_LONG, R.style.error).show();
            return;
        }

        // Construct the HashMap
        HashMap<String, String> map = new HashMap<>();
        map.put("email", emailInput);
        map.put("password", passwordInput);

        // Call executeLogin with the HashMap
        Call<LoginResult> call = retrofitInterface.executeLogin(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {

                    // Obtiene el objeto LoginResult de la respuesta
                    LoginResult loginResult = response.body();

                    // Ahora puedes acceder a las propiedades del objeto LoginResult
                    String userId = loginResult.getId();
                    String userName = loginResult.getName();
                    String userEmail = loginResult.getEmail();

                    saveData(userId);

                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                    //Toast.makeText(Login.this, userId, Toast.LENGTH_LONG).show();
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Error al iniciar",
                            Toast.LENGTH_LONG, R.style.error).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Error de conexión al servidor",
                        Toast.LENGTH_LONG, R.style.error).show();
            }
        });
    }

    private void saveData(String id){
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String idUser = id;
        SharedPreferences.Editor obj_editor = preferences.edit();
        obj_editor.putString("idUsuario", idUser);
        obj_editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(Login.this, Login.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        //finish();  // Finaliza la actividad actual para evitar que quede en el back stack
        finishAffinity();
    }
}
