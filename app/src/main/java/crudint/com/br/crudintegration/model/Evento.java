package crudint.com.br.crudintegration.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Gregori on 27/06/2017.
 */

public class Evento {

    private int id;
    private LatLng latLng;
    private String nome;
    private String obs;
    private String data;

    public Evento(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", latLng=" + latLng +
                ", nome='" + nome + '\'' +
                ", obs='" + obs + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
