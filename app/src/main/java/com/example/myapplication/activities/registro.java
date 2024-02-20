package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.DatabaseHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class registro extends AppCompatActivity {

    private EditText editTextUsuario, editTextContraseña;
    private Button buttonRegistrar, buttonIniciarSesion;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dbHelper = new DatabaseHelper(this);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);

        buttonRegistrar.setOnClickListener(v -> realizarRegistro());
        buttonIniciarSesion.setOnClickListener(v -> iniciarSesion());
    }

    private void realizarRegistro() {
        String usuario = editTextUsuario.getText().toString().trim();
        String contraseña = editTextContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contraseña)) {
            showToast("Por favor, complete todos los campos.");
            return;
        }

        // Validar el patrón de la contraseña
        if (!isValidPassword(contraseña)) {
            showToast("La contraseña debe tener al menos 8 caracteres y contener letras y números.");
            return;
        }

        // Verificar si el usuario ya existe en la base de datos
        if (dbHelper.checkUser(usuario, hashPassword(contraseña))) {
            showToast("Este usuario ya está registrado. Inicie sesión en su lugar.");
        } else {
            // Agregar el nuevo usuario a la base de datos con la contraseña hasheada
            long userId = dbHelper.addUser(usuario, hashPassword(contraseña));

            if (userId != -1) {
                showToast("Registro exitoso. Redirigiendo a Métricas...");

                // Redirigir a la actividad Métricas.java después del registro exitoso
                Intent intent = new Intent(registro.this, Inicio.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual para que el usuario no pueda volver atrás
            } else {
                showToast("Error al registrar el usuario. Inténtelo de nuevo.");
            }
        }
    }

    private boolean isValidPassword(String password) {
        // Validar que la contraseña tenga al menos 8 caracteres y contenga letras y números
        return password.length() >= 8 && password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
    }

    private void iniciarSesion() {
        String usuario = editTextUsuario.getText().toString().trim();
        String contraseña = editTextContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contraseña)) {
            showToast("Por favor, complete todos los campos.");
            return;
        }

        // Obtener la contraseña almacenada en la base de datos
        String storedPassword = dbHelper.getUserPassword(usuario);

        if (storedPassword != null && storedPassword.equals(hashPassword(contraseña))) {
            showToast("Inicio de sesión exitoso. Redirigiendo a Métricas...");

            // Redirigir a la actividad Métricas.java después del inicio de sesión exitoso
            Intent intent = new Intent(registro.this, Inicio.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual para que el usuario no pueda volver atrás
        } else {
            showToast("Credenciales incorrectas. Verifique su usuario y contraseña.");
        }
    }


    private String hashPassword(String password) {
        try {
            // Calcular el hash de la contraseña con el algoritmo SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convertir el hash a una representación hexadecimal
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
