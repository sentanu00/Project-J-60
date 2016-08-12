package com.example.sentanu.projectj60;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sentanu.projectj60.adapter.Adapter;
import com.example.sentanu.projectj60.app.AppController;
import com.example.sentanu.projectj60.data.Data;
import com.example.sentanu.projectj60.db_lokal.db_tmp;
import com.example.sentanu.projectj60.server.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StageMenu extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    /*protected ListView listview;
    protected ListAdapter adapter;
    SwipeRefreshLayout swipe;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] id_quest, judul_quest, status_quest;
    String[] gambar;
*/

    db_tmp db;




    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    Button btn_signupLogin, btn_logout;
    String id_quest, judul_quest, status_quest, la_luar, lo_luar, ra_luar, la_dalam, lo_dalam, ra_dalam;

    //Tipe Variabel boolean untuk cek User Udah Login atau Tidak
    //Default set dengan False
    private boolean loggedIn = false;

    private static final String TAG = StageMenu.class.getSimpleName();

    private static String url_select 	 = Server.URL + "select.php";
    private static String url_insert 	 = Server.URL + "insert.php";
    private static String url_edit 	     = Server.URL + "edit.php";
    private static String url_update 	 = Server.URL + "update.php";
    private static String url_delete 	 = Server.URL + "delete.php";

    //------------------------------------------------------------------------------------------------
    public static final String TAG_ID_QUEST         = "id_quest";
    public static final String TAG_JUDUL_QUEST      = "judul_quest";
    public static final String TAG_STATUS_QUEST     = "status_quest";

    public static final String TAG_LA_LUAR          = "la_luar";
    public static final String TAG_LO_LUAR          = "lo_luar";
    public static final String TAG_RA_LUAR          = "ra_luar";
    public static final String TAG_LA_DALAM         = "la_dalam";
    public static final String TAG_LO_DALAM         = "lo_dalam";
    public static final String TAG_RA_DALAM         = "ra_dalam";

    private static final String TAG_SUCCESS         = "success";
    private static final String TAG_MESSAGE         = "message";


    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_menu);

        db = new db_tmp(this);




        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list_stage_id);
        btn_signupLogin = (Button)findViewById(R.id.signup_login);
        btn_logout = (Button)findViewById(R.id.btn_logout);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new Adapter(StageMenu.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get data pada itemlist.. hehee.. akhirnya bisa..:D
                final String id_questx = itemList.get(position).getId_quest();
                final String judul_questx = itemList.get(position).getJudul_quest();
                final String status_questx = itemList.get(position).getStatus_quest();

                final String la_luarx = itemList.get(position).getLa_luar();
                final String lo_luarx = itemList.get(position).getLo_luar();
                final int ra_luarx = Integer.parseInt(itemList.get(position).getRa_luar());

                final String la_dalamx = itemList.get(position).getLa_dalam();
                final String lo_dalamx = itemList.get(position).getLo_dalam();
                final int ra_dalamx = Integer.parseInt(itemList.get(position).getRa_dalam());

                Toast.makeText(StageMenu.this,"radius luar : "+ ra_luarx, Toast.LENGTH_SHORT).show();
                Toast.makeText(StageMenu.this,"position : "+ position, Toast.LENGTH_SHORT).show();
                Toast.makeText(StageMenu.this,"id       : "+ id, Toast.LENGTH_SHORT).show();

                Intent i = new Intent();
                i.setClass(StageMenu.this, MapsActivity.class);

                // digunakan untuk mengirim data menggunakan intent
                i.putExtra("nama_quest", id_questx);

                //catatan untuk radius atau apapun ber ekstensi "Double" bila akan di share melalui
                // intent, covert ke "String" terlebih dahulu seperti latRadius dan lonRadius di
                // bawah ini
                i.putExtra("latRadius", la_luarx);
                i.putExtra("lonRadius", lo_luarx);
                i.putExtra("radRadius", ra_luarx);
                i.putExtra("latTarget", la_dalamx);
                i.putExtra("lonTarget", lo_dalamx);
                i.putExtra("radTarget", ra_dalamx);
                //intent.putExtra("position", position);
                // Or / And
                //intent.putExtra("id", id);

                startActivity(i);
            }
        });

        btn_signupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(StageMenu.this, signup.class);

                startActivity(i);

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting out
                SharedPreferences preferences = getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                // put nilai false untuk login
                editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, false);

                // put nilai untuk username
                editor.putString(AppVar.EMAIL_SHARED_PREF, "");

                //Simpan ke haredpreferences

                editor.commit();

                // Balik dan tampilkan ke halaman Utama aplikasi jika logout berhasil
                Intent intent = new Intent(StageMenu.this, StageMenu.class);
                startActivity(intent);

            }
        });










/*
        signupLogin = (Button)findViewById(R.id.signup_login);
        signupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Then you start a new Activity via Intent
                Intent i = new Intent();
                i.setClass(StageMenu.this, signup.class);
                startActivity(i);
            }
        });

        listview = (ListView) findViewById(R.id.list_stage_id);

        gambar = new String[]{Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1),
                Integer.toString(R.mipmap.sample_1)};
        id_quest = new String[]{"quest 1", "quest 2", "quest 3", "quest 4"};
        judul_quest = new String[]{"Awal Dimulainya Misteri", "Kereta Kencana", "Fakta Sejarah", "Alibi"};
        status_quest = new String[]{"completed", "on going", "on going", "on going"};


        mylist = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < id_quest.length; i++) {
            map = new HashMap<String, String>();
            map.put("NO_QUEST", id_quest[i]);
            map.put("TITLE_QUEST", judul_quest[i]);
            map.put("STATUS", status_quest[i]);
            map.put("GAMBAR", gambar[i]);
            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.layout_stage_menu,
                new String[] {"NO_QUEST","TITLE_QUEST","STATUS","GAMBAR"}, new int[] {R.id.id_quest, R.id.judul_quest, R.id.status_quest, R.id.gambar});
        listview.setAdapter(Adapter);

        listview.setOnItemClickListener(this);*/
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences=getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn=sharedPreferences.getBoolean(AppVar.LOGGEDIN_SHARED_PREF, false);

        if (loggedIn){
            // aksi jika Login Sukses
            btn_logout.setVisibility(View.VISIBLE);
            btn_signupLogin.setVisibility(View.GONE);

        }
    }


/*
    // fungsi untuk klik stage pada listview..:D
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent i = new Intent();
        if(id==0){
            i.setClass(this, Status_Quest.class);
            startActivity(i);

        }else{

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

    }*/





































    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }


    // untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        //----------------------------------------------------------------------------
                        item.setId_quest(obj.getString(TAG_ID_QUEST));
                        item.setJudul_quest(obj.getString(TAG_JUDUL_QUEST));
                        item.setStatus_quest(obj.getString(TAG_STATUS_QUEST));

                        item.setLa_luar(obj.getString(TAG_LA_LUAR));
                        item.setLo_luar(obj.getString(TAG_LO_LUAR));
                        item.setRa_luar(obj.getString(TAG_RA_LUAR));

                        item.setLa_dalam(obj.getString(TAG_LA_DALAM));
                        item.setLo_dalam(obj.getString(TAG_LO_DALAM));
                        item.setRa_dalam(obj.getString(TAG_RA_DALAM));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }
}

