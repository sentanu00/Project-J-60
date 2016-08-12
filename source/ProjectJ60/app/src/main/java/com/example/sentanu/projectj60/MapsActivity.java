package com.example.sentanu.projectj60;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sentanu.projectj60.db_lokal.db_tmp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    private static String namaQuest, latRadius, longiRadius, latTarget, longiTarget;
    public static LatLng radius, target;
    private static int radRadius, radTarget;
    double CLa, CLo, angka_random,jarak_random, keliling_bumi;
    Button GTTarget, GTMyloc;
    public static ImageView imV_ket_btn_kamera;
    public static Button btn_checkin;
    public static Button btn_camera_visible;
    public static Button btn_camera_disable;
    LatLng mylocation, anotasi;//-----------------------------------------------------------------lokasi user
    private LocationManager locManager;


    public static LocationManager locationManager;
    private static PendingIntent pendingIntent;
    private static SharedPreferences sharedPreferences;

    private static GoogleMap mMap;

    //----------- variable tutor -------------------
    Button btn_tutor2;
    TextView tv_tutor2_1,tv_tutor2_2,tv_tutor2_3,tv_tutor2_4;
    public static TextView tv_tutor2_5;
    public int check_tutor=0;
    public static String status_tutor = "FALSE";
    //----------------------------------------------

    //------------------- DB ----------------------
    db_tmp db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //--------------- bagian tutorial ---------------
        tv_tutor2_1=(TextView)findViewById(R.id.tv_tutor2_1);
        tv_tutor2_2=(TextView)findViewById(R.id.tv_tutor2_2);
        tv_tutor2_3=(TextView)findViewById(R.id.tv_tutor2_3);
        tv_tutor2_4=(TextView)findViewById(R.id.tv_tutor2_4);
        tv_tutor2_5=(TextView)findViewById(R.id.tv_tutor2_5);
        btn_tutor2=(Button)findViewById(R.id.btn_tutor2);

        Intent proximityIntent = new Intent("in.wptrafficanalyzer.activity.proximity");

        // Creating a pending intent which will be invoked by LocationManager when the specified region is
        // entered or exited
        pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent,Intent.FLAG_ACTIVITY_NEW_TASK);

        //----------------- BAGIAN CHECK TUTORIAL --------------
        open_all_tutor();

        GTTarget = (Button)findViewById(R.id.btn_go_to_target);
        //GTMyloc = (Button)findViewById(R.id.btn_go_to_myloc);
        btn_camera_visible = (Button)findViewById(R.id.btn_camera_visible);
        btn_camera_disable = (Button)findViewById(R.id.btn_camera_disable);
        imV_ket_btn_kamera= (ImageView) findViewById(R.id.imV_ket_btn_kamera);


        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting stored latitude if exists else return 0
        String lat = sharedPreferences.getString("lat", "0");

        // Getting stored longitude if exists else return 0
        String lng = sharedPreferences.getString("lng", "0");

        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        //---------------------------------------------------------------------------------------------- ambil lokasi user
        /*locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mylocation = new LatLng(location.getLatitude(),location.getLongitude());
        *///----------------------------------------------------------------------------------------------
        //set button search target on---------------------------------------------------------------

        /*GTMyloc.setVisibility(View.GONE);btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(set_button_camera_in_radius(target,mylocation,radRadius)){
                    Toast.makeText(MapsActivity.this, "button camera bisa", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClass(MapsActivity.this, kamera.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MapsActivity.this, "disable", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        /*GTMyloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTTarget.setVisibility(View.VISIBLE);
                GTMyloc.setVisibility(View.GONE);
                setingKamera(mylocation);
            }
        });*/


        //ambil data yang dikirim dari MainActovoty.java
        Bundle extras = getIntent().getExtras();
        this.namaQuest = extras.getString("nama_quest");

        //this.latRadius = extras.getString("latRadius");
        //this.longiRadius = extras.getString("lonRadius");
        //this.radRadius = extras.getInt("radRadius");

        this.latTarget = extras.getString("latTarget");
        this.longiTarget = extras.getString("lonTarget");
        this.radTarget = extras.getInt("radTarget");



        this.keliling_bumi=2.0*3.14*6373.0;
        //this.angka_random=Math.random().*0.01;

        this.angka_random=(int)Math.random()*22/7;
        this.jarak_random=(int)Math.random()*0.35;

        //DecimalFormat decimalFormat = new DecimalFormat("0.0000000");
        //DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        //dfs.setDecimalSeparator('.');
        //decimalFormat.setDecimalFormatSymbols(dfs);

        //this.CLa = Double.parseDouble(latTarget+(jarak_random*360/keliling_bumi)* (int)Math.sin(angka_random));
        //this.CLo = Double.parseDouble(longiTarget+(jarak_random*360/keliling_bumi)* (int)Math.cos(angka_random));
       // String la = ""+decimalFormat.format(latTarget+(jarak_random*360/keliling_bumi)* Math.sin(angka_random));
        //String lo = ""+decimalFormat.format(longiTarget+(jarak_random*360/keliling_bumi)* Math.cos(angka_random));

        //conversi lat long target dan radius yang berupa String ke LatLng
        //this.radius = new LatLng(Double.parseDouble(la), Double.parseDouble(lo));
        this.target = new LatLng(Double.parseDouble(latTarget), Double.parseDouble(longiTarget));
        //randomGeo(this.target, 80);


        Toast.makeText(MapsActivity.this, "nama quest : "+namaQuest, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "lat Target : "+latTarget, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "Lon Target : "+longiTarget, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "rad Target : "+radTarget, Toast.LENGTH_SHORT).show();

        //Toast.makeText(MapsActivity.this, "lat radius : "+la, Toast.LENGTH_SHORT).show();
        //Toast.makeText(MapsActivity.this, "Lon radius : "+lo, Toast.LENGTH_SHORT).show();
        /*
        Toast.makeText(MapsActivity.this, "lat Radius : "+latRadius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "Lon Radius : "+longiRadius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "rad Radius : "+radRadius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "radius : "+radius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "target : "+target, Toast.LENGTH_SHORT).show();
*/
        /* radius adalah variabel untuk lingkaran geofence POI secara meluas atau tidak mendetail
        * target adalah variabel untuk lokasi detil POI*/


        btn_camera_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MapsActivity.this, Kamera_Checkin.class);
                locationManager.removeProximityAlert(pendingIntent);
                startActivity(i);
            }
        });
        btn_camera_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GoogleMap.OnMyLocationButtonClickListener;
            }
        });

        GTTarget.setOnClickListener(new View.OnClickListener() {//-----------------------------------------
            @Override
            public void onClick(View v) {
                //setingKamera(radius);
                setingKamera(target);
            }
        });

        btn_tutor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_tutor==0){
                    tv_tutor2_1.setVisibility(View.GONE);
                    tv_tutor2_2.setVisibility(View.VISIBLE);
                    tv_tutor2_3.setVisibility(View.GONE);
                    tv_tutor2_4.setVisibility(View.GONE);
                    check_tutor=check_tutor+1;
                }else if(check_tutor==1){
                    tv_tutor2_1.setVisibility(View.GONE);
                    tv_tutor2_2.setVisibility(View.GONE);
                    tv_tutor2_3.setVisibility(View.VISIBLE);
                    tv_tutor2_4.setVisibility(View.GONE);
                    check_tutor=check_tutor+1;
                }else if(check_tutor==2){
                    tv_tutor2_1.setVisibility(View.GONE);
                    tv_tutor2_2.setVisibility(View.GONE);
                    tv_tutor2_3.setVisibility(View.GONE);
                    tv_tutor2_4.setVisibility(View.VISIBLE);
                    check_tutor=check_tutor+1;
                }else if(check_tutor==3){
                    tv_tutor2_1.setVisibility(View.GONE);
                    tv_tutor2_2.setVisibility(View.GONE);
                    tv_tutor2_3.setVisibility(View.GONE);
                    tv_tutor2_4.setVisibility(View.GONE);
                    btn_tutor2.setVisibility(View.GONE);
                    check_tutor=check_tutor+1;
                }
            }
        });



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //non aktifkan tombol mylocation pojok kanan atas
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //cek permission gps
        checkPermisionMyLocation();

        //setting kamera
        setingKamera(this.target);

        //radius luar
        buatLingkaran(target,radTarget);
        //buatLingkaran(this.radius,80);

        //buat marker
        //buatMarker(target,R.mipmap.market_keris_100,"lokasi Quest 1");


        //radius dalam
        buat_geofenceProximity(target, radTarget);

        //buat marker radius
        // buatMarker(radius, R.mipmap.sample_marker_48, namaQuest);;
    }

    public void buat_geofenceProximity(LatLng target, int radius){

/*
        // This intent will call the activity ProximityActivity
        Intent proximityIntent = new Intent("in.wptrafficanalyzer.activity.proximity");

        // Creating a pending intent which will be invoked by LocationManager when the specified region is
        // entered or exited
        pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
*/
        // Setting proximity alert
        // The pending intent will be invoked when the device enters or exits the region 20 meters
        // away from the marked point
        // The -1 indicates that, the monitor will not be expired
        locationManager.addProximityAlert(target.latitude, target.longitude, radius, -1, pendingIntent);

        /** Opening the editor object to write data to sharedPreferences */
        SharedPreferences.Editor editor = sharedPreferences.edit();

        /** Storing the latitude of the current location to the shared preferences */
        editor.putString("lat", Double.toString(target.latitude));

        /** Storing the longitude of the current location to the shared preferences */
        editor.putString("lng", Double.toString(target.longitude));

        /** Saving the values stored in the shared preferences */
        editor.commit();


        //Toast.makeText(getBaseContext(), "Proximity Alert is added", Toast.LENGTH_SHORT).show();
    }


    //fungsi cek permision my location pada manifest, dan bila
    public void checkPermisionMyLocation(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
    }

    //fungsi ini bertujuan untuk perpindahan tampilan marker entah itu my location atau posisi POI
    //fungsi ini bertujuan untuk perpindahan tampilan marker entah itu my location atau posisi POI
    public void setingKamera(LatLng target){

        //aktifkan zoom in pada kamera
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        //lakukan animasi zoom in ke level 10, dengan animasi durasi 2 detik
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4),2000,null);

        //mengatur fokus pada tampilan target
        CameraPosition cm = new CameraPosition.Builder()
                .target(target)     //sementara target ada di luar
                .zoom(25)
                .tilt(60)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cm));
    }


    //buat marker niatnya mau dirubah ke custom snippet agar waktu marker di klik
    //muncul gambar dan keterangan
    public static void buatMarker(LatLng target, int lks_mrkr_img, String nama_marker){
        mMap.addMarker(new MarkerOptions()
                .position(target)
                .title(nama_marker)
                .snippet("Population: 4,137,400")
                .icon(BitmapDescriptorFactory.fromResource(lks_mrkr_img)));
    }

    //disini untuk mevisualisasikan radius dalam POI
    public static void buatLingkaran(LatLng target, int radius){

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(target.latitude,target.longitude))
                .radius(radius)// In meters
                .strokeColor(0xffff9914)
                .fillColor(0x40eeff00)
                .strokeWidth(1);

        mMap.addCircle(circleOptions);

    }

    //bila posisi device berada dalam radius POI maka button bisa di klik, begitu juga sebaliknya.
    public Boolean set_button_camera_in_radius(LatLng myLoc, LatLng targetLoc, int radiusGeofance){
        Location loc1 = new Location("");
        loc1.setLatitude(myLoc.latitude);
        loc1.setLongitude(myLoc.longitude);
        Location loc2 = new Location("");
        loc2.setLatitude(targetLoc.latitude);
        loc2.setLongitude(targetLoc.longitude);

        float distanceInMeters = loc1.distanceTo(loc2); //---------------------------------- jarak dalam meter
        if(distanceInMeters<100){
            return true;
        }else {
            return false;
        }

    }
    public static void kirimMarker(){
        buatMarker(target,R.mipmap.market_keris_100,"lokasi Quest 1");
        //harusnya yang ini  tidak prlu di gambarkan, namun hanya perlu berupa notifikasi
        // bahwa anda telah berada di tempat yg benar
        buatLingkaran(target,radTarget);
        //buat_geofenceProximity(target, radTarget);
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
            //tv_tutor1.setVisibility(View.GONE);
            //btn_tutor1.setVisibility(View.GONE);
            this.tv_tutor2_1.setVisibility(View.GONE);
            this.tv_tutor2_2.setVisibility(View.GONE);
            this.tv_tutor2_3.setVisibility(View.GONE);
            this.tv_tutor2_4.setVisibility(View.GONE);
            this.btn_tutor2.setVisibility(View.GONE);
        }
    }

    public void randomGeo(LatLng center, int radius){
        double la, lo;
        int rd, u, v;
        double w,t,x,y;

        la = center.latitude;
        lo = center.longitude;
        rd = radius/111300;

        u=(int)Math.random();
        v=(int)Math.random();

        w=rd*Math.sqrt(u);
        t=2*Math.PI*v;
        x=w*Math.cos(t);
        y=w*Math.sin(t);

        this.radius = new LatLng(y + la, x+lo);

        //this.target = new LatLng(Double.parseDouble(latTarget), Double.parseDouble(longiTarget));
    }

}
