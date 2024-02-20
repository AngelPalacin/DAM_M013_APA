package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Rutinas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);

        // Botón Cardio
        ImageView cardioButton = findViewById(R.id.cardioButton);
        cardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones cuando se hace clic en Cardio
                // Iniciar la actividad Cardio.java
                Intent intent = new Intent(Rutinas.this, cardio.class);
                startActivity(intent);
            }
        });

        // Botón Fuerza
        ImageView fuerzaButton = findViewById(R.id.fuerzaButton);
        fuerzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones cuando se hace clic en Fuerza
                // Iniciar la actividad Fuerza.java
                Intent intent = new Intent(Rutinas.this, fuerza.class);
                startActivity(intent);
            }
        });
    }
}
