package com.example.sentanu.projectj60.db_lokal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sentanu.projectj60.Status_Quest;

import java.util.ArrayList;

/**
 * Created by sentanu on 06/08/2016.
 */
public class db_tmp {
    //mendeklarasikan NAMA_DB DAN DATABASE VERSION
    private static final String NAMA_DB ="j60_DB";
    private static final int DB_VERSION=1;

    //---------------------------------------// Tabel QUEST //------------------------------------------
//mendeklarasikan TABEL KUNCI dan ROW
    private static final String TABEL_QUEST="QUEST";

    private static final String ROW_ID_QUEST        = "id_quest";
    private static final String ROW_JUDUL_QUEST     = "judul_quest";
    private static final String ROW_STATUS_QUEST    = "status_quest";
    private static final String ROW_LATITUDE        = "latitude";
    private static final String ROW_LONGITUDE       = "longitude";
    private static final String ROW_RADIUS          = "radius";
    private static final String ROW_QUEST_OPENED    = "quest_opened";
    private static final String ROW_SKOR            = "skor";

    //mendeklarasikan CREATE_TABLE = MEMBUAT TABLE"
    private static final String CREATE_TABLE_QUEST =
            "create table "+TABEL_QUEST+" ("+ROW_ID_QUEST+" integer PRIMARY KEY autoincrement, "+ROW_JUDUL_QUEST+" text,"+ROW_STATUS_QUEST+" text,"+ROW_LATITUDE+" text,"+ROW_LONGITUDE+" text,"+ROW_RADIUS+" integer,"+ROW_QUEST_OPENED+" integer,"+ROW_SKOR+" integer)";
//--------------------------------------------------------------------------------------------------

    //---------------------------------------// Tabel Tutorial //------------------------------------------
//mendeklarasikan TABEL KUNCI dan ROW
    private static final String TABEL_TUTORIAL="TUTORIAL";

    private static final String ROW_ID_TUTOR        = "id_tutor";
    private static final String ROW_STATUS_TUTOR    = "status_tutor";

    //mendeklarasikan CREATE_TABLE = MEMBUAT TABLE"
    private static final String CREATE_TABLE_TUTORIAL =
            "create table "+TABEL_TUTORIAL+" ("+ROW_ID_TUTOR+" integer PRIMARY KEY autoincrement, "+ROW_STATUS_TUTOR+" text)";
//--------------------------------------------------------------------------------------------------



    //membuat mendeklarasikan itu adalah context
    private final Context context;
    //membuat mendeklarasikan DatabaseOpenHelper itu adalah dbhelper
    private DatabaseOpenHelper dbhelper;
    //membuat mendeklarasikan SQLiteDatabase itu adalah db
    private SQLiteDatabase db;

