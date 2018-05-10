package com.example.toothless.monlis.Helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.toothless.monlis.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_USER = "Status";

    public static final String KEY_NAMA = "Nama";

    public static final String KEY_EMAIL = "Email";

    public static final String KEY_ALAMAT = "Alamat";

    public static final String KEY_NO_METERAN = "No_Meteran";

    public static final String KEY_NO_TELP = "No_Telp";



    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String username, String password, String status,String nama, String email,String alamat, String no_telp, String no_meteran){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_NO_TELP, no_telp);
        editor.putString(KEY_NO_METERAN, no_meteran);
        // Storing email in pref
        editor.putString(KEY_PASSWORD, password);

        editor.putString(KEY_USER, status);

        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SplashActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT, null));
        user.put(KEY_NO_TELP, pref.getString(KEY_NO_TELP, null));
        user.put(KEY_NO_METERAN, pref.getString(KEY_NO_METERAN, null));



        // user email id
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_USER, pref.getString(KEY_USER, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, SplashActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void clearData(){
        editor.clear();
        editor.commit();
    }

    public void Update_Profile(String nama, String email, String alamat,String no_telp, String no_meteran){
        editor.putString(KEY_NAMA,nama);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_ALAMAT,alamat);
        editor.putString(KEY_NO_TELP,no_telp);
        editor.putString(KEY_NO_METERAN,no_meteran);


        editor.commit();
    }





    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }




}
