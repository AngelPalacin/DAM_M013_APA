package com.example.myapplication.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.DatabaseHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class imc_guardado extends AppCompatActivity {

    private ListView listViewHistorial;
    private ArrayList<String> historialList = new ArrayList<>();
    private ArrayAdapter<String> historialAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc_guardado);

        dbHelper = new DatabaseHelper(this);

        historialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, historialList);
        listViewHistorial = findViewById(R.id.listViewHistorial);
        listViewHistorial.setAdapter(historialAdapter);
        listViewHistorial.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        cargarHistorialDesdeBaseDeDatos();

        // Agregar listener al botón para borrar elementos seleccionados
        Button btnBorrarSeleccionados = findViewById(R.id.btnBorrarSeleccionados);
        btnBorrarSeleccionados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarElementosSeleccionados();
            }
        });

        // Agregar listener al ListView para seleccionar elementos
        listViewHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí puedes implementar la lógica para manejar la selección de elementos
                // Por ejemplo, puedes resaltar el elemento seleccionado o realizar alguna acción específica
                // Puedes acceder al elemento seleccionado mediante historialList.get(position)
            }
        });
    }

    private void cargarHistorialDesdeBaseDeDatos() {
        historialList.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_IMC,
                DatabaseHelper.COLUMN_FECHA
        };

        Cursor cursor = db.query(
                DatabaseHelper.SALUD_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            double imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMC));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA));

            // Formatear el IMC con 2 decimales
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String imcFormateado = decimalFormat.format(imc);

            String historialItem = "IMC: " + imcFormateado + ", Fecha: " + fecha;
            historialList.add(historialItem);
        }

        historialAdapter.notifyDataSetChanged();

        cursor.close();
        db.close();
    }

    private void borrarElementosSeleccionados() {
        SparseBooleanArray seleccionados = listViewHistorial.getCheckedItemPositions();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = seleccionados.size() - 1; i >= 0; i--) {
            if (seleccionados.valueAt(i)) {
                int position = seleccionados.keyAt(i);
                String itemSeleccionado = historialList.get(position);

                // Obtener la fecha del elemento seleccionado
                String[] parts = itemSeleccionado.split(", Fecha: ");
                String fecha = parts[1].trim();

                // Aquí debes implementar la lógica para borrar el elemento de la base de datos
                borrarRegistro(db, fecha);

                // Luego, remover el elemento de la lista y actualizar el adapter
                historialList.remove(position);
            }
        }

        historialAdapter.notifyDataSetChanged();
        db.close();

        showToast("Elementos seleccionados borrados");
    }

    private void borrarRegistro(SQLiteDatabase db, String fecha) {
        // Eliminar el registro de la base de datos donde la fecha coincida
        db.delete(DatabaseHelper.SALUD_TABLE_NAME, DatabaseHelper.COLUMN_FECHA + "=?", new String[]{fecha});
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
