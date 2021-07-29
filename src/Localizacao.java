

import java.io.Serializable;


public class Localizacao implements Serializable {
    private String regiao;
    private double latitude;
    private double longitude;

    public static ST2<String, Integer> regioes = new ST2<>();

    public Localizacao(String regiao, double latitude, double longitude) {
        this.regiao = regiao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRegiao() {
        return regiao;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "{Localizacao: " +
                "',\n    latitude: '" + latitude +
                "',\n    longitude: '" + longitude +
                "',\n    regiao: '" + regiao +
                "'\n}\n";
    }
}