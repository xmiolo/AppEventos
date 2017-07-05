package crudint.com.br.crudintegration;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import crudint.com.br.crudintegration.controller.EventController;
import crudint.com.br.crudintegration.model.Evento;
import crudint.com.br.crudintegration.util.Session;

public class EventActivity extends FragmentActivity {

    private EditText eventName;
    private EditText eventDate;
    private EditText eventObs;

    private LatLng latLongEvent;
    private Button btnSalvar;
    private Evento evento;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView localEvento = (TextView) findViewById(R.id.txtLocalEvento);
        latLongEvent = (LatLng)getIntent().getExtras().getParcelable("LATLONG");
        evento = new Evento();
        localEvento.setText(getIntent().getStringExtra("TITLE"));


        eventName = (EditText) findViewById(R.id.eventName);
        eventDate = (EditText) findViewById(R.id.eventDate);
        eventObs = (EditText) findViewById(R.id.eventObs);
        if(getIntent().getStringExtra("TELA").equals("dialog")){
            evento.setObs(getIntent().getStringExtra("OBS"));
            eventObs.setText(getIntent().getStringExtra("OBS"));
            evento.setId(getIntent().getIntExtra("ID",0));
            evento.setData(getIntent().getStringExtra("DATA"));
            eventDate.setText(getIntent().getStringExtra("DATA"));
            evento.setNome(getIntent().getStringExtra("NOME"));
            eventName.setText(getIntent().getStringExtra("NOME"));
            evento.setLatLng(latLongEvent);
        }

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    public void saveEvent(){
        //evento = new Evento();
        evento.setNome(eventName.getText().toString());
        evento.setData(eventDate.getText().toString());
        evento.setLatLng(latLongEvent);
        evento.setObs(eventObs.getText().toString());
        EventController eController = new EventController(getApplicationContext());
        if(evento.getId() >0){

            boolean b = eController.atualizaEvent(evento);
            Log.e("EVENTO", "Evento"+ evento.getNome()+" - Atualizado");

            Toast toast = Toast.makeText(getApplicationContext(), "Evento Atualizado!", Toast.LENGTH_SHORT);
            toast.show();

            Log.i("S","Exiting Second Activity");

            finish();


        } else if(eController.criarEvento(evento, getApplicationContext())){
            Toast toast = Toast.makeText(getApplicationContext(), "Evento Cadastrado!", Toast.LENGTH_SHORT);
            toast.show();
            Log.e("EVENTO", "Evento"+ evento.getNome()+" - Inserido");
            finish();
        } else {
            Log.e("EVENTO", "Evento"+ evento.getNome()+" - ERROR!");
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
