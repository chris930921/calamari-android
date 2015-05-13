package com.resourcelibrary.model.network.api.ceph.params;

import android.content.Context;
import android.content.SharedPreferences;

import com.resourcelibrary.model.network.api.ceph.CephParams;

/**
 * Created by User on 2/3/2015.
 */
public class LoginParams extends CephParams {
    private static final String FILE_PATH = "account_file";
    private SharedPreferences settings;

    public LoginParams(Context context) {
        this.settings = context.getSharedPreferences(FILE_PATH, Context.MODE_MULTI_PROCESS);
    }

    public void failLogin() {
        setPassword("");
    }

    @Override
    public String getName() {
        return get("name", "guest");
    }

    @Override
    public void setName(String name) {
        save("name", name);
    }

    @Override
    public String getPassword() {
        return get("password", "");
    }

    @Override
    public void setPassword(String password) {
        save("password", password);
    }

    @Override
    public String getHost() {
        return get("host", "127.0.0.1");
    }

    @Override
    public void setHost(String host) {
        save("host", host);
    }

    @Override
    public String getPort() {
        return get("port", "8008");
    }

    @Override
    public void setPort(String port) {
        save("port", port);
    }

    @Override
    public void setSession(String session) {
        save("session", session);
    }

    @Override
    public String getSession() {
        return get("session", "");
    }

    protected void save(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String get(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }
}
