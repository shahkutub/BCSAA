package com.bcsaa.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bcsaa.model.LeaveInfo;
import com.bcsaa.model.Logged_session_data;
import com.bcsaa.model.LoginResponse;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConstant {


    public static String grouprandomRutine;
    public static String sessionId;
    public static String weekId;
    public static LeaveInfo perticipantLeaveInfo = new LeaveInfo();
    public static String activityName;
    public static String leaveAction;
    public static String baseUrl= "baseUrl";
    public static  String pdfurl;

    public static void saveLoginUserdat(Context con, Logged_session_data loginData) {
        SharedPreferences mPrefs = con.getSharedPreferences("Logged_session_data",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        prefsEditor.putString("Logged_session_data", json);
        prefsEditor.commit();

    }

    public static Logged_session_data getLoginUserdat(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("Logged_session_data",MODE_PRIVATE);
        Logged_session_data loginData = new Logged_session_data();
        Gson gson = new Gson();
        String json = mPrefs.getString("Logged_session_data", "");
        loginData = gson.fromJson(json,Logged_session_data.class);
        return loginData;
    }

    public static String getLogged_session_data(Context con){
        SharedPreferences mPrefs = con.getSharedPreferences("Logged_session_data",MODE_PRIVATE);
        Logged_session_data loginData = new Logged_session_data();
        Gson gson = new Gson();
        String json = mPrefs.getString("Logged_session_data", "");
        return json;
    }

    public static boolean isDateAfter(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "dd-MM-yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate) || date1.getTime() == startingDate.getTime())
                return true;
            else
                return false;
        }
        catch (Exception e)
        {

            return false;
        }
    }

}
