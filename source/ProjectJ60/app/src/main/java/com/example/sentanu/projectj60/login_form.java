package com.example.sentanu.projectj60;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login_form extends AppCompatActivity {

    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Context context;
    private Button buttonLogin;
    private ProgressDialog pDialog;

    //Tipe Variabel boolean untuk cek User Udah Login atau Tidak
    //Default set dengan False
    private boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        context = login_form.this;

        //Initializing views
        pDialog = new ProgressDialog(context);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        //Adding click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //editTextEmail.setText("admin@agusharyanto.net");
        //editTextPassword.setText("abcd1234");
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

    private void login() {
        //Getting values from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();

                            //Buatkan sebuah shared preference
                            SharedPreferences sharedPreferences = login_form.this.getSharedPreferences(AppVar.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Buatkan Sebuah variabel Editor Untuk penyimpanan Nilai shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Tambahkan Nilai ke Editor
                            editor.putBoolean(AppVar.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(AppVar.EMAIL_SHARED_PREF, email);

                            //Simpan Nilai ke Variabel editor
                            editor.commit();

                            gotoStageMenu();// go to class tujuan..:D

                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_EMAIL, email);
                params.put(AppVar.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

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
}
