package com.example.geolocalizacao;

public class Localizacao {
    // {"ip":"45.71.163.28","country_code":"BR","country_name":"","region_code":"SP","region_name":"","city":"","zip_code":"09300","time_zone":"America/Sao_Paulo","latitude":-23.6799,"longitude":-46.4571,"metro_code":0}
    private String country_code;
    private String country_name;
    private String time_zone;

    public Localizacao (String code, String name, String zone){
        this.country_code = code;
        this.country_name = name;
        this.time_zone = zone;
    }

    public String  getCountry_code() {return this.country_code;}
    public String  getCountry_name() {return this.country_name;}
    public String  getTime_zone() {return this.time_zone;}
}
