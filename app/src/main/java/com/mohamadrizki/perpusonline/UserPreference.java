package com.mohamadrizki.perpusonline;

import android.content.Context;
import android.content.SharedPreferences;

import com.mohamadrizki.perpusonline.model.User;

public class UserPreference {
    private static final String PREFS_NAME = "user_pref";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phone";
    private static final String IS_LOGGED_IN = "isloggedin";
    private final SharedPreferences preferences;
    UserPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setUser(User value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID, value.getId());
        editor.putString(EMAIL, value.getEmail());
        editor.putString(PHONE_NUMBER, value.getPhoneNumber());
        editor.putBoolean(IS_LOGGED_IN, value.isLoggedIn());
        editor.apply();
    }
    User getUser() {
        User model = new User();
        model.setId(preferences.getInt(ID, 0));
        model.setEmail(preferences.getString(EMAIL, ""));
        model.setPhoneNumber(preferences.getString(PHONE_NUMBER, ""));
        model.setLoggedIn(preferences.getBoolean(IS_LOGGED_IN, false));
        return model;
    }
}
