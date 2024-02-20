package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import java.util.Timer;
import java.util.TimerTask;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity_juego extends AppCompatActivity {

    public Juego juego;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_juego);
        juego = findViewById(R.id.Pantalla);

        ViewTreeObserver obs = juego.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                juego.setAncho(juego.getWidth());
                juego.setAlto(juego.getHeight());
                juego.setPosicionInicial();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        juego.posMonedaX -= 10;

                        if (juego.posMonedaX < 0) {
                            juego.invalidate();
                            juego.setPosicionInicial();
                        }

                        juego.invalidate();
                    }
                });
            }
        }, 0, 10);
    }
}
