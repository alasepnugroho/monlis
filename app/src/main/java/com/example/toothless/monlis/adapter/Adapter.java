package com.example.toothless.monlis.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.toothless.monlis.R;
import com.example.toothless.monlis.data.Data;

import java.util.List;

/**
 * Created by KUNCORO on 23/04/2017.
 */

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> item;

    public Adapter(Activity activity, List<Data> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_pendidikan, null);

        TextView pendidikan = (TextView) convertView.findViewById(R.id.pendidikan);

        Data data;
        data = item.get(position);

        pendidikan.setText(data.getPendidikan());

        return convertView;
    }
}
