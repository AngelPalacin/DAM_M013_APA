package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class sugerencias extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, phoneNumberEditText, suggestionEditText;
    private Button enviarSugerenciaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencias);

        // Obtener referencias a los elementos en el layout
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        suggestionEditText = findViewById(R.id.suggestionEditText);
        enviarSugerenciaButton = findViewById(R.id.enviarSugerenciaButton);

        // Configurar el clic del botón para enviar la sugerencia
        enviarSugerenciaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarSugerencia();
            }
        });
    }

    private void enviarSugerencia() {
        // Obtener el contenido de los campos
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String suggestion = suggestionEditText.getText().toString();

        // Aquí podrías enviar la sugerencia a un servidor o hacer cualquier otra acción necesaria

        // Mostrar mensaje de agradecimiento
        Toast.makeText(sugerencias.this, "Gracias por la sugerencia, la tomaremos en consideración", Toast.LENGTH_SHORT).show();
    }
}
