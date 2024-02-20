package com.example.myapplication.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class midia extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> selectedExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midia);

        // Obtener referencias a las vistas
        TextView textView = findViewById(R.id.textView);
        TextView exerciseTextView = findViewById(R.id.exerciseTextView);
        ListView selectedExercisesListView = findViewById(R.id.selectedExercisesListView);
        Button btnBorrarRegistros = findViewById(R.id.btnBorrarRegistros);

        // Inicializar la lista de ejercicios seleccionados
        selectedExercises = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, selectedExercises);

        // Configurar el adaptador para el ListView
        selectedExercisesListView.setAdapter(adapter);
        selectedExercisesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Obtener la lista de ejercicios seleccionados de SharedPreferences (puedes ajustar esto según tus necesidades)
        Set<String> savedExercisesCardio = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getStringSet("selectedExercisesCardio", new HashSet<>());
        Set<String> savedExercisesFuerza = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getStringSet("selectedExercisesFuerza", new HashSet<>());

        selectedExercises.addAll(savedExercisesCardio);
        selectedExercises.addAll(savedExercisesFuerza);
        adapter.notifyDataSetChanged();

        // Manejar clics en los elementos del ListView
        selectedExercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Manejar la selección/deselección de elementos
                String selectedExercise = adapter.getItem(position);
                if (selectedExercisesListView.isItemChecked(position)) {
                    // Agregar a la lista si se selecciona
                    if (!selectedExercises.contains(selectedExercise)) {
                        selectedExercises.add(selectedExercise);
                    }
                } else {
                    // Quitar de la lista si se deselecciona
                    selectedExercises.remove(selectedExercise);
                }
            }
        });

        // Configurar clic del botón de borrar
        btnBorrarRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una lista temporal para almacenar los ejercicios a borrar
                ArrayList<String> exercisesToRemove = new ArrayList<>();

                // Recorrer la lista de ejercicios seleccionados y agregar los que se deben borrar a la lista temporal
                for (String exercise : selectedExercises) {
                    if (selectedExercisesListView.isItemChecked(selectedExercises.indexOf(exercise))) {
                        exercisesToRemove.add(exercise);
                    }
                }

                // Eliminar los ejercicios de la lista principal
                for (String exercise : exercisesToRemove) {
                    selectedExercises.remove(exercise);
                    adapter.remove(exercise);
                }

                // Limpiar selecciones en el ListView
                selectedExercisesListView.clearChoices();
                adapter.notifyDataSetChanged();

                // Guardar la lista actualizada en SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("selectedExercisesCardio", new HashSet<>(selectedExercises));
                editor.putStringSet("selectedExercisesFuerza", new HashSet<>(selectedExercises));
                editor.apply();
            }
        });
    }
}
