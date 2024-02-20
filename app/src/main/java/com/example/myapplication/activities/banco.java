package com.example.myapplication.activities;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapters.DatabaseHelper;
import com.example.myapplication.R;

public class banco extends AppCompatActivity {

    private EditText editNombre;
    private EditText editNumeroCuenta;
    private EditText editDNI;
    private EditText editApellidos;
    private EditText editDireccion;
    private EditText editTelefono;
    private EditText editCorreo;
    private Button botonEditar;
    private Button botonVolverMenu;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);

        // Inicializar vistas
        editNombre = findViewById(R.id.editNombre);
        editNumeroCuenta = findViewById(R.id.editNumeroCuenta);
        editDNI = findViewById(R.id.editDNI);
        editApellidos = findViewById(R.id.editApellidos);
        editDireccion = findViewById(R.id.editDireccion);
        editTelefono = findViewById(R.id.editTelefono);
        editCorreo = findViewById(R.id.editCorreo);
        botonEditar = findViewById(R.id.botonEditar);
        botonVolverMenu = findViewById(R.id.botonVolverMenu);

        // Inicializar el ayudante de la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener y mostrar los datos bancarios desde la base de datos
        mostrarDatosBancarios();

        // Configurar el botón de "Editar"
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar campos vacíos
                if (camposValidos()) {
                    // Validar el formato del correo
                    if (isValidEmail(editCorreo.getText().toString())) {
                        // Validar el número de teléfono
                        if (isValidPhoneNumber(editTelefono.getText().toString())) {
                            // Habilitar la edición de los EditTexts
                            enableEditing(true);

                            // Cambiar el texto del botón según si está habilitado o deshabilitado
                            botonEditar.setText("Guardar");
                            botonEditar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Validar campos vacíos nuevamente
                                    if (camposValidos()) {
                                        // Validar el formato del correo nuevamente
                                        if (isValidEmail(editCorreo.getText().toString())) {
                                            // Validar el número de teléfono nuevamente
                                            if (isValidPhoneNumber(editTelefono.getText().toString())) {
                                                // Validar el DNI antes de guardar los datos bancarios
                                                String dni = editDNI.getText().toString();
                                                if (isValidDNI(dni)) {
                                                    // Guardar los datos bancarios en la base de datos
                                                    guardarDatosBancarios();

                                                    // Deshabilitar la edición
                                                    enableEditing(false);

                                                    // Cambiar el texto del botón
                                                    botonEditar.setText("Editar");
                                                } else {
                                                    // Mostrar un mensaje de error si el DNI no es válido
                                                    Toast.makeText(banco.this, "DNI no válido", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                // Mostrar un mensaje de error si el número de teléfono no es válido
                                                Toast.makeText(banco.this, "Número de teléfono no válido", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Mostrar un mensaje de error si el formato del correo no es válido
                                            Toast.makeText(banco.this, "Formato de correo no válido", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Mostrar un mensaje de error si hay campos vacíos
                                        Toast.makeText(banco.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Mostrar un mensaje de error si el número de teléfono no es válido
                            Toast.makeText(banco.this, "Número de teléfono no válido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Mostrar un mensaje de error si el formato del correo no es válido
                        Toast.makeText(banco.this, "Formato de correo no válido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mostrar un mensaje de error si hay campos vacíos
                    Toast.makeText(banco.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón "Volver al Menú"
        botonVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finalizar la actividad actual y volver a la anterior (Inicio.java)
                finish();
            }
        });
    }

    // Método para validar campos vacíos
    private boolean camposValidos() {
        return !TextUtils.isEmpty(editNombre.getText().toString()) &&
                !TextUtils.isEmpty(editNumeroCuenta.getText().toString()) &&
                !TextUtils.isEmpty(editDNI.getText().toString()) &&
                !TextUtils.isEmpty(editApellidos.getText().toString()) &&
                !TextUtils.isEmpty(editDireccion.getText().toString()) &&
                !TextUtils.isEmpty(editTelefono.getText().toString()) &&
                !TextUtils.isEmpty(editCorreo.getText().toString());
    }

    // Método para validar el formato del correo electrónico
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para validar el número de teléfono (solo dígitos)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return TextUtils.isDigitsOnly(phoneNumber);
    }

    private void mostrarDatosBancarios() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Definir las columnas que deseas recuperar
        String[] projection = {
                DatabaseHelper.COLUMN_NOMBRE,
                DatabaseHelper.COLUMN_NUMERO_CUENTA,
                DatabaseHelper.COLUMN_DNI,
                DatabaseHelper.COLUMN_APELLIDOS,
                DatabaseHelper.COLUMN_DIRECCION,
                DatabaseHelper.COLUMN_TELEFONO,
                DatabaseHelper.COLUMN_CORREO
        };

        // Hacer la consulta
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Mover al primer resultado (si hay alguno)
        if (cursor.moveToFirst()) {
            // Obtener los datos y mostrarlos
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
            String numeroCuenta = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NUMERO_CUENTA));
            String dni = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DNI));
            String apellidos = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDOS));
            String direccion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIRECCION));
            String telefono = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONO));
            String correo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CORREO));

            editNombre.setText(nombre);
            editNumeroCuenta.setText(numeroCuenta);
            editDNI.setText(dni);
            editApellidos.setText(apellidos);
            editDireccion.setText(direccion);
            editTelefono.setText(telefono);
            editCorreo.setText(correo);
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();
    }

    private void guardarDatosBancarios() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Obtener los datos ingresados
        String nuevoNombre = editNombre.getText().toString();
        String nuevoNumeroCuenta = editNumeroCuenta.getText().toString();
        String nuevoDNI = editDNI.getText().toString();
        String nuevosApellidos = editApellidos.getText().toString();
        String nuevaDireccion = editDireccion.getText().toString();
        String nuevoTelefono = editTelefono.getText().toString();
        String nuevoCorreo = editCorreo.getText().toString();

        // Crear un nuevo conjunto de valores
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOMBRE, nuevoNombre);
        values.put(DatabaseHelper.COLUMN_NUMERO_CUENTA, nuevoNumeroCuenta);
        values.put(DatabaseHelper.COLUMN_DNI, nuevoDNI);
        values.put(DatabaseHelper.COLUMN_APELLIDOS, nuevosApellidos);
        values.put(DatabaseHelper.COLUMN_DIRECCION, nuevaDireccion);
        values.put(DatabaseHelper.COLUMN_TELEFONO, nuevoTelefono);
        values.put(DatabaseHelper.COLUMN_CORREO, nuevoCorreo);

        // Actualizar o insertar en la base de datos
        long result = db.update(
                DatabaseHelper.TABLE_NAME,
                values,
                null,
                null
        );

        if (result == 0) {
            // No se actualizó ninguna fila, entonces inserta un nuevo registro
            db.insert(DatabaseHelper.TABLE_NAME, null, values);
        }

        // Cerrar la base de datos
        db.close();
    }

    // Método para habilitar o deshabilitar la edición de los EditTexts
    private void enableEditing(boolean enable) {
        editNombre.setEnabled(enable);
        editNumeroCuenta.setEnabled(enable);
        editDNI.setEnabled(enable);
        editApellidos.setEnabled(enable);
        editDireccion.setEnabled(enable);
        editTelefono.setEnabled(enable);
        editCorreo.setEnabled(enable);
    }

    // Método para validar el DNI
    private boolean isValidDNI(String dni) {
        // Verificar que el DNI tiene 9 caracteres (8 dígitos + 1 letra)
        if (dni.length() != 9) {
            return false;
        }

        // Verificar que los primeros 8 caracteres son dígitos
        try {
            Integer.parseInt(dni.substring(0, 8));
        } catch (NumberFormatException e) {
            return false;
        }

        // Verificar que el último carácter es una letra
        char letra = dni.charAt(8);
        return Character.isLetter(letra);
    }
}
