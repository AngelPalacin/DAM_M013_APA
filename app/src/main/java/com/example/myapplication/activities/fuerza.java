package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ExerciseAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class fuerza extends AppCompatActivity {

    private ExerciseAdapter adapter;
    private List<Exercise> exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuerza);

        exercisesList = new ArrayList<>();
        adapter = new ExerciseAdapter(this, exercisesList);

        ListView exercisesListView = findViewById(R.id.exercisesListView);
        exercisesListView.setAdapter(adapter);
        exercisesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        exercisesList.add(new Exercise("MMA", "Artes Marciales Mixtas", R.drawable.mma));
        exercisesList.add(new Exercise("Piernas", "Press de piernas", R.drawable.piernas));
        exercisesList.add(new Exercise("Abdominales", "Plancha de abdomen", R.drawable.abdominales));
        exercisesList.add(new Exercise("Espalda", "Dominadas", R.drawable.espalda));
        exercisesList.add(new Exercise("Biceps", "Curl Biceps/triceps", R.drawable.biceps));
        exercisesList.add(new Exercise("Hombros", "Levantar mancuernas", R.drawable.hombros));


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

                editor.putStringSet("selectedExercisesFuerza", selectedExercises);
                editor.apply();

                Intent intent = new Intent(fuerza.this, midia.class);
                startActivity(intent);
            }
        });
    }
}