    //mengambil context untuk mengakses system di android
    public db_tmp(Context ctx) {
        //mendeklarasikan ctx adalah context ( context context di ganti ctx )
        this.context = ctx;
        // membuat DatabaseOpenHelper
        dbhelper = new DatabaseOpenHelper(context);
        //menuliskan DatabaseOpenHelper = SQLiteDatabase
        db = dbhelper.getWritableDatabase();
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------


    private static class DatabaseOpenHelper extends SQLiteOpenHelper {
        //membuat database
        public DatabaseOpenHelper(Context context) {
            super(context, NAMA_DB, null, DB_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            //db.execSQL(CREATE_TABLE);
            //db.execSQL(CREATE_TABLE_LOGIN);
            db.execSQL(CREATE_TABLE_QUEST);
            db.execSQL(CREATE_TABLE_TUTORIAL);

           /* Insert data to a Table QUEST*/
            db.execSQL("INSERT INTO "
                    + TABEL_QUEST
                    + " ("+ROW_JUDUL_QUEST+","+ROW_STATUS_QUEST+","+ROW_LATITUDE+","+ROW_LONGITUDE+","+ROW_RADIUS+","+ROW_QUEST_OPENED+","+ROW_SKOR+")"
                    + " VALUES ('SMKN 1 GATE', 'FALSE','-6.735186','108.536748', 5, 1,300);");/* Insert data to a Table QUEST*/
            /* Insert data to a Table QUEST*/
            db.execSQL("INSERT INTO "
                    + TABEL_QUEST
                    + " ("+ROW_JUDUL_QUEST+","+ROW_STATUS_QUEST+","+ROW_LATITUDE+","+ROW_LONGITUDE+","+ROW_RADIUS+","+ROW_QUEST_OPENED+","+ROW_SKOR+")"
                    + " VALUES ('PLAZA', 'FALSE','-6.734940','108.536718', 5, 0,300);");/* Insert data to a Table QUEST*/
            /* Insert data to a Table QUEST*/
            db.execSQL("INSERT INTO "
                    + TABEL_QUEST
                    + " ("+ROW_JUDUL_QUEST+","+ROW_STATUS_QUEST+","+ROW_LATITUDE+","+ROW_LONGITUDE+","+ROW_RADIUS+","+ROW_QUEST_OPENED+","+ROW_SKOR+")"
                    + " VALUES ('RUINS', 'FALSE','-6.734497','108.536292', 5, 0,300);");/* Insert data to a Table QUEST*/

            /* Insert data to a Table TUTORIAL*/
            db.execSQL("INSERT INTO "
                    + TABEL_TUTORIAL
                    + " ("+ROW_STATUS_TUTOR+")"
                    + " VALUES ('TRUE');");

        }
        //memperbarui database bila sudah ada
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS "+NAMA_DB);
            onCreate(db);

        }
    }

    //---------------------------------------------------------------------------------------------


    //menutup DatabaseOpenHelper
    public void close() {
        dbhelper.close();
    }

    //menambahkan pada row
    public void addRowQuest(String status) {

        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_QUEST, status);

        try {
            //menambahkan nama tabel bila tidak akan error
            // db.delete(NAMA_TABEL, null, null);
            db.insert(TABEL_QUEST, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }
    //menambahkan pada row
    public void EditRowQuest(String status, String quest_opened , String id) {

        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_QUEST, status);
        values.put(ROW_QUEST_OPENED, quest_opened);

        try {
            //menambahkan nama tabel bila tidak akan error
            // db.delete(NAMA_TABEL, null, null);
            // db.insert(TABEL_QUEST, null, values);
            //db.update(TABEL_QUEST, values, ROW_ID_QUEST + "=" + id + "", null);
            //db.update(TABEL_QUEST,values, ROW_ID_QUEST +"="+id,null);


            db.execSQL("update Quest set status_quest = '"+status+"', quest_opened = "+quest_opened+" where id_quest = "+id+"");
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }//menambahkan pada row


    /*//menambahkan pada row
    public void EditRowTutor(String status) {

        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_TUTOR, status);

        try {
            //menambahkan nama tabel bila tidak akan error
            // db.delete(NAMA_TABEL, null, null);
            // db.insert(TABEL_QUEST, null, values);
            //db.update(TABEL_QUEST, values, ROW_ID_QUEST + "=" + id + "", null);
            //db.update(TABEL_QUEST,values, ROW_ID_QUEST +"="+id,null);


            db.execSQL("update "+TABEL_TUTORIAL+" set "+ROW_STATUS_TUTOR+" = '"+status+"' where "+ROW_ID_TUTOR+" = 1");
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }//menambahkan pada row*/



    //menambahkan pada row
    public void EditRowTutor(String status) {

        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_TUTOR, status);

        try {
            db.execSQL("update "+TABEL_TUTORIAL+" set "+ROW_STATUS_TUTOR+" = '"+status+"' where "+ROW_ID_TUTOR+" = 1");
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }//menambahkan pada row

    public void EditRowQuest() {

        ContentValues values = new ContentValues();
        values.put(ROW_STATUS_QUEST, "TRUE");
        values.put(ROW_QUEST_OPENED, 1);

        try {
            //menambahkan nama tabel bila tidak akan error
            // db.delete(NAMA_TABEL, null, null);
            // db.insert(TABEL_QUEST, null, values);
            //db.update(TABEL_QUEST, values, ROW_ID_QUEST + "=" + id + "", null);
            //db.update(TABEL_QUEST,values,ROW_ID_QUEST+"=2",null);

            db.execSQL("update Quest set status_quest = 'TRUE', quest_opened = 1 where id_quest = 2");
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    //membuat array pada table layout
    public ArrayList<ArrayList<Object>> ambilSemuaBarisQuest() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;
        try {
            cur = db.query(TABEL_QUEST,
                    new String[] { ROW_ID_QUEST, ROW_JUDUL_QUEST, ROW_STATUS_QUEST, ROW_LATITUDE, ROW_LONGITUDE, ROW_RADIUS, ROW_QUEST_OPENED, ROW_SKOR }, null, null,
                    null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getLong(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataList.add(cur.getString(3));
                    dataList.add(cur.getString(4));
                    dataList.add(cur.getString(5));
                    dataList.add(cur.getString(6));
                    dataList.add(cur.getString(7));

                    dataArray.add(dataList);

                } while (cur.moveToNext());

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
        }
        return dataArray;

    }

    //membuat array pada table layout
    public ArrayList<ArrayList<Object>> ambilSemuaBarisTutor() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;
        try {
            //Cursor c = sqLiteDatabase.query("table1", tableColumns, whereClause, whereArgs, null, null, orderBy);
            cur = db.query(TABEL_TUTORIAL,
                    new String[] { ROW_ID_TUTOR, ROW_STATUS_TUTOR }, null, null,
                    null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getLong(0));
                    dataList.add(cur.getString(1));

                    dataArray.add(dataList);

                } while (cur.moveToNext());

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
        }
        return dataArray;

    }

}
