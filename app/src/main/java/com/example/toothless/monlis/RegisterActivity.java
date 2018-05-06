package com.example.toothless.monlis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toothless.monlis.Helper.get_url_link;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    Button signup;
    //UserSessionManager session;
    EditText nama_et, username_et, email_et, password_et,con_password_et, no_meteran_et, gol_listrik_et;
    TextView login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    get_url_link link = new get_url_link();
    String url = link.getUrl_link("get_datalistrik");
    Spinner spinner_dataListrik;
    ProgressDialog pDialog;
    List<Data> listDataListrik = new ArrayList<Data>();
    private static final String TAG = RegisterActivity.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_PENDIDIKAN = "pendidikan";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = (Button) findViewById(R.id.btn_signup);
        login = (TextView) findViewById(R.id.signin);
        nama_et = (EditText) findViewById(R.id.nama);
        username_et = (EditText) findViewById(R.id.username);
        email_et = (EditText) findViewById(R.id.email);
        password_et = (EditText) findViewById(R.id.password);
        con_password_et = (EditText) findViewById(R.id.confirm_password);
        no_meteran_et = (EditText) findViewById(R.id.no_meteran);
        gol_listrik_et = (EditText) findViewById(R.id.gol_listrik);
        final TextInputLayout nama_lay = (TextInputLayout) findViewById(R.id.textInputNama);
        final TextInputLayout username_lay = (TextInputLayout) findViewById(R.id.textInputUsername);
        final TextInputLayout email_lay = (TextInputLayout) findViewById(R.id.textInputEmail);
        final TextInputLayout password_lay = (TextInputLayout) findViewById(R.id.textInputPassword);
        final TextInputLayout con_pass_lay = (TextInputLayout) findViewById(R.id.textInputConfPassword);
        final TextInputLayout no_meteran_lay = (TextInputLayout) findViewById(R.id.textInputNoMeteran);
        final TextInputLayout gol_listrik_lay = (TextInputLayout) findViewById(R.id.textInputGolListrik);





        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent forget = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(forget);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nama = nama_et.getText().toString().toLowerCase();
                String username = username_et.getText().toString().toLowerCase();
                String email = email_et.getText().toString().toLowerCase();
                String password = password_et.getText().toString().toLowerCase();
                String no_meteran = no_meteran_et.getText().toString();
                String gol_listrik = gol_listrik_et.getText().toString();

                new register().execute(nama,username,password,email,no_meteran,gol_listrik);
            }
        });

        nama_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (nama_et.getText().toString().equals("")){
                    nama_lay.setError("Nama is Empty.");
                    signup.setEnabled(false);
                }else {
                    nama_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });

        username_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (username_et.getText().toString().equals("")){
                    username_lay.setError("Username is Empty.");
                    signup.setEnabled(false);
                }else {
                    username_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });

        email_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (TextUtils.isEmpty(email_et.getText().toString()) || !email_et.getText().toString().matches(emailPattern)){
                    email_lay.setError("Enter a valid email address.");
                    signup.setEnabled(false);
                }else {
                    email_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });

        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (password_et.getText().length()< 6 || password_et.getText().length()> 15 ){
                    password_lay.setError("Password between 6 and 10 alphanumeric characters.");
                    signup.setEnabled(false);
                }else {
                    password_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });

        con_password_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (!con_password_et.getText().toString().equals(password_et.getText().toString())){
                    con_pass_lay.setError("Password Doesn't Match.");
                    signup.setEnabled(false);
                }else {
                    con_pass_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });


        no_meteran_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (no_meteran_et.getText().toString().equals("")){
                    no_meteran_lay.setError("No Meteran is Empty.");
                    signup.setEnabled(false);
                }else {
                    no_meteran_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });

        gol_listrik_et.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange (View v, boolean hasFocus){
                if (gol_listrik_et.getText().toString().equals("")){
                    gol_listrik_lay.setError("Golongan Listrik is Empty.");
                    signup.setEnabled(false);
                }else {
                    gol_listrik_lay.setError(null);
                    signup.setEnabled(true);
                }
            }
        });

    }



    private class register extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        AlertDialog alertDialog;

        @Override
        public String doInBackground(String... params) { //mengirim parameter ke api
            String nama = params[0];
            String username = params[1];
            String password = params[2];
            String email = params[3];
            String no_meteran = params[4];
            String gol_listrik = params[5];
            get_url_link link = new get_url_link();
            String url = link.getUrl_link("api_register");
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "nama="+nama+"&username="+username+"&password="+password+"&email="+email+"&no_meteran="+no_meteran+"&gol_listrik="+gol_listrik);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "a74e4b36-604d-5c8a-0512-e7dca84cc23f")
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        @Override
        protected void onPreExecute() { //menampilkan dialog proggress

            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
            alertDialog.setTitle("Register Status");
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Wait a Moment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) { //respon

            JSONObject jObj = null;
            String reason;
            int register_status=100;
            try
            {
                jObj = new JSONObject(result);
                JSONArray array_data= jObj.getJSONArray("daftar");
                JSONObject explrObject = array_data.getJSONObject(0);
                int status = explrObject.getInt("daftar_status");
                reason = explrObject.getString("reason");

                if (status==0){
                    pDialog.dismiss();
                    alertDialog.setMessage(reason);
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    new CountDownTimer(5000, 1000) {
                        public void onFinish() {
                            Intent forget = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(forget);
                            finish();
                        }

                        public void onTick(long millisUntilFinished) {
                            // millisUntilFinished    The amount of time until finished.
                        }
                    }.start();
                }else {
                    alertDialog.setMessage(reason);
                    alertDialog.show();
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
