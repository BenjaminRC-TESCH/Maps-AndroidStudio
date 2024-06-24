package com.tesch.miruta.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProfileInitialization {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL.URL_NODE) // Asegúrate de que URL.URL_NODE esté correctamente definido
                    .addConverterFactory(GsonConverterFactory.create()) // Usamos Gson para manejar JSON
                    .build();
        }
        return retrofit;
    }
}
