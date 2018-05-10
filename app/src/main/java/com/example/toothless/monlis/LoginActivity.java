package com.example.toothless.monlis;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.example.toothless.monlis.Helper.DatabaseHandler_Detail_Listrik;
import com.example.toothless.monlis.Helper.Detail_Listrik;
import  com.example.toothless.monlis.Helper.get_url_link;
import  com.example.toothless.monlis.Helper.UserSessionManager;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView register;
    UserSessionManager session;

    EditText username_et, password_et;
    String username,password;
    String getPassword;
    TextView forgotpass,sigup;
    String coba;

    private DatabaseHandler_Detail_Listrik databaseHelper;
    private Detail_Listrik detail_listrik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.btn_login);
        session = new UserSessionManager(getApplicationContext());
        forgotpass = (TextView) findViewById(R.id.forgotpass);
        sigup = (TextView) findViewById(R.id.signup);
        username_et = (EditText) findViewById(R.id.username);
        password_et = (EditText) findViewById(R.id.password);
        databaseHelper = new DatabaseHandler_Detail_Listrik(LoginActivity.this);
        sigup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent forget = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(forget);

            }
        });

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                username = username_et.getText().toString().toLowerCase();
                password = password_et.getText().toString().toLowerCase();
                getPassword= password;
                if (TextUtils.isEmpty(username)) {
                    Toast msg = Toast.makeText(LoginActivity.this, "Username is Empty.", Toast.LENGTH_LONG);
                    msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
                    msg.show();
                }else if (TextUtils.isEmpty(password)){
                    Toast msg = Toast.makeText(LoginActivity.this, "Password is Empty.", Toast.LENGTH_LONG);
                    msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
                    msg.show();
                }else{
                    String type = "api_login";
                    new Masuk().execute(type,username,password);
                }
            }
        });


        sigup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent forget = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(forget);

            }
        });
    }

    private class Masuk extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        AlertDialog alertDialog;
        @Override
        public String doInBackground(String... params) {
            String type = params [0];
            get_url_link link = new get_url_link();
            String login_url = link.getUrl_link(type);
            String username = params [1];
            String password = params [2];
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "username="+username+"&password="+password);
                Request request = new Request.Builder()
                        .url(login_url)
                        .post(body)
                        .addHeader("username", username)
                        .addHeader("password", password)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "1f0ab2d9-c7a8-9bd4-f729-54df41618bf4")
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String line="";
            return result;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Login Status");
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Wait a Moment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            JSONObject jObj = null;
            String reason = null;

            int status=100;
            try
            {
                jObj = new JSONObject(result);
                JSONArray jsonArray= jObj.getJSONArray("login");
                JSONObject explrObject = jsonArray.getJSONObject(0);
                status = explrObject.getInt("login_status");
                reason = explrObject.getString("reason");

            }
            catch (JSONException e)
            {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }
            if(status == -1){
                alertDialog.setMessage(reason);
                alertDialog.show();
            }else if (status == 0){
                int user_status = 100;
                try
                {
                    pDialog.dismiss();
                    JSONArray jsonArray= jObj.getJSONArray("Data");
                    JSONObject explrObject = jsonArray.getJSONObject(0);
                    user_status = explrObject.getInt("status");
                    if (user_status==1){
                        /*alertDialog.setMessage(reason);
                        alertDialog.show();*/
                    String id_user = explrObject.getString("id_user");
                    String nama = explrObject.getString("nama_user");
                    String email = explrObject.getString("email");
                    String alamat = explrObject.getString("alamat");
                    String no_telp = explrObject.getString("no_telp");
                    String no_meteran = explrObject.getString("no_meteran");
                    String id = FirebaseInstanceId.getInstance().getToken();
                    new getlist().execute(id_user,id);
                        session.createUserLoginSession(id_user,password,String.valueOf(user_status),nama,email,alamat,no_telp,no_meteran);
                        Intent Home = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(Home);
                        alertDialog.dismiss();
                        finish();

                    }else{
                            alertDialog.setMessage("Your account is in active. Please Contact Admin.");
                            alertDialog.show();
                        }
                }catch (JSONException e)
                {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }

    private class getlist extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        AlertDialog alertDialog;

        @Override
        public String doInBackground(String... params) {
            String id_user = params[0];
            String token = params[1];
            get_url_link link = new get_url_link();
            String url = link.getUrl_link("api_get_detail_listrik");
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "id_user="+id_user+"&token="+token);
                Request request = new Request.Builder()
                        .url("http://monlis.e-dokter.xyz/api_get_detail_listrik.php")
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "de7c2099-7da3-dc0b-1cb5-2d516ae2e7c3")
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jObj = null;
            try
            {
                jObj = new JSONObject(result);
                JSONArray array_data= jObj.getJSONArray("Data");
                for (int i=0;i<array_data.length();i++)
                {
                    detail_listrik = new Detail_Listrik();
                    JSONObject explrObject = array_data.getJSONObject(i);
                    String detail_id,detail_biaya,detail_energi,batas_energi,batas_biaya;
                    detail_id = explrObject.getString("id_detail");
                    detail_energi = explrObject.getString("energi_bulanan");
                    detail_biaya = explrObject.getString("biaya_bulanan");
                    batas_energi = explrObject.getString("batas_energi");
                    batas_biaya = explrObject.getString("batas_biaya");
                    detail_listrik.setId_detail(detail_id);
                    detail_listrik.setEnergi_bulanan(detail_energi);
                    detail_listrik.setBiaya_bulanan(detail_biaya);
                    detail_listrik.setBatas_energi(batas_energi);
                    detail_listrik.setBatas_biaya(batas_biaya);
                    databaseHelper.addUser(detail_listrik);

                }

            }
            catch (JSONException e)
            {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }


        }
    }



}
