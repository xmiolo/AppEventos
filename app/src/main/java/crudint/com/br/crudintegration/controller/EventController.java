package crudint.com.br.crudintegration.controller;

import android.content.Context;

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
        //System.out.println(ev);
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
}
