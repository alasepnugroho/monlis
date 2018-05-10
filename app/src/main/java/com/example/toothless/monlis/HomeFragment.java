package com.example.toothless.monlis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.toothless.monlis.Helper.CustomAdapter_Detail_Listrik;
import com.example.toothless.monlis.Helper.DatabaseHandler_Detail_Listrik;
import com.example.toothless.monlis.Helper.Detail_Listrik;
import  com.example.toothless.monlis.Helper.get_url_link;



public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    ListView lv;
    ProgressDialog pDialog;
    private TextView cob;
    AlertDialog alertDialog;
    SwipeRefreshLayout swipeLayout;
    private DatabaseHandler_Detail_Listrik databaseHelper;
    private Detail_Listrik detail_listrik;
    CustomAdapter_Detail_Listrik adapter;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.activity_fragment_home,container,false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        databaseHelper = new DatabaseHandler_Detail_Listrik(getActivity());
        lv = (ListView) rootView.findViewById(R.id.listdetaillistrik);
        ArrayList<Detail_Listrik> Array = databaseHelper.getAllUser();

        adapter = new CustomAdapter_Detail_Listrik(getContext(),Array);
        lv.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //new getlist().execute();
                        swipeLayout.setRefreshing(false);
                    }
                }
        );

        return rootView;

    }

    @Override
    public void onRefresh() {

    }


    /*private class getlist extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        AlertDialog alertDialog;

        @Override
        public String doInBackground(String... params) {
            get_url_link link = new get_url_link();
            String url = link.getUrl_link("api_get_detail_listrik");
            String result = null;
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "");
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "c3334dfa-e354-719f-1d39-f2f6b673bcf2")
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
            alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Login Status");
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Wait a Moment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            databaseHelper.deleteUser();
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

                ArrayList<Detail_Listrik> Array = databaseHelper.getAllUser();
                adapter = new CustomAdapter_Detail_Listrik(getContext(),Array);
                lv.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }
        }
    }*/
}