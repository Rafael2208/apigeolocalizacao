package com.example.geolocalizacao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteGeoIP {

    private static String readStream (InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine())!= null){
                total.append(line).append('\n');
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request (String stringUrl) throws IOException {
        URL url = null;                              // ler os bytes
        HttpURLConnection urlConnection = null;
        try{
            url = new URL (stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }  finally {
            urlConnection.disconnect();
        }

    }

    public static Localizacao localizar (String ip) throws JSONException, IOException {
        String resposta = request("https://freegeoip.app/json/" + ip);
        JSONObject obj = new JSONObject(resposta);
        String code = obj.getString("country_code");
        String name = obj.getString("country_name");
        String zone = obj.getString("time_zone");

        return new Localizacao(code, name, zone);
    }
}
