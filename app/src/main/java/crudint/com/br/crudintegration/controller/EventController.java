package crudint.com.br.crudintegration.controller;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import crudint.com.br.crudintegration.model.Evento;
import crudint.com.br.crudintegration.util.DatabaseHelper;

/**
 * Created by Gregori on 28/06/2017.
 */

public class EventController {

    private DatabaseHelper db;

    public EventController(Context context){
        this.db = new DatabaseHelper(context);
    }

    public boolean criarEvento(Evento ev, Context context){
        Long l = db.criaEvento(ev, context);
        if(l != null){
            return true;
        } return false;
    }

    /**
     * Listar Eventos
     * @param context
     * @return
     */
    public ArrayList<Evento> listarEventos(Context context){
        return db.listEventos(context);
    }

    /**
     * Busca evento a partir de uma marker
     * @param mark
     * @return
     */
    public Evento getEventByMarker(Marker mark){
        Evento ev = new Evento();
        ev.setLatLng(mark.getPosition());
        return db.getEvent(ev);
    }

    /**
     * Atualiza evento
     */
    public boolean atualizaEvent(Evento evento){
        return db.atualizaEvento(evento);
    }

    public boolean removeEvento(Evento evento){
        return db.removeEvento(evento);
    }
}
