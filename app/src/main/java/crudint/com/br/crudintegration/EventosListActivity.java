package crudint.com.br.crudintegration;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import crudint.com.br.crudintegration.controller.EventController;
import crudint.com.br.crudintegration.model.Evento;

public class EventosListActivity extends AppCompatActivity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_list);


        lv = (ListView) findViewById(R.id.listEventos);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        /*List<String> your_array_list = new ArrayList<>();
        your_array_list.add("foo");
        your_array_list.add("bar");*/

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                montarListEventos() );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                Log.e("ItenClicado", value);
                Toast toast = Toast.makeText(getApplicationContext(), "Acessando evento selecionado!", Toast.LENGTH_SHORT);
                toast.show();
                abreEvento(value);
            }
        });


    }

    public void abreEvento(String nomeEvento){
        EventController eController = new EventController(this);
        Evento even = eController.getEventByName(nomeEvento);
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("TELA", "dialog");

        intent.putExtra("TITLE", "");
        intent.putExtra("NOME", even.getNome());
        intent.putExtra("LATLONG", new LatLng(0,0));
        intent.putExtra("OBS", even.getObs());
        intent.putExtra("ID", even.getId());
        intent.putExtra("DATA", even.getData());
        startActivity(intent);
    }


    public ArrayList<String> montarListEventos(){
        EventController eController = new EventController(this);
        ArrayList<Evento> todosEventos = new ArrayList<Evento>();
        todosEventos = eController.listarEventos(this);
        ArrayList<String> nomeEventos = new ArrayList<>();
        for(Evento v : todosEventos){
            nomeEventos.add(v.getNome());
        }
        return nomeEventos;
    }
}
