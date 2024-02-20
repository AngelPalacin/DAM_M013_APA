package com.example.myapplication.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class gym extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final long METADATA_DISPLAY_DURATION = 3500;

    private TextView albumTextView;
    private TextView artistTextView;
    private TextView durationTextView;

    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        // Verificar y solicitar permisos de almacenamiento en tiempo de ejecución
        requestStoragePermissions();

        // Configurar el ListView con las canciones
        setupListView();

        // Obtener referencias a los TextViews en el layout
        albumTextView = findViewById(R.id.albumTextView);
        artistTextView = findViewById(R.id.artistTextView);
        durationTextView = findViewById(R.id.durationTextView);
    }

    private void setupListView() {
        // Obtener la lista de canciones en la carpeta "Download"
        ArrayList<String> songList = getSongList();

        // Configurar el ListView con las canciones
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        listView.setAdapter(adapter);

        // Configurar el clic en un elemento de la lista para abrir el reproductor de música o mostrar metadatos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastClickTime < METADATA_DISPLAY_DURATION) {
                    // Segundo clic dentro del intervalo de tiempo
                    onItemDoubleClick(position, songList);
                } else {
                    // Primer clic
                    lastClickTime = currentTime;
                    // Mostrar metadatos al hacer clic en una canción
                    showMetadata(songList.get(position));
                }
            }
        });
    }

    private void onItemDoubleClick(int position, ArrayList<String> songList) {
        // Obtener la ubicación completa de la canción seleccionada
        String songPath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + songList.get(position);

        // Abrir la actividad MusicPlayerActivity al hacer doble clic en una canción
        Intent intent = new Intent(gym.this, MusicPlayerActivity.class);
        intent.putExtra("SONG_PATH", songPath);
        intent.putStringArrayListExtra("SONG_LIST", songList);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

    private void showMetadata(final String songFileName) {
        // Extraer metadatos de la canción seleccionada y mostrar en TextViews
        extractMetadata(songFileName);

        // Retrasar la transición a la actividad de reproducción después de mostrar metadatos
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Obtener la ubicación completa de la canción seleccionada
                String songPath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + songFileName;

                // Abrir la actividad MusicPlayerActivity después de mostrar metadatos
                Intent intent = new Intent(gym.this, MusicPlayerActivity.class);
                intent.putExtra("SONG_PATH", songPath);
                intent.putStringArrayListExtra("SONG_LIST", getSongList());
                intent.putExtra("POSITION", getSongList().indexOf(songFileName));
                startActivity(intent);
            }
        }, METADATA_DISPLAY_DURATION);
    }

    private void extractMetadata(String songFileName) {
        // Extraer metadatos de la canción seleccionada y mostrar en TextViews
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            String songPath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + songFileName;
            retriever.setDataSource(songPath);

            // Extraer metadatos
            String albumName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String durationString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            // Obtener la carátula del álbum como un array de bytes
            byte[] albumArt = retriever.getEmbeddedPicture();

            // Convertir la duración a minutos
            long durationMillis = Long.parseLong(durationString);
            long durationMinutes = durationMillis / 60000;

            // Actualizar los TextViews con la información de los metadatos
            albumTextView.setText("Album: " + albumName);
            artistTextView.setText("Artist: " + artist);
            durationTextView.setText("Duration: " + durationMinutes + " minutes");

            // Mostrar la carátula del álbum si está disponible
            if (albumArt != null) {
                // Convertir los bytes a un Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(albumArt, 0, albumArt.length);

                // Mostrar el Bitmap en la ImageView
                ImageView albumArtImageView = findViewById(R.id.albumArtImageView);
                albumArtImageView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Liberar los recursos del MediaMetadataRetriever si la versión de Android es 10 o superior
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                try {
                    retriever.release();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void requestStoragePermissions() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permiso ya otorgado, procede con la lógica
                setupListView();
            } else {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE);
            }
        }
    }

    private ArrayList<String> getSongList() {
        ArrayList<String> songs = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getPath() + "/Download";
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".mp3")) {
                        songs.add(file.getName());
                    }
                }
            } else {
                Toast.makeText(this, "No se pueden listar archivos en el directorio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El directorio no existe", Toast.LENGTH_SHORT).show();
        }

        return songs;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            boolean allPermissionsGranted = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                setupListView();
            } else {
                Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
