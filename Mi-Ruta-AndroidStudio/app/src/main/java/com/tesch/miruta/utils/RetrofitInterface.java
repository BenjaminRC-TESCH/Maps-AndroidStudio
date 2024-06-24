package com.tesch.miruta.utils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/auth/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/auth/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/auth/profile")
    Call<LoginResult> executeProfile (@Body HashMap<String, String> map);

    @POST("/auth/updateProfile")
    Call<Void> executeUpdateProfile (@Body HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/pregunta")
    Call<HashMap<String, String>> getRespuesta(@Field("pregunta") String pregunta);

    @GET("/preguntas")
    Call<List<String>> getPreguntas();

}
