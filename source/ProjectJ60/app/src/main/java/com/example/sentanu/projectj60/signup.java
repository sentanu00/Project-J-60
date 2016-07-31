package com.example.sentanu.projectj60;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sentanu.projectj60.adapter.Adapter;
import com.example.sentanu.projectj60.app.AppController;
import com.example.sentanu.projectj60.server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class signup extends AppCompatActivity {

    TextView link_login;
    private ProgressDialog pDialog;
    private Context context;

    //Tipe Variabel boolean untuk cek User Udah Login atau Tidak
    //Default set dengan False
    private boolean loggedIn = false;
    int success;
    Adapter adapter;
    AlertDialog.Builder dialog;


    EditText input_name, input_email, input_password, input_accountname, input_status;
    Button btn_signup;
    String email, password;

    private static final String TAG = signup.class.getSimpleName();
    private static String url_signup 	 = Server.URL + "signup.php";

    public static final String TAG_ID_USER     		= "id";
    public static final String TAG_USER_NAME     	= "username";
    public static final String TAG_PASSWORD      	= "password";
    public static final String TAG_EMAIL         	= "email";
    public static final String TAG_ACCOUNTNAME     	= "accountname";
    public static final String TAG_STATUS         	= "status";

    private static final String TAG_SUCCESS         = "success";
    private static final String TAG_MESSAGE         = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = signup.this;

        input_name=(EditText)findViewById(R.id.input_name);
        input_email=(EditText)findViewById(R.id.input_email);
        input_password=(EditText)findViewById(R.id.input_password);
        input_accountname=(EditText)findViewById(R.id.input_accountname);
        input_status=(EditText)findViewById(R.id.input_status);

        btn_signup=(Button)findViewById(R.id.btn_signup);
        link_login=(TextView)findViewById(R.id.link_login);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=input_email.getText().toString();
                password=input_password.getText().toString();
                simpan_user();

            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(signup.this, login_form.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences=getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn=sharedPreferences.getBoolean(AppVar.LOGGEDIN_SHARED_PREF, false);

        if (loggedIn){
            // Class Yang akan muncul jika Login Sukses
            gotoStageMenu();

        }
    }


    private void gotoStageMenu() {
        Intent intent = new Intent(context, StageMenu.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    // fungsi untuk menyimpan atau update
    private void simpan_user() {                                                                  // nnti di isi intent go to map..:D

        final String email = input_email.getText().toString();

        String url;
        // menyiapkan url simpan
        url = url_signup;

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        //callVolley();
                        //kosong();

                        Toast.makeText(signup.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        //---------------------------------------------------------------------------------------------------------------------------
                        //Buatkan sebuah shared preference
                        SharedPreferences sharedPreferences = signup.this.getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Buatkan Sebuah variabel Editor Untuk penyimpanan Nilai shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Tambahkan Nilai ke Editor
                        editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(AppVar.EMAIL_SHARED_PREF, email);

                        //Simpan Nilai ke Variabel editor
                        editor.commit();

                        //----------------------------------------------------------------------------------------------------------------------------

                    } else {
                        Toast.makeText(signup.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(signup.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                //------------------------------------------------------------------------------------
                // jika id kosong maka simpan, jika id ada nilainya maka update

                params.put("username", input_name.getText().toString());
                params.put("password", input_password.getText().toString());
                params.put("email", input_email.getText().toString());
                params.put("accountname", "user");
                params.put("status", "1");
                //--------------------------------------------------------------------------------

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }


}
