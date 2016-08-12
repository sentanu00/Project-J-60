package com.example.sentanu.projectj60;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sentanu.projectj60.db_lokal.db_tmp;

import java.util.ArrayList;

public class Kamera_Checkin extends AppCompatActivity {


    db_tmp db;
    Button btn_checkin;


    String id_quest;
    String judul_quest;
    String la;
    String lo;
    int ra, skor, qop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera__checkin);
        db = new db_tmp(this);
        //db.EditRowQuest();
        open_all_quest();


        /*Toast.makeText(Kamera_Checkin.this, "id quest   : "+id_quest, Toast.LENGTH_SHORT).show();
        Toast.makeText(Kamera_Checkin.this, "quest opened: "+qop, Toast.LENGTH_SHORT).show();
        Toast.makeText(Kamera_Checkin.this, "judul quest: "+judul_quest, Toast.LENGTH_SHORT).show();
        Toast.makeText(Kamera_Checkin.this, "lat Target : "+la, Toast.LENGTH_SHORT).show();
        Toast.makeText(Kamera_Checkin.this, "Lon Target : "+lo, Toast.LENGTH_SHORT).show();
        Toast.makeText(Kamera_Checkin.this, "rad Target : "+ra, Toast.LENGTH_SHORT).show();
*/
        btn_checkin=(Button)findViewById(R.id.btn_checkIn);
        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = new db_tmp(Kamera_Checkin.this);
                //jika tombol di klik  [OK]
                //data1 complete = true  [OK]
                //data1 row quest_opened = 0  [OK]
                //data1+1 row quest_opened = 1  [OK]

                String next_stage = (Integer.parseInt(id_quest)+1)+"";

                edit_data_quest(id_quest,"0","TRUE");
                if((Integer.parseInt(id_quest)+1)<=3){
                    edit_data_quest(next_stage,"1","FALSE");
                }
                db.EditRowTutor("FALSE");
                //db.EditRowQuest();
                reward(judul_quest);
            }
        });
    }

    public void open_all_quest(){

        ArrayList<ArrayList<Object>> data = db.ambilSemuaBarisQuest();
        for (int posisi = 0; posisi < data.size(); posisi++) {
            ArrayList<Object> baris = data.get(posisi);

            Toast.makeText(Kamera_Checkin.this,baris.get(0).toString()+" "+baris.get(6).toString(), Toast.LENGTH_SHORT).show();

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
    public void reward(String Quest){
        new AlertDialog.Builder(this)
                .setTitle("Selamat")
                .setMessage("Quest "+Quest+" Sukses!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i = new Intent();
                        i.setClass(Kamera_Checkin.this, Status_Quest.class);
                        startActivity(i);
                    }
                }).create().show();
    }

    public void edit_data_quest(String id, String quest_opened ,String status ){
        //db.update(TABEL_QUEST,values, ROW_ID_QUEST +"="+id,null);
       // public void EditRowQuest(String status, String quest_opened , String id) {
        db.EditRowQuest(status, quest_opened, id);
    }
}
