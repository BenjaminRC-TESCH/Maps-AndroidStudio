package com.tesch.miruta.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tesch.miruta.auth.Login;
import com.tesch.miruta.utils.LoginResult;
import com.tesch.miruta.utils.URL;
import com.tesch.miruta.views.chatbot.MessageResult;
import com.tesch.miruta.R;
import com.tesch.miruta.views.chatbot.RecyclerAdapter;
import com.tesch.miruta.utils.RetrofitInterface;
import com.tesch.miruta.utils.RetrofitProfileInitialization;
import com.tesch.miruta.views.chatbot.RetrofitChatInitialization;
import com.tesch.miruta.views.maps.MapsFragment;
import com.tesch.miruta.views.chatbot.Category;
import com.tesch.miruta.views.chatbot.CategoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatbotActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, chatbot, map, about, logout, profile, fav;
    TextView name, email;
    TextView asset;
    Dialog dialog;
    Button btnDialogCancel, btnDialogLogout;
    Button btnCloseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        WebView myWebView = findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable debugging
        WebView.setWebContentsDebuggingEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Optionally, you can add some logs to confirm page load
                Log.d("WebView", "Page loaded: " + url);
                Toast.makeText(getApplicationContext(),"Error" + url.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Log error details
                Log.e("WebView", "Error loading page: " + error.getDescription());
                Toast.makeText(getApplicationContext(),"Error" + error.getDescription().toString(), Toast.LENGTH_LONG).show();
            }
        });

        myWebView.loadUrl(URL.URL_FLASK);
    }


    @Override
    protected void onStart() {
        super.onStart();
        views();
        getData();
        changeAppearance();
        menuDrawerOptions();
        showDialog();
    }

    private void views(){
        chatbot = findViewById(R.id.chatbot);
        asset = findViewById(R.id.text_chatbot);
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        chatbot = findViewById(R.id.chatbot);
        map = findViewById(R.id.map);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.nav_name);
        email = findViewById(R.id.nav_email);
        fav = findViewById(R.id.fav);
        profile = findViewById(R.id.profile);

        btnCloseFragment = findViewById(R.id.btnCloseFragment);
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
                redirectActivity(ChatbotActivity.this, MainActivity.class);
            }
        });

        //Re dirige al chatbotactivity
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                //Toast.makeText(getApplicationContext(), "chatbot", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige al mapactivity
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChatbotActivity.this, MapActivity.class);
                //Toast.makeText(getApplicationContext(), "map", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige al aboutactivity
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChatbotActivity.this, AboutActivity.class);
                //Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_LONG).show();
            }
        });

        //Re dirige a favoritos
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChatbotActivity.this, Favorite.class);
            }
        });

        //Dirige a profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChatbotActivity.this, Profile.class);
            }
        });

        //Re dirige al login
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void changeAppearance() {
        Drawable customDrawable = getResources().getDrawable(R.drawable.baseline_chatbot_white);
        asset.setCompoundDrawablesWithIntrinsicBounds(customDrawable, null, null, null);
        asset.setTextColor(getResources().getColor(android.R.color.white));
        Drawable customBackground = getResources().getDrawable(R.drawable.custom_rounded_red_background);
        chatbot.setBackground(customBackground);
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
    }

    private void showDialog(){
        dialog = new Dialog(ChatbotActivity.this);
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
                Intent intent = new Intent(ChatbotActivity.this, Login.class);
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
                    Toast.makeText(ChatbotActivity.this, "Error en la respuesta: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                // Handle connection error here
                Toast.makeText(ChatbotActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}