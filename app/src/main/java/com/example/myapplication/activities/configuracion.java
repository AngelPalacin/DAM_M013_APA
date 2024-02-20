package com.example.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class configuracion extends AppCompatActivity {

    private Switch notificacionesSwitch;
    private Switch newsletterSwitch;
    private Button guardarConfiguracionButton;
    private Button enlacePiscinasButton;
    private Button enlaceGDPRButton;
    private TextView enlacePiscinas;
    private TextView enlaceGDPR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        // Obtener referencias a los elementos en el layout
        notificacionesSwitch = findViewById(R.id.notificacionesSwitch);
        newsletterSwitch = findViewById(R.id.newsletterSwitch);
        guardarConfiguracionButton = findViewById(R.id.guardarConfiguracionButton);
        enlacePiscinasButton = findViewById(R.id.enlacePiscinas);
        enlaceGDPRButton = findViewById(R.id.enlaceGDPR);

        // Configurar el clic del botón para guardar la configuración
        guardarConfiguracionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarConfiguracion();
            }
        });

        // Agregar un Listener al Switch de la Newsletter
        newsletterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Si el Switch está marcado, mostrar el mensaje
                Toast.makeText(configuracion.this,
                        "Le llegarán las novedades al correo con el que inició sesión",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el clic del botón para el enlace a piscinas
        enlacePiscinasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEnlace("https://www.boe.es/buscar/pdf/2013/BOE-A-2013-10580-consolidado.pdf");
            }
        });

        // Configurar el clic del botón para el enlace a GDPR
        enlaceGDPRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEnlace("https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679");
            }
        });
    }

    private void guardarConfiguracion() {
        // Obtener el estado de los Switches (notificaciones y newsletter)
        boolean notificacionesHabilitadas = notificacionesSwitch.isChecked();
        boolean newsletterHabilitada = newsletterSwitch.isChecked();

        // Aquí puedes guardar la configuración en SharedPreferences o en tu base de datos

        // Mostrar mensaje de éxito
        Toast.makeText(configuracion.this, "Configuración guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void abrirEnlace(String url) {
        // Abrir el enlace en el navegador web
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
