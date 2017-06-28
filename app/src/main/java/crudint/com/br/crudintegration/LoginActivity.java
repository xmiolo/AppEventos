package crudint.com.br.crudintegration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import crudint.com.br.crudintegration.controller.UsuarioController;
import crudint.com.br.crudintegration.model.Usuario;
import crudint.com.br.crudintegration.util.Session;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private Session session;
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "gregori@gregori.comgregori:123"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView txtMessage;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                auth();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

        Usuario newUser = new Usuario();
        newUser.setEmail("admin@admin.com");
        newUser.setNome("Cabeça");
        newUser.setSenha("123");

        Usuario newUser2 = new Usuario();
        newUser2.setEmail("pedro@pedro.com");
        newUser2.setNome("Fosforo");
        newUser2.setSenha("123");

        UsuarioController uController = new UsuarioController(this);
        uController.criarUsuario(newUser);
        uController.criarUsuario(newUser2);
    }

    public void auth(){
        UsuarioController uController = new UsuarioController(getApplicationContext());
        Usuario usu = uController.autenticar(mEmailView.getText().toString(),mPasswordView.getText().toString());
        session = new Session(getApplicationContext());

        session.setusename(usu.getEmail());
        session.setid(usu.getId());
        if(session.getusename().length()>=3){
            //Autenticou
            Intent intentToMap = new Intent(this, MapsActivity.class);
            startActivity(intentToMap);
        } else System.out.println("NOPS");
    }




}

