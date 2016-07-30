package com.example.sentanu.projectj60.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sentanu.projectj60.R;
import com.example.sentanu.projectj60.data.Data;

import java.util.List;

/**
 * Created by sentanu on 28/07/2016.
 */
public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            convertView = inflater.inflate(R.layout.layout_stage_menu, null);

        TextView id_quest = (TextView) convertView.findViewById(R.id.id_quest);
        TextView judul_quest = (TextView) convertView.findViewById(R.id.judul_quest);
        TextView status_quest = (TextView) convertView.findViewById(R.id.status_quest);

        Data data = items.get(position);

        id_quest.setText("Quest "+data.getId_quest());
        judul_quest.setText(data.getJudul_quest());
        status_quest.setText(data.getStatus_quest());

        return convertView;
    }
}
