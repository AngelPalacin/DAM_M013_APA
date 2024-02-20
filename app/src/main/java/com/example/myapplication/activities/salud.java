package com.example.myapplication.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activities.imc_guardado;
import com.example.myapplication.adapters.DatabaseHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class salud extends AppCompatActivity {

    private RadioGroup radioGroupSexo;
    private EditText editTextAltura, editTextPeso;
    private Button buttonCalcular, buttonGuardar;
    private TextView textViewResultado;

    // Declarar un ArrayList y el ListView a nivel de clase
    private ArrayList<String> registros = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listViewRegistro;  // Declarar el ListView

    // Declarar el helper de la base de datos
    private DatabaseHelper dbHelper;
    private Button buttonverhistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);

        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        editTextAltura = findViewById(R.id.editTextAltura);
        editTextPeso = findViewById(R.id.editTextPeso);
        buttonCalcular = findViewById(R.id.buttonCalcular);
        textViewResultado = findViewById(R.id.textViewResultado);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonverhistorial = findViewById(R.id.buttonverhistorial);

        // Configurar el filtro para permitir solo dígitos y limitar la longitud a 3 (por ejemplo, "177")
        editTextAltura.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3), digitFilter});

        // Inicializar el adapter y configurar el ListView solo una vez
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registros);



        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        buttonCalcular.setOnClickListener(v -> calcularIMC());
        buttonGuardar.setOnClickListener(v -> guardarDatos());
        buttonverhistorial.setOnClickListener(v -> verHistorial()); // Agregado para abrir la nueva actividad

    }

    // Filtro para permitir solo dígitos
    private final InputFilter digitFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    private void calcularIMC() {
        int selectedId = radioGroupSexo.getCheckedRadioButtonId();

        if (selectedId == -1) {
            showToast("Seleccione el sexo");
            return;
        }

        RadioButton radioButtonSexo = findViewById(selectedId);
        String sexo = radioButtonSexo.getText().toString();

        String alturaStr = editTextAltura.getText().toString().trim();
        String pesoStr = editTextPeso.getText().toString().trim();

        if (TextUtils.isEmpty(alturaStr) || TextUtils.isEmpty(pesoStr)) {
            showToast("Ingrese la altura y el peso");
            return;
        }

        // Convertir la altura directamente a metros
        double altura = Double.parseDouble(alturaStr) / 100.0;
        double peso = Double.parseDouble(pesoStr);

        // Calcular el IMC
        double imc = calcularIMC(altura, peso);

        // Formatear el IMC con un decimal
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String imcFormateado = decimalFormat.format(imc);

        // Mostrar el IMC en el TextView
        String mensajeRecomendacion = obtenerMensajeRecomendacion(imc, sexo);
        textViewResultado.setText("Tu IMC es: " + imcFormateado + "\n\n" + mensajeRecomendacion);
    }
    private void verHistorial() {
        Intent intent = new Intent(this, imc_guardado.class);
        startActivity(intent);
    }
    private double calcularIMC(double altura, double peso) {
        // Fórmula del IMC: Peso (kg) / (altura (m) * altura (m))
        return peso / (altura * altura);
    }

    private String obtenerMensajeRecomendacion(double imc, String sexo) {
        String mensajeRecomendacion;

        if (imc > 20) {
            mensajeRecomendacion = "Te recomendamos revisar los ejercicios de cardio.";
        } else if (imc < 10) {
            mensajeRecomendacion = "Te recomendamos los ejercicios de fuerza.";
        } else {
            mensajeRecomendacion = "Tu IMC está dentro de un rango saludable.";
        }

        return mensajeRecomendacion;
    }

    private void guardarDatos() {
        String pesoString = editTextPeso.getText().toString();
        String alturaString = editTextAltura.getText().toString();

        if (TextUtils.isEmpty(pesoString) || TextUtils.isEmpty(alturaString)) {
            showToast("Ingrese la altura y el peso antes de guardar");
            return;
        }

        double peso = Double.parseDouble(pesoString);
        double altura = Double.parseDouble(alturaString) / 100.0; // Convertir a metros

        // Calcular el IMC
        double imc = calcularIMC(altura, peso);

        // Formatear el IMC con un decimal
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String imcFormateado = decimalFormat.format(imc);

        String fechaActual = obtenerFechaActual();

        // Agregar el registro al ArrayList
        registros.add("IMC: " + imcFormateado + ", Fecha: " + fechaActual);

        // Notificar al adapter que los datos han cambiado
        adapter.notifyDataSetChanged();

        // Mostrar mensaje
        showToast("Registro guardado: IMC " + imcFormateado);

        // Guardar en la base de datos
        guardarEnBaseDeDatos(imc, fechaActual);
    }

    private void guardarEnBaseDeDatos(double imc, String fecha) {
        // Obtener una referencia de escritura de la base de datos
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Crear un nuevo registro con los valores
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_IMC, imc);
        values.put(DatabaseHelper.COLUMN_FECHA, fecha);

        // Insertar el nuevo registro en la tabla de salud
        long newRowId = db.insert(DatabaseHelper.SALUD_TABLE_NAME, null, values);

        // Cerrar la conexión con la base de datos
        db.close();
    }

    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
