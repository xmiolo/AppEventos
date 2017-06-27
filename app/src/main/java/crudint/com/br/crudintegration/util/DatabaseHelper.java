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
            + "(" + KEY_ID + " INTEGER PRIMARY KEY, nome TEXT,data DATETIME, obs TEXT, latLng TEXT)";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO_EVENTO);

        // create new tables
        onCreate(db);
    }


    public long criaUsuario(Usuario todo, long[] eventos_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL, todo.getEmail());
        values.put(SENHA, todo.getSenha());
        values.put(NOME, todo.getNome());

        // insert row
        long usuario_id = db.insert(TABLE_USUARIO, null, values);

        // assigning tags to todo
        for (long evento_id : eventos_ids) {
            createUsuarioEvento(usuario_id, evento_id);
        }

        return usuario_id;
    }
    public long createUsuarioEvento(long usuario_id, long evento_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USUARIO_ID, usuario_id);
        values.put(KEY_EVENTO_ID, evento_id);

        long id = db.insert(TABLE_USUARIO_EVENTO, null, values);

        return id;
    }
    public Usuario getUsuario(long usuario_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USUARIO + " WHERE "
                + KEY_ID + " = " + usuario_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Usuario td = new Usuario();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setEmail((c.getString(c.getColumnIndex(EMAIL))));
        td.setNome(c.getString(c.getColumnIndex(NOME)));

        return td;
    }
}
