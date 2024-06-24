package com.tesch.miruta.auth;

import android.content.Intent;
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
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.URL;

import java.util.HashMap;
import java.util.regex.Pattern;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {

    private TextView welcomeText, loginText, loginTextName, loginTextEmail;
    private EditText name, email, password, confirmPassword;
    private Button signup, next;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,12}$"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        views();
        retrofitConfigure();
        visibilityName();
    }

    private void views() {
        welcomeText = findViewById(R.id.text_bienvenido);
        loginTextName = findViewById(R.id.login_name);
        loginTextEmail = findViewById(R.id.login_correo);
        loginText = findViewById(R.id.loginTextt);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signupBtn);
        next = findViewById(R.id.siguienteBtn);
        confirmPassword = findViewById(R.id.confirmPassword);
    }

    private void retrofitConfigure() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL.URL_NODE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    private void visibilityName() {
        loginTextEmail.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        confirmPassword.setVisibility(View.GONE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameInput = name.getText().toString().trim();

                if (TextUtils.isEmpty(nameInput) || !NAME_PATTERN.matcher(nameInput).matches()) {
                    StyleableToast.makeText(getApplicationContext(), "Por favor ingresa un nombre válido.",
                            Toast.LENGTH_LONG, R.style.error).show();
                } else {
                    visibilityEmail();
                }
            }
        });
    }

    private void visibilityEmail() {
        loginTextName.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        confirmPassword.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);
        loginTextEmail.setVisibility(View.VISIBLE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                String confirmPasswordInput = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(emailInput) || !isValidEmail(emailInput)) {
                    StyleableToast.makeText(getApplicationContext(), "¡Introduce un correo válido!",
                            Toast.LENGTH_LONG, R.style.error).show();

                } else if (!passwordInput.equals(confirmPasswordInput)) {
                    StyleableToast.makeText(getApplicationContext(), "¡Las contraseñas no coinciden!",
                            Toast.LENGTH_LONG, R.style.error).show();
                } else {
                    signUp();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    private void signUp() {
        String nameInput = name.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        HashMap<String, String> map = new HashMap<>();
        map.put("name", nameInput);
        map.put("email", emailInput);
        map.put("password", passwordInput);

        Call<Void> call = retrofitInterface.executeSignup(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    StyleableToast.makeText(getApplicationContext(), "Registro Exitoso",
                            Toast.LENGTH_LONG, R.style.success).show();
                    clearFields();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Este usuario ya está registrado",
                            Toast.LENGTH_LONG, R.style.error).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearFields() {
        name.setText("");
        email.setText("");
        password.setText("");
        confirmPassword.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
