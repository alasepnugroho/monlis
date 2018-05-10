package com.example.toothless.monlis.Helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;



import com.example.toothless.monlis.R;
import com.example.toothless.monlis.SplashActivity;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CustomAdapter_Detail_Listrik extends BaseAdapter {

    Context c;
    ArrayList<Detail_Listrik> detail_listriks;
    UserSessionManager session;
    public CustomAdapter_Detail_Listrik(Context c, ArrayList<Detail_Listrik> Detail_Listrik_list) {
        this.c = c;
        this.detail_listriks = Detail_Listrik_list;
    }

    @Override
    public int getCount() {
        return detail_listriks.size();
    }

    @Override
    public Object getItem(int i) {
        return detail_listriks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null)
        {
            view=LayoutInflater.from(c).inflate(R.layout.model_detail_listrik,viewGroup,false);

        }

        //Button daftar = (Button)view.findViewById(R.id.btn_daftarperiksa2);
        TextView energiTxt= (TextView) view.findViewById(R.id.energiTxt);
        TextView biayaTxt= (TextView) view.findViewById(R.id.biayaTxt);
        TextView batas_energiTxt= (TextView) view.findViewById(R.id.batas_energi);
        Detail_Listrik detail_listrik= (Detail_Listrik) this.getItem(i);

        final String energi=detail_listrik.getEnergi_bulanan();
        final String biaya = detail_listrik.getBiaya_bulanan();
        final String id_detail = detail_listrik.getId_detail();
        final String batas_energi = detail_listrik.getBatas_energi();



        energiTxt.setText("Dr. "+energi);
        biayaTxt.setText("Spesialis : "+ biaya);
        batas_energiTxt.setText(batas_energi);



        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OPEN DETAIL ACTIVITY
                openDetailActivity(Fullname,diag);

            }
        });*/

        return view;
    }
    ////open activity



}
