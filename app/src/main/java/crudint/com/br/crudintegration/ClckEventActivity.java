package crudint.com.br.crudintegration;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.Marker;

import crudint.com.br.crudintegration.controller.EventController;
import crudint.com.br.crudintegration.model.Evento;

/**
 * Created by Gregori on 03/07/2017.
 */

public class ClckEventActivity extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Marker marker;
    public Dialog d;
    public Button edit, del;
    private EventController eventController;

    public ClckEventActivity(Activity a) {
        super(a);
        this.c = a;
    }
    public ClckEventActivity(Activity a, Marker mark) {
        super(a);
        this.marker = mark;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_clckevent);
        edit = (Button) findViewById(R.id.btn_edit);
        del = (Button) findViewById(R.id.btn_del);
        edit.setOnClickListener(this);
        del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                eventController = new EventController(this.getContext());
                Evento eventoSelecionado = eventController.getEventByMarker(this.marker);
                Intent intent = new Intent(c, EventActivity.class);
                intent.putExtra("TELA", "dialog");

                intent.putExtra("TITLE", "");
                intent.putExtra("NOME", eventoSelecionado.getNome());
                intent.putExtra("LATLONG", eventoSelecionado.getLatLng());
                intent.putExtra("OBS", eventoSelecionado.getObs());
                intent.putExtra("ID", eventoSelecionado.getId());
                intent.putExtra("DATA", eventoSelecionado.getData());
                c.startActivity(intent);
                //c.finish();
                break;
            case R.id.btn_del:
                eventController = new EventController(this.getContext());
                Evento evento = eventController.getEventByMarker(this.marker);
                if (eventController.removeEvento(evento)){
                    Toast toast = Toast.makeText(this.getContext(), "Evento Excluído!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Falha ao Excluir Evento!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            default:
                break;
        }
        dismiss();
        Intent inte = new Intent(c, MapsActivity.class);
        c.startActivity(inte);
    }
}
