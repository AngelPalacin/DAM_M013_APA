package com.example.myapplication.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String songPath;
    private ArrayList<String> songList;
    private int currentPosition;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        // Obtener la ruta de la canción y la lista de canciones desde el Intent
        songPath = getIntent().getStringExtra("SONG_PATH");
        songList = getIntent().getStringArrayListExtra("SONG_LIST");
        currentPosition = getIntent().getIntExtra("POSITION", 0);

        // Inicializar el reproductor de música
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configurar botones y SeekBar
        Button playPauseButton = findViewById(R.id.playPauseButton);
        Button stopButton = findViewById(R.id.stopButton);
        Button nextButton = findViewById(R.id.nextButton);
        Button prevButton = findViewById(R.id.prevButton);
        Button forwardButton = findViewById(R.id.forwardButton);
        Button backwardButton = findViewById(R.id.backwardButton);

        SeekBar seekBar = findViewById(R.id.seekBar);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a MainActivity y limpiar la pila de actividades
                Intent intent = new Intent(MusicPlayerActivity.this, gym.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevSong();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward();
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backward();
            }
        });

        // Configurar SeekBar para actualizar la posición de reproducción
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No se necesita implementación aquí
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No se necesita implementación aquí
            }
        });

        // Actualizar la posición de la SeekBar en un hilo separado
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            final int currentPosition = mediaPlayer.getCurrentPosition();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    seekBar.setProgress(currentPosition);
                                }
                            });
                        }
                        Thread.sleep(1000);
                    }
                } catch (IllegalStateException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        } else {
            if (isPaused) {
                mediaPlayer.start();
                isPaused = false;
            } else {
                playSong(currentPosition);
            }
        }
    }

    private void stop() {
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        isPaused = false;
    }

    private void nextSong() {
        currentPosition = (currentPosition + 1) % songList.size();
        playSong(currentPosition);
    }

    private void prevSong() {
        currentPosition = (currentPosition - 1 + songList.size()) % songList.size();
        playSong(currentPosition);
    }

    private void forward() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        if (currentPosition + 10000 < duration) {
            mediaPlayer.seekTo(currentPosition + 10000);
        } else {
            stop();
        }
    }

    private void backward() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        if (currentPosition - 10000 > 0) {
            mediaPlayer.seekTo(currentPosition - 10000);
        } else {
            mediaPlayer.seekTo(0);
        }
    }

    private void playSong(int position) {
        songPath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + songList.get(position);

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(songPath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
