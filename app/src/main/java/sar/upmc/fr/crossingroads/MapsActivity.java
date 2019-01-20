package sar.upmc.fr.crossingroads;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location loc;
    private LocationCallback mLocationCallback;
    private boolean mRequestingLocationUpdates = true;
    //Buttons
    private FloatingActionButton buttonProfil;
    private FloatingActionButton buttonScores;
    //Music Player
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastKnownLocation();

        //Media Player
        Intent svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);

        //Buttons
        buttonProfil = findViewById(R.id.button);
        buttonScores = findViewById(R.id.button2);
        //Buttons Listeners
        buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScoresActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getLastKnownLocation() {
        Log.d("tag", "getLastKnownLocation called");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0  );
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()) {
                    Location location = task.getResult();
                    loc = location;
                    Log.d("tag", "onComplete called " + loc.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title("You are here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude())));
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

        mMap.addMarker(new MarkerOptions().position(new LatLng(-33.867487, 151.206990))).setIcon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.sydney_icon)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(48.856614, 2.352222))).setIcon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.paris_icon)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(40.712784, -74.005941))).setIcon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.newyork_icon)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO Gerer pour que la musique reprenne onResume
//        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mediaPlayer.pause();
        //mediaPlayer.release();
    }

    protected Bitmap resizeBitmap(int icon) {
        int height = 180;
        int width = 180;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(icon, null);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap marker = Bitmap.createScaledBitmap(b, width, height, false);
        return marker;
    }
}
