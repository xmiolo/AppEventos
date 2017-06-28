package crudint.com.br.crudintegration;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import crudint.com.br.crudintegration.controller.EventController;
import crudint.com.br.crudintegration.model.Evento;
import crudint.com.br.crudintegration.util.Session;

public class EventActivity extends AppCompatActivity {

    private EditText eventName;
    private EditText eventDate;
    private EditText eventObs;
    private LatLng latLongEvent;
    private Button btnSalvar;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView localEvento = (TextView) findViewById(R.id.txtLocalEvento);
        latLongEvent = (LatLng)getIntent().getExtras().getParcelable("LATLONG");
        System.out.println(latLongEvent.toString());

        localEvento.setText(getIntent().getStringExtra("LUGAR"));

        eventName = (EditText) findViewById(R.id.eventName);
        eventDate = (EditText) findViewById(R.id.eventDate);
        eventObs = (EditText) findViewById(R.id.eventObs);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    public void saveEvent(){
        Evento evento = new Evento();
        evento.setNome(eventName.getText().toString());
        evento.setData(eventDate.getText().toString());
        evento.setLatLng(latLongEvent);
        evento.setObs(eventObs.getText().toString());
        EventController eController = new EventController(getApplicationContext());
        if(eController.criarEvento(evento, getApplicationContext())){
            /*Intent mess = new Intent();
            // TODO APRIMORAR
            mess.putExtra("NOMEEVENTO", evento.getNome());
            setResult(MapsActivity.RESULT_OK, mess);*/
            finish();
        } else {
            System.out.println("OPA");
        }

    }

    public EditText getEventName() {
        return eventName;
    }

    public EditText getEventDate() {
        return eventDate;
    }

    public EditText getEventObs() {
        return eventObs;
    }

    public void setEventName(EditText eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(EditText eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventObs(EditText eventObs) {
        this.eventObs = eventObs;
    }
}
