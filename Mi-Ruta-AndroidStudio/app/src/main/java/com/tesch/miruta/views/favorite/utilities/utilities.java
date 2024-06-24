package com.tesch.miruta.views.favorite.utilities;

public class utilities {
    public static final String TABLA_RUTA = "ruta"; // Nombre de la tabla corregido
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_ID_RUTA = "id_ruta";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_ORIGEN = "origen";
    public static final String CAMPO_DESTINO = "destino"; // Corregido aquí también

    public static final String CREAR_TABLA_RUTA = "CREATE TABLE " + TABLA_RUTA + " ("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_ID_RUTA + " TEXT, "
            + CAMPO_NOMBRE + " TEXT, "
            + CAMPO_ORIGEN + " TEXT, "
            + CAMPO_DESTINO + " TEXT)";
}

