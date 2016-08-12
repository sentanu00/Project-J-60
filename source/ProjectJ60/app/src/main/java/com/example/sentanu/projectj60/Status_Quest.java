package com.example.sentanu.projectj60;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sentanu.projectj60.db_lokal.db_tmp;

import java.util.ArrayList;

public class Status_Quest extends AppCompatActivity {

    db_tmp db;
    Button btn_next_poi;

    String id_quest;
    String judul_quest;
    String la;
    String lo;
    int ra, skor;
    int qop = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status__quest);
        db = new db_tmp(this);

        open_all_quest();



        /*Toast.makeText(Status_Quest.this, "id quest   : "+id_quest, Toast.LENGTH_SHORT).show();
        Toast.makeText(Status_Quest.this, "judul quest : "+judul_quest, Toast.LENGTH_SHORT).show();
        Toast.makeText(Status_Quest.this, "quest opened: "+qop, Toast.LENGTH_SHORT).show();
        Toast.makeText(Status_Quest.this, "lat Target : "+la, Toast.LENGTH_SHORT).show();
        Toast.makeText(Status_Quest.this, "Lon Target : "+lo, Toast.LENGTH_SHORT).show();
        Toast.makeText(Status_Quest.this, "rad Target : "+ra, Toast.LENGTH_SHORT).show();
*/
        Toast.makeText(Status_Quest.this,"id quest = "+id_quest, Toast.LENGTH_SHORT).show();

        btn_next_poi=(Button)findViewById(R.id.btn_next_poi);
        btn_next_poi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();


                Toast.makeText(Status_Quest.this,"id quest = "+id_quest, Toast.LENGTH_SHORT).show();

                if(id_quest==null){
                    i.setClass(Status_Quest.this, stage_menu_db_lokal.class);

                }else if( id_quest!=null){

                    if((Integer.parseInt(id_quest))<=3){

                        i.putExtra("nama_quest", judul_quest);
                        i.putExtra("latTarget", la);
                        i.putExtra("lonTarget", lo);
                        i.putExtra("radTarget", ra);
                        i.setClass(Status_Quest.this, MapsActivity.class);
                        startActivity(i);
                    }
                }

                startActivity(i);

            }
        });


        // disini bisa lihat score dari db lokal,
        // bisa lihat next poi,
        // go to map dengan mengIntent radius data2 dari database
        // if data3 complated maka back to menu stage dan terima kasih sudah mencoba..:D
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent();
        i.setClass(Status_Quest.this, StageMenu.class);
        startActivity(i);
    }
    public void open_all_quest(){

        ArrayList<ArrayList<Object>> data = db.ambilSemuaBarisQuest();
        for (int posisi = 0; posisi < data.size(); posisi++) {
            ArrayList<Object> baris = data.get(posisi);
            Toast.makeText(Status_Quest.this,baris.get(0).toString()+" "+baris.get(6).toString(), Toast.LENGTH_SHORT).show();

            //menentukan konsidi dr database
            if(baris.get(6).toString().equals("1")){


                this.id_quest     = baris.get(0).toString();
                this.judul_quest  = baris.get(1).toString();
                this.qop          = Integer.parseInt( baris.get(6).toString());
                this.la           = baris.get(3).toString();
                this.lo           = baris.get(4).toString();
                this.ra           = Integer.parseInt( baris.get(5).toString());
                this.skor         = Integer.parseInt( baris.get(7).toString());

            }
        }
        db.close();
    }

    public void edit_data_quest(String id, String quest_opened ,String status ){
        db.EditRowQuest(status, quest_opened, id);
    }
}
