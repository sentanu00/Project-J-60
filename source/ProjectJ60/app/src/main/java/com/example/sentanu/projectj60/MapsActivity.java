package com.example.sentanu.projectj60;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    String namaQuest, latRadius, longiRadius, latTarget, longiTarget;
    LatLng radius, target;
    int radRadius, radTarget;
    Button GTTarget, GTMyloc;
    LatLng mylocation;//-----------------------------------------------------------------lokasi user
    private LocationManager locManager;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GTTarget = (Button)findViewById(R.id.btn_go_to_target);
        GTMyloc = (Button)findViewById(R.id.btn_go_to_myloc);

        //---------------------------------------------------------------------------------------------- ambil lokasi user
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mylocation = new LatLng(location.getLatitude(),location.getLongitude());
        //----------------------------------------------------------------------------------------------
        //set button search target on---------------------------------------------------------------
        GTTarget.setVisibility(View.GONE);
        GTMyloc.setVisibility(View.VISIBLE);

        GTTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTTarget.setVisibility(View.GONE);
                GTMyloc.setVisibility(View.VISIBLE);
                setingKamera(target);
            }
        });
        GTMyloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GTTarget.setVisibility(View.VISIBLE);
                GTMyloc.setVisibility(View.GONE);
                setingKamera(mylocation);
            }
        });


        //ambil data yang dikirim dari MainActovoty.java
        Bundle extras = getIntent().getExtras();
        this.namaQuest = extras.getString("nama_quest");

        this.latRadius = extras.getString("latRadius");
        this.longiRadius = extras.getString("lonRadius");
        this.radRadius = extras.getInt("radRadius");

        this.latTarget = extras.getString("latTarget");
        this.longiTarget = extras.getString("lonTarget");
        this.radTarget = extras.getInt("radTarget");

        //conversi lat long target dan radius yang berupa String ke LatLng
        this.radius = new LatLng(Double.parseDouble(latRadius), Double.parseDouble(longiRadius));
        this.target = new LatLng(Double.parseDouble(latTarget), Double.parseDouble(longiTarget));


        Toast.makeText(MapsActivity.this, "nama quest : "+namaQuest, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "lat Radius : "+latRadius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "Lon Radius : "+longiRadius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "rad Radius : "+radRadius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "lat Target : "+latTarget, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "Lon Target : "+longiTarget, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "rad Target : "+radTarget, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "radius : "+radius, Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this, "target : "+target, Toast.LENGTH_SHORT).show();

        /* radius adalah variabel untuk lingkaran geofence POI secara meluas atau tidak mendetail
        * target adalah variabel untuk lokasi detil POI*/


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
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        //cek permission gps
        checkPermisionMyLocation();

        //setting kamera
        setingKamera(this.target);

        //radius luar
        buatLingkaran(radius,radRadius);

        //radius dalam
        //harusnya yang ini  tidak prlu di gambarkan, namun hanya perlu berupa notifikasi
        // bahwa anda telah berada di tempat yg benar
        buatLingkaran(target,radTarget);

        //buat marker radius
        // buatMarker(radius, R.mipmap.sample_marker_48, namaQuest);;
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
    public void setingKamera(LatLng target){

        //aktifkan zoom in pada kamera
        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        //lakukan animasi zoom in ke level 10, dengan animasi durasi 2 detik
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4),2000,null);

        //mengatur fokus pada tampilan target
        CameraPosition cm = new CameraPosition.Builder()
                .target(target)     //sementara target ada di luar
                .zoom(15)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cm));
    }

    //buat marker niatnya mau dirubah ke custom snippet agar waktu marker di klik
    //muncul gambar dan keterangan
    public void buatMarker(LatLng target, int lks_mrkr_img, String nama_marker){
        mMap.addMarker(new MarkerOptions()
                .position(target)
                .title(nama_marker)
                .snippet("Population: 4,137,400")
                .icon(BitmapDescriptorFactory.fromResource(lks_mrkr_img)));
    }

    //disini untuk mevisualisasikan radius dalam POI
    public void buatLingkaran(LatLng target, int radius){

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(target.latitude,target.longitude))
                .radius(radius)// In meters
                .strokeColor(0xffff9914)
                .fillColor(0x40eeff00)
                .strokeWidth(1);

        mMap.addCircle(circleOptions);

    }

    //bila posisi device berada dalam radius POI maka button bisa di klik, begitu juga sebaliknya.
    public Boolean set_button_camera_in_radius(LatLng myLoc, LatLng targetLoc){
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
}
