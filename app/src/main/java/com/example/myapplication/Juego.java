package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.activities.Inicio;

import java.util.Random;

public class Juego extends View {
    private MediaPlayer fondoMediaPlayer;
    private MediaPlayer failMediaPlayer;
    private int ancho;
    private int alto;
    private int posX;
    private int posY;
    private int radio;
    int posMonedaX;
    int posMonedaY;
    private RectF rectCesta;
    private RectF rectMoneda;
    private boolean colisionDetectada = false;
    private long tiempoColision = 0;
    private Random random = new Random();
    private int direccionMonedaRoja = 1;
    private float velocidadBolaRoja = 10;
    private int barridosCompletos = 0;

    private Bitmap bitmapNaveAmarilla;
    private Bitmap bitmapNaveRoja;

    private Context context;
    private String difficulty;
    private boolean modoFacil = false;

    private long lastCollisionTime = 0;
    private float velocidadInicialFacil = 3;
    private float velocidadInicialDificil = 10;
    private long tiempoInicioJuego;
    private long lastSpeedUpdate = 0;

    public Juego(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public Juego(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Juego(Context context, AttributeSet attrs, String difficulty) {
        super(context, attrs);
        this.context = context;
        this.difficulty = difficulty;
        modoFacil = "facil".equals(difficulty);
        init(context);
    }

    private void init(Context context) {
        bitmapNaveAmarilla = BitmapFactory.decodeResource(getResources(), R.drawable.fuerte);
        bitmapNaveRoja = BitmapFactory.decodeResource(getResources(), R.drawable.donut);

        fondoMediaPlayer = MediaPlayer.create(context, R.raw.fondo);
        fondoMediaPlayer.setLooping(true);

        failMediaPlayer = MediaPlayer.create(context, R.raw.fail);
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setPosicionInicial() {
        this.posX = this.ancho / 2;

        // Verifica si hay colisión antes de establecer la posición Y
        if (!colisionDetectada) {
            // Mantén la posición Y fija
            this.posY = this.posY;
        } else {
            // Establece la posición Y según la altura de la pantalla
            this.posY = this.alto - 50;
        }

        this.radio = 50;
        this.posMonedaY = new Random().nextInt(this.alto);
        this.posMonedaX = this.ancho + new Random().nextInt(this.ancho / 2);

        // Ajusta la velocidad según la dificultad seleccionada en la actividad anterior, esto se utilizará posteriormente en el código.
        if (modoFacil) {
            velocidadBolaRoja = velocidadInicialFacil;
        } else {
            velocidadBolaRoja = velocidadInicialDificil;
        }

        tiempoInicioJuego = SystemClock.elapsedRealtime();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (colisionDetectada && context != null) {
                    Intent intent = new Intent(context, Inicio.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                posY = (int) event.getY();
                radio = 75;
                colisionDetectada = false;
                fondoMediaPlayer.start();
                this.invalidate();
        }
        return true;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        fondoMediaPlayer.start();
        tiempoInicioJuego = SystemClock.elapsedRealtime();

        ancho = getWidth();
        alto = getHeight();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        fondoMediaPlayer.stop();
        fondoMediaPlayer.release();
        failMediaPlayer.release();

        velocidadInicialFacil = 1;
        velocidadInicialDificil = 10;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint fondoPaint = new Paint();
        Paint puntosPaint = new Paint();

        fondoPaint.setColor(Color.BLACK);
        fondoPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        puntosPaint.setTextAlign(Paint.Align.CENTER);
        puntosPaint.setTextSize(150); // Ajusta el tamaño del texto
        puntosPaint.setColor(Color.WHITE);

        canvas.drawRect(0, 0, ancho, alto, fondoPaint);

        rectCesta = new RectF(0, posY - radio, 3 * radio, posY + radio * 2); // Ajusta el tamaño del rectángulo y la posición
        // Ajusta el tamaño de la imagen de la cesta
        Bitmap scaledBitmapNaveAmarilla = Bitmap.createScaledBitmap(bitmapNaveAmarilla, (int) rectCesta.width(), (int) rectCesta.height(), true);
        canvas.drawBitmap(scaledBitmapNaveAmarilla, null, rectCesta, null);

        if (!colisionDetectada) {
            if (posMonedaX < 0) {
                posMonedaX = ancho + random.nextInt(ancho / 2);
                posMonedaY = random.nextInt(alto);
                direccionMonedaRoja = random.nextBoolean() ? 1 : -1;
                barridosCompletos++;

                // Ajusta la velocidad según la dificultad seleccionada en UsernameActivity
                if (modoFacil) {
                    velocidadBolaRoja = velocidadInicialFacil;
                } else {
                    long tiempoTranscurrido = SystemClock.elapsedRealtime() - tiempoInicioJuego;

                    if (tiempoTranscurrido > 200) {
                        float incrementoVelocidad = (float) (tiempoTranscurrido - 3000) / 1000;
                        velocidadBolaRoja = velocidadInicialDificil * (float) Math.pow(2, incrementoVelocidad);
                    } else {
                        velocidadBolaRoja = velocidadInicialDificil;
                    }
                }
            }

            posMonedaX -= velocidadBolaRoja;
            rectMoneda = new RectF(posMonedaX - radio, posMonedaY - radio, posMonedaX + radio * 2, posMonedaY + radio * 2); // Ajusta el tamaño del rectángulo y la posición
            // Ajusta el tamaño de la imagen de la moneda
            Bitmap scaledBitmapNaveRoja = Bitmap.createScaledBitmap(bitmapNaveRoja, (int) rectMoneda.width(), (int) rectMoneda.height(), true);
            canvas.drawBitmap(scaledBitmapNaveRoja, null, rectMoneda, null);

            if (RectF.intersects(rectCesta, rectMoneda)) {
                colisionDetectada = true;
                tiempoColision = SystemClock.elapsedRealtime();
                fondoMediaPlayer.pause();
                failMediaPlayer.start();

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (context != null) {
                            Intent intent = new Intent(context, Inicio.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }
                }, 2000);
            }
        } else {
            canvas.drawText("Game Over", ancho / 2, alto / 2, puntosPaint);
        }

        invalidate();
    }

    public int getAncho() {
        return 0;
    }

    public int getAlto() {
        return 0;
    }
}
