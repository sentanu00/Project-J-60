package com.example.sentanu.projectj60;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sentanu.projectj60.db_lokal.db_tmp;
import com.google.android.gms.games.quest.Quest;

import java.util.ArrayList;
import java.util.HashMap;

public class stage_menu_db_lokal extends AppCompatActivity implements AdapterView.OnItemClickListener {

    db_tmp db;

    protected ListView lv;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] Quest_name;
    String[] status;
    String[] Gbr;


    String judul_quest, id_quest;
    String la;
    String lo;
    int ra;
    int qop=0;
    Button btn_tutor1;
    TextView tv_tutor1;

    //------- untuk tutorial --------
    String status_tutor = "FALSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_menu_db_lokal);
        db = new db_tmp(this);

        tv_tutor1=(TextView) findViewById(R.id.tv_tutor1);
        btn_tutor1=(Button)findViewById(R.id.btn_tutor1);
        btn_tutor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_tutor1.setVisibility(View.GONE);
                btn_tutor1.setVisibility(View.GONE);
            }
        });

        lv = (ListView)findViewById(R.id.list_stage_id_lokal);

        Quest_name = new String[] {"Quest 1"};
        status = new String[] {"on going"};
        Gbr = new String[] {Integer.toString(R.mipmap.sample_1)};

        open_all_quest();
        open_all_tutor();

        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < Quest_name.length; i++){
            map = new HashMap<String, String>();
            map.put("nama_quest", Quest_name[i]);
            map.put("status", status[i]);
            map.put("gbr", Gbr[i]);
            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.layout_stage_menu,
                new String[] {"nama_quest","status", "gbr"}, new int[] {R.id.id_quest, R.id.status_quest, R.id.gambar});
        lv.setAdapter(Adapter);

        lv.setOnItemClickListener(this);
        //bisa lihat skor terakhir
        //bisa klik dengan mengirimkan radius dari data1 database lokal     [ok]
        //bila data3 sudah "complate" maka akan muncul perintah apakah anda ingin memainkannya lagi?
        //bila ia maka data2,data3 "quest_opened" di set false dan data1 di set true
        //semua data "completed" di set false
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent i = new Intent();
        i.setClass(stage_menu_db_lokal.this, MapsActivity.class);

        if(qop==0){
            cek_status_quest();
        }else{

            // digunakan untuk mengirim data menggunakan intent
            i.putExtra("nama_quest", judul_quest);

            //catatan untuk radius atau apapun ber ekstensi "Double" bila akan di share melalui
            // intent, covert ke "String" terlebih dahulu seperti latRadius dan lonRadius di
            // bawah ini
        /*i.putExtra("latRadius", la_luarx);
        i.putExtra("lonRadius", lo_luarx);
        i.putExtra("radRadius", ra_luarx);*/

            i.putExtra("latTarget", la);
            i.putExtra("lonTarget", lo);
            i.putExtra("radTarget", ra);

            //intent.putExtra("position", position);
            // Or / And
            //intent.putExtra("id", id);

            startActivity(i);
        }
    }


    public void open_all_tutor(){

        db = new db_tmp(this);
        ArrayList<ArrayList<Object>> data = db.ambilSemuaBarisTutor();
        for (int posisi = 0; posisi < data.size(); posisi++) {
            ArrayList<Object> baris = data.get(posisi);

            this.status_tutor = baris.get(1).toString();
        }
        db.close();
        check_tutor();
    }
    public void check_tutor(){
        if (status_tutor.equals("FALSE")) {
            tv_tutor1.setVisibility(View.GONE);
            btn_tutor1.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Keluar?")
                .setMessage("Benarkah anda ingin keluar?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                }).create().show();
    }
    public void open_all_quest(){

        ArrayList<ArrayList<Object>> data = db.ambilSemuaBarisQuest();
        for (int posisi = 0; posisi < data.size(); posisi++) {
            ArrayList<Object> baris = data.get(posisi);

            //Toast.makeText(Kamera_Checkin.this,baris.get(0).toString()+" "+baris.get(6).toString(), Toast.LENGTH_SHORT).show();

            //menentukan konsidi dr database
            if(baris.get(6).toString().equals("1")){

                this.id_quest     = baris.get(0).toString();
                this.judul_quest  = baris.get(1).toString();
                this.qop          = Integer.parseInt( baris.get(6).toString());
                this.la           = baris.get(3).toString();
                this.lo           = baris.get(4).toString();
                this.ra           = Integer.parseInt( baris.get(5).toString());

            }
        }
        db.close();
    }

    public void cek_status_quest(){
        new AlertDialog.Builder(this)
                .setTitle("Perhatian")
                .setMessage("Ingin memulai permainan lagi?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i = new Intent();
                        db = new db_tmp(stage_menu_db_lokal.this);
                        db.EditRowQuest("FALSE","1", "1");
                        db.EditRowQuest("FALSE","0", "2");
                        db.EditRowQuest("FALSE","0", "3");
                        open_all_quest();


                        i.putExtra("nama_quest", judul_quest);
                        i.putExtra("latTarget", la);
                        i.putExtra("lonTarget", lo);
                        i.putExtra("radTarget", ra);
                        i.setClass(stage_menu_db_lokal.this, MapsActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

}
