package com.tesch.miruta.views.chatbot;

import java.util.List;

public class Categoria {
    private String nombre;
    private List<String> preguntas;

    public Categoria(String nombre, List<String> preguntas) {
        this.nombre = nombre;
        this.preguntas = preguntas;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getPreguntas() {
        return preguntas;
    }
}