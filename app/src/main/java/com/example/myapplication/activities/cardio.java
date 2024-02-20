package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapters.ExerciseAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class cardio extends AppCompatActivity {

    private ExerciseAdapter adapter;
    private List<Exercise> exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);

        exercisesList = new ArrayList<>();
        adapter = new ExerciseAdapter(this, exercisesList);

        ListView exercisesListView = findViewById(R.id.exercisesListView);
        exercisesListView.setAdapter(adapter);
        exercisesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        exercisesList.add(new Exercise("Saltar", "Sube encima de la caja", R.drawable.saltar));
        exercisesList.add(new Exercise("Correr", "Mover las piernas a la vez hacia delante", R.drawable.correr));
        exercisesList.add(new Exercise("Remo", "Práctica de remo indoor", R.drawable.remo));
        exercisesList.add(new Exercise("Natación", "Nadar en la piscina", R.drawable.natacion));
        exercisesList.add(new Exercise("Eliptica", "Ejercicio Eliptica", R.drawable.eliptica));
        exercisesList.add(new Exercise("Escaleras", "Subir escaleras", R.drawable.escaleras));
        exercisesList.add(new Exercise("Comba", "Necesitas una comba", R.drawable.comba));
        exercisesList.add(new Exercise("Bici", "Pedalea", R.drawable.bici));

        findViewById(R.id.btnGuardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                Set<String> selectedExercises = new HashSet<>();

                for (int i = 0; i < exercisesList.size(); i++) {
                    if (adapter.isSelected(i)) {
                        selectedExercises.add(exercisesList.get(i).getName());
                    }
                }

                editor.putStringSet("selectedExercisesCardio", selectedExercises);
                editor.apply();

                Intent intent = new Intent(cardio.this, midia.class);
                startActivity(intent);
            }
        });
    }
}
