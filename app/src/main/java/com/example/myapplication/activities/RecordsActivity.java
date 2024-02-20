package com.example.myapplication.activities;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.DatabaseHelper;
import com.example.myapplication.adapters.RecordAdapter;

public class RecordsActivity extends AppCompatActivity {

    private RecordAdapter recordAdapter;
    private ListView recordsListView;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recordsListView = findViewById(R.id.recordsListView);
        deleteButton = findViewById(R.id.borrarRegistrosButton);

        // Configurar el ListView con el RecordAdapter
        recordAdapter = new RecordAdapter(this, null);
        recordsListView.setAdapter(recordAdapter);

        // Mostrar los registros almacenados en la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        showRecords(dbHelper.getReadableDatabase());

        // Configurar el clic del botón de borrado
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedRecords();
            }
        });
    }

    private void showRecords(SQLiteDatabase db) {
        Cursor cursor = db.query(DatabaseHelper.CALENDAR_TABLE_NAME, null, null, null, null, null, null);

        // Cerrar el Cursor anterior si ya había uno
        if (recordAdapter.getCursor() != null) {
            recordAdapter.getCursor().close();
        }

        // Cambiar el Cursor del adapter
        recordAdapter.changeCursor(cursor);
    }

    private void deleteSelectedRecords() {
        // Obtener los registros seleccionados
        for (Integer recordId : recordAdapter.getSelectedRecords()) {
            // Eliminar el registro de la base de datos
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            int rowsAffected = db.delete(
                    DatabaseHelper.CALENDAR_TABLE_NAME,
                    DatabaseHelper.COLUMN_CALENDAR_ID + " = ?",
                    new String[]{String.valueOf(recordId)});

            if (rowsAffected > 0) {
                // Registro eliminado exitosamente
                Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                // Error al eliminar el registro
                Toast.makeText(this, "Error al eliminar el registro", Toast.LENGTH_SHORT).show();
            }

            db.close();
        }

        // Actualizar la lista después de la eliminación
        showRecords(new DatabaseHelper(this).getReadableDatabase());
    }
}
