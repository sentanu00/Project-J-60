package com.example.sentanu.projectj60;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class StageMenu extends AppCompatActivity implements AdapterView.OnItemClickListener {

    protected ListView listview;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] no_quest, title_quest, status;
    String[] gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_menu);

        listview = (ListView) findViewById(R.id.list_stage_id);

        gambar = new String[]{Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1)};
        no_quest = new String[]{"quest 1", "quest 2", "quest 3", "quest 4"};
        title_quest = new String[]{"Awal Dimulainya Misteri", "Kereta Kencana", "Fakta Sejarah", "Alibi"};
        status = new String[]{"completed", "on going", "on going", "on going"};


        mylist = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < no_quest.length; i++) {
            map = new HashMap<String, String>();
            map.put("NO_QUEST", no_quest[i]);
            map.put("TITLE_QUEST", title_quest[i]);
            map.put("STATUS", status[i]);
            map.put("GAMBAR", gambar[i]);
            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.layout_stage_menu,
                new String[] {"NO_QUEST","TITLE_QUEST","STATUS","GAMBAR"}, new int[] {R.id.no_quest, R.id.title_quest, R.id.status, R.id.gambar});
        listview.setAdapter(Adapter);

        listview.setOnItemClickListener(this);
    }

    // fungsi untuk klik stage pada listview..:D
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent i = new Intent();
        i.setClass(this, MapsActivity.class);

        // digunakan untuk mengirim data menggunakan intent
        i.putExtra("nama_quest", "Quest 1");

        //catatan untuk radius atau apapun ber ekstensi "Double" bila akan di share melalui
        // intent, covert ke "String" terlebih dahulu seperti latRadius dan lonRadius di
        // bawah ini
        i.putExtra("latRadius", "-6.9295007");
        i.putExtra("lonRadius", "107.6080578");
        i.putExtra("radRadius", 100);
        i.putExtra("latTarget", "-6.9295007");
        i.putExtra("lonTarget", "107.6080578");
        i.putExtra("radTarget", 20);
        //intent.putExtra("position", position);
        // Or / And
        //intent.putExtra("id", id);

        startActivity(i);

    }
}

