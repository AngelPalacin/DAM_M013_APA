package com.example.myapplication.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapters.DatabaseHelper;
import com.example.myapplication.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class calendario extends AppCompatActivity {

    private Button selectDateTimeButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        dbHelper = new DatabaseHelper(this);

        selectDateTimeButton = findViewById(R.id.selectDateTimeButton);

        selectDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        // Crear el selector de fecha
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Crear el selector de hora
                showTimePicker(selection);
            }
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void showTimePicker(Long selectedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDate);

        // Crear el selector de hora
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTitleText("Selecciona una hora")
                .build();

        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Combinar la fecha y la hora seleccionadas
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                // Formatear la fecha y hora seleccionadas
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String selectedDateTime = sdf.format(new Date(calendar.getTimeInMillis()));

                // Mostrar el diálogo emergente para seleccionar la actividad del gimnasio
                showActivitySelectionDialog(selectedDateTime);
            }
        });

        timePicker.show(getSupportFragmentManager(), "TIME_PICKER");
    }

    private void showActivitySelectionDialog(final String selectedDateTime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una actividad");
        final String[] activities = {"Zumba", "Spinning", "Boxeo", "Otras Actividades"};

        builder.setItems(activities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones según la actividad seleccionada
                String selectedActivity = activities[which];

                // Guardar en la base de datos
                insertActivity(selectedDateTime, selectedActivity);

                // Mostrar un mensaje de éxito
                showToast("Fecha y hora seleccionadas: " + selectedDateTime +
                        "\nActividad seleccionada: " + selectedActivity);
            }
        });

        builder.show();
    }

    private void insertActivity(String selectedDateTime, String selectedActivity) {
        // Obtener una referencia a la base de datos en modo escritura
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Crear un objeto ContentValues para insertar datos en la base de datos
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FECHA_HORA, selectedDateTime);
        values.put(DatabaseHelper.COLUMN_ACTIVIDAD, selectedActivity);

        // Insertar datos en la tabla del calendario
        db.insert(DatabaseHelper.CALENDAR_TABLE_NAME, null, values);

        // Cerrar la base de datos
        db.close();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}