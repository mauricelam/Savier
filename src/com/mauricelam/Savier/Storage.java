package com.mauricelam.Savier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * User: mauricelam
 * Date: 17/11/13
 * Time: 9:28 PM
 */
public class Storage {

    private SharedPreferences prefs;
    Gson gson;

    public Storage(Context context, String namespace) {
        init(context.getSharedPreferences("goals", Context.MODE_PRIVATE));
    }

    public Storage(SharedPreferences prefs) {
        init(prefs);
    }

    private void init(SharedPreferences prefs) {
        this.prefs = prefs;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Goal.class, new InterfaceAdapter<Goal>());
        this.gson = gsonBuilder.create();
    }

    public void putObject(String key, Object obj, Type type) {
        String json = gson.toJson(obj, type);
        Log.w("Savier store", json);
        prefs.edit().putString(key, json).commit();
    }

    public Object getObject(String key, Type type) {
        String json = prefs.getString(key, "");
        Log.w("Savier get", json);
        return gson.fromJson(json, type);
    }

}
