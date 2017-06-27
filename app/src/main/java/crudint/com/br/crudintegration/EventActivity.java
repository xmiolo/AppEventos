package crudint.com.br.crudintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

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
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    public void saveEvent(){
        System.out.println("NOME: "+getEventName().getText()+" Data: "+getEventDate().getText());
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
