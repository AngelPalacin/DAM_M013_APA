package com.example.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends CursorAdapter {

    private List<Integer> selectedRecords;

    public RecordAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        selectedRecords = new ArrayList<>();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_record_adapter, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Obtener referencias a las vistas en el diseño del elemento
        CheckBox recordCheckBox = view.findViewById(R.id.recordCheckBox);
        TextView dateTimeTextView = view.findViewById(R.id.dateTimeTextView);
        TextView activityTextView = view.findViewById(R.id.activityTextView);

        // Obtener datos del cursor
        final int recordId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR_ID));
        String dateTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FECHA_HORA));
        String activity = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVIDAD));

        // Actualizar vistas con datos del cursor
        dateTimeTextView.setText(dateTime);
        activityTextView.setText(activity);

        // Gestionar la selección del CheckBox
        recordCheckBox.setChecked(selectedRecords.contains(recordId));
        recordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordCheckBox.isChecked()) {
                    selectedRecords.add(recordId);
                } else {
                    selectedRecords.remove(Integer.valueOf(recordId));
                }
            }
        });
    }

    // Método para obtener los registros seleccionados
    public List<Integer> getSelectedRecords() {
        return selectedRecords;
    }
}
