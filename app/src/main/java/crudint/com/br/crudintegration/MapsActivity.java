package crudint.com.br.crudintegration;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

import crudint.com.br.crudintegration.controller.EventController;
import crudint.com.br.crudintegration.model.Evento;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private PlaceAutocompleteFragment autocompleteFragment;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity_maps_manual);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng enderecoSelecionadoLtLn = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(enderecoSelecionadoLtLn).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(enderecoSelecionadoLtLn));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(enderecoSelecionadoLtLn, 12.0f));
                openEventAct(place);
            }
            @Override
            public void onError(Status status) {
                Log.i("ERR", "Ocorreu um erro: " + status);
            }
        });


    }

    public void addMarkers(){
        EventController eController = new EventController(this);
        ArrayList<Evento> todosEventos = new ArrayList<Evento>();
        todosEventos = eController.listarEventos(this);
        for(Evento evento : todosEventos){
            mMap.addMarker(new MarkerOptions().position(evento.getLatLng()).title(evento.getNome()));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng lalg = new LatLng(-29.166709,-51.516986);
        //TODO
        //Repensar isso
        //mMap.addMarker(new MarkerOptions().position(lalg).title("Sua localização"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lalg));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lalg, 5.0f));


        this.addMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ClckEventActivity clckEvent = new ClckEventActivity(MapsActivity.this,marker);
                clckEvent.show();
                return false;
            }
        });
    }

    public void openEventAct(Place place){
        Intent intent = new Intent(getApplicationContext(), EventActivity.class);
        intent.putExtra("TELA", "maps");
        intent.putExtra("TITLE", place.getName());
        intent.putExtra("LATLONG", place.getLatLng());
        startActivity(intent);
    }
}
