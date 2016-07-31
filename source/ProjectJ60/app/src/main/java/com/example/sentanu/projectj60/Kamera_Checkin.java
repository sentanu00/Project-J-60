package com.example.sentanu.projectj60;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Kamera_Checkin extends AppCompatActivity {

    Button btn_checkin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera__checkin);

        btn_checkin=(Button)findViewById(R.id.btn_checkIn);
        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(Kamera_Checkin.this, StageMenu.class);
                startActivity(i);
            }
        });
    }
}
