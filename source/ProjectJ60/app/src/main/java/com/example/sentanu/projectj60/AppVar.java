package com.example.sentanu.projectj60;

/**
 * Created by sentanu on 31/07/2016.
 */
public class AppVar {

    //URL to our login.php file, url bisa diganti sesuai dengan alamat server kita
    public static final String LOGIN_URL = "http://cbj60.scbdresearch.com/android_j60/php_controller_j60/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";


    //Kunci untuk Sharedpreferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //Ini digunakan untuk store username jika User telah Login
    public static final String EMAIL_SHARED_PREF = "username";

    // Ini digunakan untuk store sharedpreference untuk cek user melakukan login atau tidak
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}
