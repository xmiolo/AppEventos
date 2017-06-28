package crudint.com.br.crudintegration.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Gregori on 26/06/2017.
 */

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setid(int id){
        prefs.edit().putInt("id", id).commit();
    }

    public int getid(){
        int id = prefs.getInt("id",0);
        return id;
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
       // prefsCommit();
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}
