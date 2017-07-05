package crudint.com.br.crudintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToMap = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intentToMap);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        Button btEventos = (Button) findViewById(R.id.btnEventos);
        btEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToEventList = new Intent(getApplicationContext(), EventosListActivity.class);
                startActivity(intentToEventList);
            }
        });

        Button btDetalhes = (Button) findViewById(R.id.btnUsuario);
        btDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToUserDetail = new Intent(getApplicationContext(), UserDetailActivity.class);
                startActivity(intentToUserDetail);
            }
        });
    }

}
