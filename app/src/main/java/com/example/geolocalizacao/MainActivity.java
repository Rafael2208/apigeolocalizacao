package com.example.geolocalizacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // permite conexao com a internet na thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void carregarLocalizacao(String ip) throws JSONException, IOException {

        Localizacao localizacao = ClienteGeoIP.localizar(ip);
        TextView pais = (TextView) findViewById(R.id.pais);
        pais.setText(localizacao.getCountry_name());
        TextView zone = (TextView) findViewById(R.id.area);
        zone.setText(localizacao.getTime_zone());
    }

    public void btnLocalizarOnClick(View view) {

        EditText txtIP = (EditText) findViewById(R.id.txtIP);
        String ip = txtIP.getText().toString();
        try {
            // verificacao de internet
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            }
            else {
                 carregarLocalizacao(ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EditText txtIP = (EditText) findViewById(R.id.txtIP);
                    String ip = txtIP.getText().toString();
                    // carregar localizacao
                    try {
                        carregarLocalizacao(ip);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Não funciona", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}