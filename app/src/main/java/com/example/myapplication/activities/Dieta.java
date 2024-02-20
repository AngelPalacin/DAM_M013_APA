package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Dieta extends AppCompatActivity {

    private WebView youtubeWebView;
    private int currentIndex = 0;

    private String[] videoUrls = {
            "https://www.youtube.com/watch?v=M_urt7h6CyA",
            "https://www.youtube.com/watch?v=znZax993Mxk",
            "https://www.youtube.com/watch?v=sTJDNBpQO9w"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);

        // Obtén una referencia al WebView
        youtubeWebView = findViewById(R.id.youtubeWebView);

        // Habilita JavaScript y otros ajustes en el WebView
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Agrega un WebViewClient para manejar eventos y errores
        youtubeWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Esto se llama cuando la página ha terminado de cargar
                super.onPageFinished(view, url);
                // Puedes agregar lógica adicional aquí si es necesario
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Maneja errores aquí
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        // Carga la URL del primer video de YouTube
        loadVideo();

        Button miBoton = findViewById(R.id.miBoton);
        miBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cambia al siguiente video al hacer clic en el botón
                currentIndex = (currentIndex + 1) % videoUrls.length;
                loadVideo();
            }
        });
    }

    private void loadVideo() {
        youtubeWebView.loadUrl(videoUrls[currentIndex]);
    }
}
