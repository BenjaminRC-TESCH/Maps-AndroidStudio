package com.tesch.miruta.views.chatbot;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> questions;

    public Category(String name, ArrayList<String> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }
}

