package crudint.com.br.crudintegration.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import crudint.com.br.crudintegration.model.Usuario;
import crudint.com.br.crudintegration.util.DatabaseHelper;

/**
 * Created by Gregori on 27/06/2017.
 */

public class UsuarioController {

    private DatabaseHelper db;

    public UsuarioController(Context context){
        this.db = new DatabaseHelper(context);
    }

    /**
     * Autentica usuario na aplicação
     * @param email
     * @param senha
     * @return
     */
    public Usuario autenticar(String email, String senha){
        return db.autentica(email, senha);
    }

    /**
     * Salva novo usuário no banco de dados
     * @param us
     */
    public void criarUsuario(Usuario us){
        db.criaUsuario(us);
    }

    /**
     * Busca usuário por email
     * @param email
     * @return
     */
    public Usuario buscaUsuarioByEmail(String email){
        return db.getUsuarioByEmail(email);
    }


}
