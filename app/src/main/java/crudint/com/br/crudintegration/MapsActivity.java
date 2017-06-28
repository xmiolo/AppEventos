package crudint.com.br.crudintegration;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import crudint.com.br.crudintegration.util.GooglePlacesAutompleteAdapter;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();


        setContentView(R.layout.activity_maps_manual);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: obter informações sobre o local selecionado.
                //s
                //System.out.println("Lugar selecionado: "+ place.getLatLng());
                LatLng enderecoSelecionadoLtLn = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(enderecoSelecionadoLtLn).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(enderecoSelecionadoLtLn));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(enderecoSelecionadoLtLn, 12.0f));
                //TODO
                //TIREI PRA CRIAR O EXEMPLO
                openEventAct(place);


            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                System.out.println("errroO@! "+status);
                //Log.i(TAG, "Ocorreu um erro: " + status);
            }
        });

/*        GooglePlacesAutompleteAdapter dataAdapter = new GooglePlacesAutompleteAdapter(MapsActivity.this, R.layout.activity_maps_manual);


        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);*/

           /* //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);

        cetEnterLocation.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                dataAdapter.getFilter().filter(s.toString());
            }
        });*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //GPSTracker gps = new GPSTracker(this);

        LatLng lalg = new LatLng(-29.166709,-51.516986);

        mMap.addMarker(new MarkerOptions().position(lalg).title("Sua localização"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lalg));
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void openEventAct(Place place){
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("LUGAR", place.getName());
        intent.putExtra("LATLONG", place.getLatLng());
        startActivity(intent);
        //startActivityForResult(intent, RESULT_OK);
        //startActivityForResults(myIntent, MY_REQUEST_CODE);
    }


}
