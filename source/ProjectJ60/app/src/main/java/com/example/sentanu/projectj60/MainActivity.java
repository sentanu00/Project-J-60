package com.example.sentanu.projectj60;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView= (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();

                //disini get posisi dari gritview dan get kordinat, radius 1 radius 2 dari database dan lempar ke map
                //disini harusnya yang di get adalah data dari database, bukan posisi gridview
                Intent i;
                i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("nama_quest", "Quest 1");

                //catatan untuk radius atau apapun ber ekstensi "Double" bila akan di share melalui
                // intent, covert ke "String" terlebih dahulu seperti latRadius dan lonRadius di
                // bawah ini
                i.putExtra("latRadius", "-6.9295007");
                i.putExtra("lonRadius", "107.6080578");
                i.putExtra("radRadius", 100);
                i.putExtra("latTarget", "-6.9295007");
                i.putExtra("lonTarget", "107.6080578");
                i.putExtra("radTarget", 40);

                startActivity(i);
            }
        });


        /*btn_map = (Button)findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });*/
    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c){
            mContext=c;
        }

        private Integer[] mThumbIds = {
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1,
                R.mipmap.sample_1
        };
        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //disini pengaturan tampilan griview
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(170,170));
            imageView.setPadding(6,6,6,6);
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
    }



}
