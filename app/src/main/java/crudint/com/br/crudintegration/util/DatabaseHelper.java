package crudint.com.br.crudintegration.util;

/**
 * Created by Gregori on 27/06/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import crudint.com.br.crudintegration.model.Evento;
import crudint.com.br.crudintegration.model.Usuario;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eventManager";

    // Table Names
    private static final String TABLE_USUARIO = "usuarios";
    private static final String TABLE_EVENTO = "eventos";
    private static final String TABLE_USUARIO_EVENTO = "usuario_eventos";

    // Common column names
    private static final String KEY_ID = "id";
    //private static final String KEY_CREATED_AT = "nome";

    // NOTES Table - column nmaes
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String NOME = "nome";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_USUARIO_ID = "usuario_id";
    private static final String KEY_EVENTO_ID = "evento_id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + "(id INTEGER PRIMARY KEY,email TEXT, senha TEXT, nome TEXT)";

    // Tag table create statement
    private static final String CREATE_TABLE_EVENTO = "CREATE TABLE " + TABLE_EVENTO
            + "(" + KEY_ID + " INTEGER PRIMARY KEY, nome TEXT,data DATETIME, obs TEXT, latlng TEXT)";

    // todo_tag table create statement
    private static final String CREATE_TABLE_USUARUI_EVENTO = "CREATE TABLE "
            + TABLE_USUARIO_EVENTO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_USUARIO_ID + " INTEGER," + KEY_EVENTO_ID + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_EVENTO);
        db.execSQL(CREATE_TABLE_USUARUI_EVENTO);
        db.execSQL("INSERT INTO "+TABLE_USUARIO+" (id, email, senha, nome) values (1,'admin@admin.com','123','Gregori');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO_EVENTO);
        db.execSQL("INSERT INTO "+TABLE_USUARIO+" (id, email, senha, nome) values (1,'admin@admin.com','123','Gregori');");


        // create new tables
        onCreate(db);
    }


    public long criaUsuario(Usuario todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL, todo.getEmail());
        values.put(SENHA, todo.getSenha());
        values.put(NOME, todo.getNome());

        // insert row
        long usuario_id = db.insert(TABLE_USUARIO, null, values);

        /*// assigning tags to todo
        for (long evento_id : eventos_ids) {
            createUsuarioEvento(usuario_id, evento_id);
        }*/
        System.out.println("Usuario criado com sucesso: "+todo.getNome());
        return usuario_id;
    }

    /**
     * Lista todos Eventos
     * @param cont
     * @return
     */
    public ArrayList<Evento> listEventos(Context cont){
        /*String[] latlong =  "-34.8799074,174.7565664".split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);*/
        ArrayList<Evento> todos = new ArrayList<Evento>();
        String selectQuery = "SELECT  id, nome,data,obs,latlng FROM " + TABLE_EVENTO+";";

        System.out.println(selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //System.out.println("CURSOR: "+c.getPosition()+" COUNT: "+c.getColumnCount());
        // looping through all rows and adding to list
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            /*System.out.println("COLUNAS: "+c.getColumnName(0));
            System.out.println("COLUNAS: "+c.getColumnName(1));
            System.out.println("COLUNAS: "+c.getColumnName(2));
            System.out.println("COLUNAS: "+c.getColumnName(3));
            System.out.println("COLUNAS: "+c.getColumnName(4));*/
            do {
                Evento td = new Evento();
                td.setId(c.getInt(0));
                td.setNome(c.getString(1));
                td.setData(c.getString(2));
                td.setObs(c.getString(3));
                String[] latlong = c.getString(4).replace("lat/lng: (", "").replace(")","").split(",");
                System.out.println("LATLANG "+latlong + " GETSTRNG: "+c.getString(4).replace("lat/lng: (", "").replace(")",""));


                td.setLatLng(new LatLng(Double.parseDouble(latlong[0]),Double.parseDouble(latlong[1])));



                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }


    public Evento getEvent(Evento ev) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENTO + " WHERE latlng = '"+ev.getLatLng()+"';";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Evento eventoReturn = new Evento();
        if (c!= null && c.getCount() > 0 && c.moveToFirst()) {


            eventoReturn.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            eventoReturn.setNome(c.getString(c.getColumnIndex("nome")));
            eventoReturn.setObs(c.getString(c.getColumnIndex("obs")));
            eventoReturn.setData(c.getString(c.getColumnIndex("data")));
            eventoReturn.setLatLng(ev.getLatLng());

        }


        return eventoReturn;
    }
    /**
     * Insere evento no banco
     * @param evento
     * @return
     */
    public Long criaEvento(Evento evento, Context contexto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", evento.getNome());
        values.put("obs", evento.getObs());
        values.put("latlng", evento.getLatLng().toString());
        values.put("data", evento.getData());

        Long eventoId = db.insert(TABLE_EVENTO, null, values);
        Session session = new Session(contexto);

        Long l = createUsuarioEvento(session.getid(),eventoId);

        System.out.println("Evento criado com sucesso: "+evento.getNome() + " - ID "+l);
        return eventoId;
    }

    /**
     * Insere relação de evento no banco
     * @param usuario_id
     * @param evento_id
     * @return
     */
    public Long createUsuarioEvento(long usuario_id, long evento_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USUARIO_ID, usuario_id);
        values.put(KEY_EVENTO_ID, evento_id);

        Long id = db.insert(TABLE_USUARIO_EVENTO, null, values);

        return id;
    }
    public Usuario getUsuarioById(long usuario_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USUARIO + " WHERE "
                + KEY_ID + " = " + usuario_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Usuario td = new Usuario();
        if (c!= null && c.getCount() > 0 && c.moveToFirst()) {
            c.moveToFirst();

            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
            td.setNome(c.getString(c.getColumnIndex(NOME)));
        }


        return td;
    }

    public Usuario getUsuarioByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USUARIO + " WHERE "
                + EMAIL + " = '" + email+"';";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Usuario td = new Usuario();
        if (c!= null && c.getCount() > 0 && c.moveToFirst()) {
            c.moveToFirst();

            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
            td.setNome(c.getString(c.getColumnIndex(NOME)));
        }


        return td;
    }

    public Usuario autentica(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("DATABASE HELPER Autenticar");
        String selectQuery = "SELECT  * FROM " + TABLE_USUARIO + " WHERE "
                + EMAIL + " = '" + email + "' AND "+SENHA+" = '"+senha+"';";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Usuario td = new Usuario();
        if (c!= null && c.getCount() > 0 && c.moveToFirst()) {
            c.moveToFirst();
            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
            td.setNome(c.getString(c.getColumnIndex(NOME)));
        }



        return td;
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
