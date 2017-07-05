package crudint.com.br.crudintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import crudint.com.br.crudintegration.util.Session;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Session session = new Session(getApplicationContext());

        session.getid();

        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);

        txtNome.setText(session.getnome());
        txtEmail.setText(session.getusename());
    }
}
