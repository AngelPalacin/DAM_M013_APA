package com.example.myapplication.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activities.Exercise;

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    private SparseBooleanArray selectedItems;

    public ExerciseAdapter(Context context, List<Exercise> exercisesList) {
        super(context, 0, exercisesList);
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public int getViewTypeCount() {
        return 14; // Número de tipos de vistas en tu lista (saltar, correr, remo, MMA, natación, elíptica, escaleras, comba, bici, hombro, piernas, abdominales, espalda, bíceps)
    }

    @Override
    public int getItemViewType(int position) {
        Exercise exercise = getItem(position);

        // Devuelve el tipo de vista según el nombre del ejercicio
        if ("Saltar".equals(exercise.getName())) {
            return 0; // Tipo para Saltar
        } else if ("Correr".equals(exercise.getName())) {
            return 1; // Tipo para Correr
        } else if ("Remo".equals(exercise.getName())) {
            return 2; // Tipo para Remo
        } else if ("MMA".equals(exercise.getName())) {
            return 3; // Tipo para MMA
        } else if ("Natación".equals(exercise.getName())) {
            return 4; // Tipo para Natación
        } else if ("Eliptica".equals(exercise.getName())) {
            return 5; // Tipo para Elíptica
        } else if ("Escaleras".equals(exercise.getName())) {
            return 6; // Tipo para Escaleras
        } else if ("Comba".equals(exercise.getName())) {
            return 7; // Tipo para Comba
        } else if ("Bici".equals(exercise.getName())) {
            return 8; // Tipo para Bici
        } else if ("Hombros".equals(exercise.getName())) {
            return 9; // Tipo para Hombro
        } else if ("Piernas".equals(exercise.getName())) {
            return 10; // Tipo para Piernas
        } else if ("Abdominales".equals(exercise.getName())) {
            return 11; // Tipo para Abdominales
        } else if ("Espalda".equals(exercise.getName())) {
            return 12; // Tipo para Espalda
        } else if ("Biceps".equals(exercise.getName())) {
            return 13; // Tipo para Bíceps
        } else {
            // Puedes manejar otros casos o proporcionar un valor predeterminado
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exercise exercise = getItem(position);
        int viewType = getItemViewType(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);
            viewHolder.imageViewSaltar = convertView.findViewById(R.id.imageViewSaltar);
            viewHolder.imageViewCorrer = convertView.findViewById(R.id.imageViewCorrer);
            viewHolder.imageremo = convertView.findViewById(R.id.imageremo);
            viewHolder.imageViewMMA = convertView.findViewById(R.id.imageViewMMA);
            viewHolder.imageViewNatacion = convertView.findViewById(R.id.imageViewNatacion);
            viewHolder.imageViewEliptica = convertView.findViewById(R.id.imageViewEliptica);
            viewHolder.imageViewEscaleras = convertView.findViewById(R.id.imageViewEscaleras);
            viewHolder.imageViewComba = convertView.findViewById(R.id.imageViewComba);
            viewHolder.imageViewBici = convertView.findViewById(R.id.imageViewBicicleta);
            viewHolder.imageViewBiceps = convertView.findViewById(R.id.imageViewBiceps);
            viewHolder.imageViewHombros = convertView.findViewById(R.id.imageViewHombros);
            viewHolder.imageViewPiernas = convertView.findViewById(R.id.imageViewPiernas);
            viewHolder.imageViewAbdominales = convertView.findViewById(R.id.imageViewAbdominales);
            viewHolder.imageViewEspalda = convertView.findViewById(R.id.imageViewEspalda);

            viewHolder.exerciseDescriptionTextView = convertView.findViewById(R.id.exerciseDescriptionTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox.setChecked(selectedItems.get(position, false));
        viewHolder.exerciseDescriptionTextView.setText(exercise.getDescription());

        // Mostrar la imagen correspondiente según el tipo de vista
        viewHolder.imageViewSaltar.setVisibility(viewType == 0 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewCorrer.setVisibility(viewType == 1 ? View.VISIBLE : View.GONE);
        viewHolder.imageremo.setVisibility(viewType == 2 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewMMA.setVisibility(viewType == 3 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewNatacion.setVisibility(viewType == 4 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewEliptica.setVisibility(viewType == 5 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewEscaleras.setVisibility(viewType == 6 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewComba.setVisibility(viewType == 7 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewBici.setVisibility(viewType == 8 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewBiceps.setVisibility(viewType == 9 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewPiernas.setVisibility(viewType == 10 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewAbdominales.setVisibility(viewType == 11 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewEspalda.setVisibility(viewType == 12 ? View.VISIBLE : View.GONE);
        viewHolder.imageViewHombros.setVisibility(viewType == 13 ? View.VISIBLE : View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSelection(position, viewHolder.checkBox);
            }
        });

        return convertView;
    }

    public boolean isSelected(int position) {
        return selectedItems.get(position, false);
    }

    private void toggleSelection(int position, CheckBox checkBox) {
        boolean isSelected = selectedItems.get(position);
        if (isSelected) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        checkBox.setChecked(!isSelected);
    }

    private static class ViewHolder {
        CheckBox checkBox;
        ImageView imageViewSaltar;
        ImageView imageViewCorrer;
        ImageView imageremo;
        ImageView imageViewMMA;
        ImageView imageViewNatacion;
        ImageView imageViewEliptica;
        ImageView imageViewEscaleras;
        ImageView imageViewComba;
        ImageView imageViewBici;
        ImageView imageViewBiceps;
        ImageView imageViewHombros;
        ImageView imageViewPiernas;
        ImageView imageViewAbdominales;
        ImageView imageViewEspalda;

        TextView exerciseDescriptionTextView;
    }
}
