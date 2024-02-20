package com.example.myapplication.activities;

public class Exercise {
    private String name;
    private String description;
    private int imageResource; // Referencia al recurso de la imagen estática

    public Exercise(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
    }

    // Métodos getter para acceder a los atributos

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    // Método toString para obtener una representación de cadena legible

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
