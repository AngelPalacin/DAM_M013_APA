package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Anuncios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAnuncios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Anuncio> listaAnuncios = obtenerAnuncios();  // Implementa el método para obtener tus anuncios

        AnuncioAdapter adapter = new AnuncioAdapter(listaAnuncios);
        recyclerView.setAdapter(adapter);
    }

    private List<Anuncio> obtenerAnuncios() {
        // Implementa la lógica para obtener tus anuncios desde tu fuente de datos
        // Puedes utilizar una lista estática como ejemplo
        List<Anuncio> anuncios = new ArrayList<>();
        anuncios.add(new Anuncio("Periodo de vacaciones en Agosto", "El gimnasio estará cerrado del 1 al 15 de agosto."));
        anuncios.add(new Anuncio("Tareas de mantenimiento de máquinas", "Del 20 al 25 de agosto realizaremos tareas de mantenimiento en nuestras máquinas."));
        anuncios.add(new Anuncio("Descuentos presenciales en el gimnasio", "Aprovecha los descuentos especiales al renovar tu membresía directamente en el gimnasio."));
        anuncios.add(new Anuncio("¡Logra tus objetivos con nuestro programa de entrenamiento personalizado!", "Consulta con nuestros entrenadores para obtener un plan adaptado a tus necesidades y metas."));
        anuncios.add(new Anuncio("¡Amplia variedad de clases grupales!", "Descubre nuestras clases de yoga, pilates, spinning y más. ¡Mejora tu bienestar y disfruta del ejercicio en grupo!"));
        anuncios.add(new Anuncio("Equipo de expertos a tu disposición", "Contamos con un equipo de entrenadores certificados y nutricionistas para ayudarte en tu viaje hacia un estilo de vida saludable."));
        anuncios.add(new Anuncio("Consulta gratuita de salud y bienestar", "Aprovecha una consulta gratuita con nuestro especialista en salud para evaluar tu estado físico y recibir recomendaciones personalizadas."));
        anuncios.add(new Anuncio("Área de relajación y recuperación", "Disfruta de nuestro espacio de spa y recuperación después de tu entrenamiento. ¡Relájate y revitaliza tu cuerpo!"));
        return anuncios;

    }

    // Clase AnuncioAdapter dentro de Anuncios.java
    private class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

        private List<Anuncio> listaAnuncios;

        public AnuncioAdapter(List<Anuncio> listaAnuncios) {
            this.listaAnuncios = listaAnuncios;
        }

        @Override
        public AnuncioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflar el diseño del elemento de anuncio (activity_anuncios.xml en este caso)
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_anuncios, parent, false);
            return new AnuncioViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AnuncioViewHolder holder, int position) {
            Anuncio anuncio = listaAnuncios.get(position);
            holder.tituloAnuncio.setText(anuncio.getTitulo());
            holder.descripcionAnuncio.setText(anuncio.getDescripcion());
        }

        @Override
        public int getItemCount() {
            return listaAnuncios.size();
        }

        public class AnuncioViewHolder extends RecyclerView.ViewHolder {
            public TextView tituloAnuncio;
            public TextView descripcionAnuncio;

            public AnuncioViewHolder(View view) {
                super(view);
                tituloAnuncio = view.findViewById(R.id.tituloAnuncio);
                descripcionAnuncio = view.findViewById(R.id.descripcionAnuncio);
            }
        }
    }

    // Clase Anuncio para representar cada elemento de la lista
    private class Anuncio {
        private String titulo;
        private String descripcion;

        public Anuncio(String titulo, String descripcion) {
            this.titulo = titulo;
            this.descripcion = descripcion;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}
