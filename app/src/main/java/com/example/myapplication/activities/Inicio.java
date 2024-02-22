package com.example.myapplication.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity_juego;
import com.example.myapplication.R;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Obtener referencia a la ImageView que representa la sección de Rutinas
        ImageView rutinasImageView = findViewById(R.id.rutinasImageView);

        // Configurar el clic de la imagen de Rutinas para iniciar la actividad Rutinas
        rutinasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Rutinas
                Intent intent = new Intent(Inicio.this, Rutinas.class);
                startActivity(intent);
            }
        });

        Button atrasButton = findViewById(R.id.btnAtras);


        // Configurar el clic del botón Atras para volver a la actividad Registro
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Registro
                Intent registroIntent = new Intent(Inicio.this, registro.class);
                startActivity(registroIntent);
            }
        });


        // Obtener referencia a la ImageView que representa la sección de dieta
        ImageView dietasImageView = findViewById(R.id.dietasImageView);

        // Configurar el clic de la imagen de Dieta para iniciar la actividad Dieta
        dietasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Dieta
                Intent intent = new Intent(Inicio.this, Dieta.class);
                startActivity(intent);
            }
        });

        // Configurar el menú desplegable
        Spinner dropdownMenu = findViewById(R.id.dropdownMenu);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Menu", "Datos Bancarios", "Calendario", "Sugerencias", "Configuración", "Contacto", "Anuncios"});

        dropdownMenu.setAdapter(adapter);

        // Manejar la selección del menú desplegable
        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acciones según la opción seleccionada
                String selectedItem = (String) parentView.getItemAtPosition(position);

                // Comprobar la opción seleccionada y tomar acciones
                switch (selectedItem) {
                    case "Datos Bancarios":
                        // Si se selecciona "Datos Bancarios", iniciar la actividad bancos
                        Intent bancosIntent = new Intent(Inicio.this, banco.class);
                        startActivity(bancosIntent);
                        break;
                    case "Calendario":
                        // Si se selecciona "Calendario", iniciar la actividad Calendario
                        Intent calendarioIntent = new Intent(Inicio.this, calendario.class);
                        startActivity(calendarioIntent);
                        break;
                    case "Sugerencias":
                        // Si se selecciona "Sugerencias", iniciar la actividad Sugerencias
                        Intent sugerenciasIntent = new Intent(Inicio.this, sugerencias.class);
                        startActivity(sugerenciasIntent);
                        break;
                    case "Configuración":
                        // Si se selecciona "Configuración", iniciar la actividad ConfiguracionActivity
                        Intent configuracionIntent = new Intent(Inicio.this, configuracion.class);
                        startActivity(configuracionIntent);
                        break;
                    case "Contacto":
                        // Si se selecciona "Configuración", iniciar la actividad ConfiguracionActivity
                        Intent contactoIntent = new Intent(Inicio.this, contacto.class);
                        startActivity(contactoIntent);
                        break;
                    case "Anuncios":
                        // Si se selecciona "Anuncios", iniciar la actividad Anuncios
                        Intent anunciosIntent = new Intent(Inicio.this, Anuncios.class);
                        startActivity(anunciosIntent);
                        break;
                    default:
                        // Otras acciones según sea necesario
                        Toast.makeText(Inicio.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada
            }
        });

        // Obtener referencia al botón Música en el layout
        ImageView musicButton = findViewById(R.id.startButton);

        // Configurar el clic del botón Música para iniciar la actividad Gym
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Gym
                Intent gymIntent = new Intent(Inicio.this, gym.class);
                startActivity(gymIntent);
            }
        });


// Obtener referencia al botón "salud" en el layout
        ImageView saludButton = findViewById(R.id.saludButton);

// Configurar el clic del botón "salud" para iniciar una actividad específica (reemplaza "NombreDeLaActividad" con el nombre correcto)
        saludButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad correspondiente (reemplaza "NombreDeLaActividad" con el nombre correcto)
                Intent saludIntent = new Intent(Inicio.this, salud.class);
                startActivity(saludIntent);
            }
        });



        // Obtener referencia al botón Ver Registros en el layout
       ImageView verRegistrosButton = findViewById(R.id.verRegistrosButton);

        // Configurar el clic del botón para ver los registros
        verRegistrosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad RecordsActivity
                Intent intent = new Intent(Inicio.this, RecordsActivity.class);
                startActivity(intent);
            }
        });

        // Obtener referencia al botón Ver Midia en el layout
        ImageView verMidiaButton = findViewById(R.id.verMidiaButton);

        // Configurar el clic del botón para abrir la actividad Midia
        verMidiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Midia
                Intent midiaIntent = new Intent(Inicio.this, midia.class);
                startActivity(midiaIntent);
            }
        });

        // Obtener referencia al botón Juego en el layout
        Button juegoButton = findViewById(R.id.btnJuego);

        // Configurar el clic del botón Juego para iniciar la actividad MainActivity_juego
        juegoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad MainActivity_juego
                Intent juegoIntent = new Intent(Inicio.this, MainActivity_juego.class);
                startActivity(juegoIntent);
            }
        });
        // Obtener referencia al botón Tienda en el layout
        ImageView tiendaButton = findViewById(R.id.tienda);

// Configurar el clic del botón Tienda para iniciar la actividad Tienda
        tiendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad Tienda
                Intent tiendaIntent = new Intent(Inicio.this, tienda.class);
                startActivity(tiendaIntent);
            }
        });
    }

}
