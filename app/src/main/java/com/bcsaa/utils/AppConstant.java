package com.bcsaa.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bcsaa.model.Logged_session_data;
import com.bcsaa.model.LoginResponse;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class AppConstant {


    public static String grouprandomRutine;

    public static void saveLoginUserdat(Context con, Logged_session_data loginData) {
        SharedPreferences mPrefs = con.getSharedPreferences("Logged_session_data",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        prefsEditor.putString("Logged_session_data", json);
        prefsEditor.commit();

    }

//    public static LoginResponse getLoginUserdat(Context con){
//        SharedPreferences mPrefs = con.getSharedPreferences("loginUserdata",MODE_PRIVATE);
//        LoginResponse loginData = new LoginResponse();
//        Gson gson = new Gson();
//        String json = mPrefs.getString("loginUserdata", "");
//        loginData = gson.fromJson(json,LoginResponse.class);
//        return loginData;
//    }

    public static String getLogged_session_data(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("Logged_session_data",MODE_PRIVATE);
        Logged_session_data loginData = new Logged_session_data();
        Gson gson = new Gson();
        String json = mPrefs.getString("Logged_session_data", "");
        return json;
    }

}