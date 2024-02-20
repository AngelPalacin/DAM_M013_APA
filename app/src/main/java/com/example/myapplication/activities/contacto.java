package com.example.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class contacto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        ImageView instaImageView = findViewById(R.id.insta);
        instaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagramLink();
            }
        });

        ImageView twitterImageView = findViewById(R.id.twitter);
        twitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTwitterLink();
            }
        });

        ImageView facebookImageView = findViewById(R.id.facebook);
        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebookLink();
            }
        });
    }

    public void openInstagramLink() {
        String instagramUrl = "https://www.instagram.com/enjoy_clubs/?hl=es";
        openUrlInBrowser(instagramUrl);
    }

    public void openTwitterLink() {
        String twitterUrl = "https://twitter.com/i/flow/login?redirect_after_login=%2FEnjoyWellnessES";
        openUrlInBrowser(twitterUrl);
    }

    public void openFacebookLink() {
        String facebookUrl = "https://www.facebook.com/enjoylaribera/?locale=es_LA";
        openUrlInBrowser(facebookUrl);
    }

    private void openUrlInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void onCallButtonClick(View view) {
        String phoneNumber = "tel:+34674225934"; // Reemplaza con el número de teléfono real

        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));

        // Verifica si la aplicación de marcado está disponible antes de iniciar la actividad
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            // Manejar el caso en el que no haya ninguna aplicación de marcado disponible
            Toast.makeText(this, "No se encontró una aplicación de marcado.", Toast.LENGTH_SHORT).show();
        }
    }

}
